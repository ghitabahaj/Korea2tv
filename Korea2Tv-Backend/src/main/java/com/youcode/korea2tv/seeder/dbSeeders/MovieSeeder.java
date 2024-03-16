package  com.youcode.korea2tv.seeder.dbSeeders;

import com.youcode.korea2tv.models.entity.*;
import com.youcode.korea2tv.models.entity.embedded.MediaCreditEmbedded;
import  com.youcode.korea2tv.repositories.*;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class MovieSeeder {
    @Value("${spring.tmdb.api.key}")
    private String TMDB_API_KEY;
    @Value("${spring.tmdb.api.link-v3}")
    private String TMDB_BASE_URL_V3;
    private final MediaRepository mediaRepository;
    private final GenreRepository genreRepository;
    private final CountryRepository countryRepository;
    private final ProductionRepository productionRepository;
    private final HttpClient httpClient;
    private final VideosRepository videosRepository;
    private final CreditRepository creditRepository;
    private final MediaCreditRepository mediaCreditRepository;

    public MovieSeeder(MediaRepository mediaRepository,
                       ProductionRepository productionRepository,
                       CountryRepository countryRepository,
                       GenreRepository genreRepository,
                       VideosRepository videosRepository,
                       CreditRepository creditRepository,
                       MediaCreditRepository mediaCreditRepository) {
        this.mediaCreditRepository = mediaCreditRepository;
        this.creditRepository = creditRepository;
        this.productionRepository = productionRepository;
        this.genreRepository = genreRepository;
        this.countryRepository = countryRepository;
        this.mediaRepository = mediaRepository;
        this.httpClient = HttpClient.newHttpClient();
        this.videosRepository = videosRepository;
    }

    public void fetchMediaTmdb() throws IOException, InterruptedException{
        long countPage = 1;
        long totalPages = 5;
        do {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(TMDB_BASE_URL_V3 + "/discover/movie?include_adult=true&include_video=true&page="+countPage+"&sort_by=popularity.desc&api_key=" + TMDB_API_KEY + "&with_original_language=ko"))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode resultsNode = rootNode.get("results");
            JsonNode totalPagesNode = rootNode.get("total_pages");

            //stock id movies
            for (JsonNode item : resultsNode) {
                Long idTmdb = item.get("id").asLong();
                Optional<Media> existingMedia = mediaRepository.findMediaByIdTmdb(idTmdb);
                if(existingMedia.isPresent()){
                    continue;
                }
                fetchMediaByIdTmdb(idTmdb);
            }
            System.out.println(countPage++);
        }while (countPage <= totalPages);
    }



    public void fetchMediaByIdTmdb(Long idTmdb) throws IOException, InterruptedException{
        //Get api
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TMDB_BASE_URL_V3 + "/movie/"+idTmdb+"?append_to_response=videos&api_key=" + TMDB_API_KEY))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        //select object json
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());
        JsonNode genresNode = rootNode.get("genres");
        JsonNode productionNode = rootNode.get("production_companies");
        JsonNode countriesNode = rootNode.get("production_countries");
        JsonNode videosNode = rootNode.get("videos").get("results");
        JsonNode spokenLanguagesNode = rootNode.get("spoken_languages");

        // Store data Genres
        Set<Genre> genresCollect = new HashSet<>();
        Set<Genre> genresNew = new HashSet<>();
        for (JsonNode item : genresNode) {
            Long genreId = item.get("id").asLong();
            Optional<Genre> existingGenre = genreRepository.findGenreByIdTmdb(genreId);
            Genre genre;
            if (existingGenre.isPresent()) {
                genre = existingGenre.get();
            } else {
                genre = Genre.builder()
                        .idTmdb(genreId)
                        .name(item.get("name").asText())
                        .build();
                genresNew.add(genre);
            }
            genresCollect.add(genre);
        }
        genreRepository.saveAll(genresNew);

        // Store data Productions
        Set<Production> productionsCollect = new HashSet<>();
        Set<Production> productionsNew = new HashSet<>();
        for (JsonNode item : productionNode) {
            Long productionId = item.get("id").asLong();
            Optional<Production> existingProduction = productionRepository.findProductionByIdTmdb(productionId);
            Production production;
            if(existingProduction.isPresent()){
                production = existingProduction.get();
            }else{
                production = Production.builder()
                        .idTmdb(item.get("id").asLong())
                        .logoPath(item.get("logo_path").asText())
                        .originCountry(item.get("origin_country").asText())
                        .name(item.get("name").asText())
                        .build();
                productionsNew.add(production);
            }
            productionsCollect.add(production);
        }
        productionRepository.saveAll(productionsNew);

        // Store data Countries
        Set<Country> countriesCollect = new HashSet<>();
        Set<Country> countriesNew = new HashSet<>();
        for (JsonNode item : countriesNode) {
            String countryName = item.get("name").asText();
            Optional<Country> existingCountry = countryRepository.findCountriesByName(countryName);
            Country country;
            if(existingCountry.isPresent()){
                country = existingCountry.get();
            }else{
                country = Country.builder()
                        .iso(item.get("iso_3166_1").asText())
                        .name(item.get("name").asText())
                        .build();
                countriesNew.add(country);
            }
                countriesCollect.add(country);
        }
        countryRepository.saveAll(countriesNew);

        // Store data Videos
        Set<Videos> videosCollect = new HashSet<>();
        Set<Videos> videosNew = new HashSet<>();
        for (JsonNode item : videosNode) {
            String videoId = item.get("id").asText();
            Optional<Videos> existingVideos = videosRepository.findVideosByIdTmdb(videoId);
            Videos video;
            //Parse Date Time
            LocalDateTime publishedAt = LocalDateTime.parse(item.get("published_at").asText(), DateTimeFormatter.ISO_DATE_TIME);
            //Object new video
            if(existingVideos.isPresent()){
                video = existingVideos.get();
            }else{
                video = Videos.builder()
                        .idTmdb(item.get("id").asText())
                        .name(item.get("name").asText())
                        ._key(item.get("key").asText())
                        ._site(item.get("site").asText())
                        ._size(item.get("size").asInt())
                        ._type(item.get("type").asText())
                        ._official(item.get("official").asText())
                        ._publishedAt(publishedAt)
                        .build();
                videosNew.add(video);
            }
            videosCollect.add(video);
        }
        videosRepository.saveAll(videosNew);

        // Store Media
        // Parse Date
        String title = rootNode.get("title").asText();
        String titleOriginal = rootNode.get("original_title").asText();
        LocalDate releaseDate = LocalDate.parse(rootNode.get("release_date").asText());
        String shortLink = title != null ? generateShortLink(title) : generateShortLink(titleOriginal);
        Media media = Media.builder()
                .idTmdb(idTmdb)
                .idImdb(rootNode.get("imdb_id").asText())
                .title(title)
                .shortLink(shortLink)
                .originalTitle(titleOriginal)
                .posterPath(rootNode.get("poster_path").asText())
                .backDropPath(rootNode.get("backdrop_path").asText())
                .status(rootNode.get("status").asText())
                .releaseDate(releaseDate)
                .duration(rootNode.get("runtime").asInt())
                .overview(rootNode.get("overview").asText())
                .originalLanguage(rootNode.get("original_language").asText())
                .levelView(0)
                .adult(rootNode.get("adult").asBoolean())
                .popularity(rootNode.get("popularity").asDouble())
                .voteAverage(rootNode.get("vote_average").asDouble())
                .voteCount(rootNode.get("vote_count").asInt())
                .typeMedia("movie")
                .videos(videosCollect)
                .countries(countriesCollect)
                .genres(genresCollect)
                .productions(productionsCollect)
                .build();
        Media mediaSaved = mediaRepository.save(media);

        saveCredits(idTmdb, mediaSaved);
    }


    public void saveCredits(Long idMediaTmdb, Media media) throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TMDB_BASE_URL_V3 + "/movie/"+idMediaTmdb+"/credits?api_key=" + TMDB_API_KEY))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());
        JsonNode castsNode = rootNode.get("cast");

        for (JsonNode item : castsNode) {
            Long idCredit = item.get("id").asLong();
            Optional<Credit> existingCredit = creditRepository.findCreditByIdTmdb(idCredit);
            Credit credit;
            Credit savedCredit;
            if(existingCredit.isPresent()){
                savedCredit = existingCredit.get();
            }else {
                //Store Credit
                credit = Credit.builder()
                        .idTmdb(idCredit)
                        .adult(item.get("adult").asBoolean())
                        .gender(item.get("gender").asInt())
                        .name(item.get("original_name").asText())
                        .popularity(item.get("popularity").asDouble())
                        .profilePath(item.get("profile_path").asText())
                        .build();
                savedCredit = creditRepository.save(credit);
            }

                // save Media Credit
                MediaCredit mediaCredit = MediaCredit.builder()
                        .id(MediaCreditEmbedded.builder()
                                .idCredit(savedCredit.getId())
                                .idMedia(media.getId())
                                .build())
                        .media(media)
                        .credit(savedCredit)
                        ._creditIdTmdb(item.get("credit_id").asText())
                        ._character(item.get("character").asText())
                        ._knownForDepartment(item.get("known_for_department").asText())
                        ._order(item.get("order").asInt())
                        .build();
                mediaCreditRepository.save(mediaCredit);
        }
    }

    public String generateShortLink(String titleOrOriginal){
        RandomStringGenerator generatorDIGITS8 = new RandomStringGenerator.Builder()
                .withinRange('0', '9')
                .filteredBy(CharacterPredicates.DIGITS)
                .build();
        RandomStringGenerator generatorDIGITS4 = new RandomStringGenerator.Builder()
                .withinRange('0', '9')
                .filteredBy(CharacterPredicates.DIGITS)
                .build();
        String randomString1 = generatorDIGITS8.generate(8); // Generate a random string of length 8
        String randomString2 = generatorDIGITS4.generate(4); // Generate a random string of length 8

        return titleOrOriginal.toLowerCase().replaceAll("[^a-z0-9]", "_") + "_" + randomString1 + "_" + randomString2;
    }


}
