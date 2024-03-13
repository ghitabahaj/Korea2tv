package  com.youcode.korea2tv.seeder.dbSeeders;

import com.youcode.korea2tv.models.entity.*;
import  com.youcode.korea2tv.repositories.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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

    public MovieSeeder(MediaRepository mediaRepository,
                       ProductionRepository productionRepository,
                       CountryRepository countryRepository,
                       GenreRepository genreRepository,
                       VideosRepository videosRepository) {
        this.productionRepository = productionRepository;
        this.genreRepository = genreRepository;
        this.countryRepository = countryRepository;
        this.mediaRepository = mediaRepository;
        this.httpClient = HttpClient.newHttpClient();
        this.videosRepository = videosRepository;
    }

    public void fetchIdTmdbMedia() throws IOException, InterruptedException{
        long countPage = 1;
        long totalPages = 100;
        do {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(TMDB_BASE_URL_V3 + "/discover/movie?include_adult=true&include_video=true&page="+countPage+"&sort_by=popularity.desc&api_key=" + TMDB_API_KEY))
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
        // for dates
        // Inside your code
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

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


        // Store language
        String language = null;
        for (JsonNode item : spokenLanguagesNode) {
            language = item.get("name").asText();
        }

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
            String countryIso = item.get("iso_3166_1").asText();
            Optional<Country> existingCountry = countryRepository.findCountriesByIso(countryIso);
            Country country;
            if(existingCountry.isPresent()){
                country = existingCountry.get();
            }else{
                country = Country.builder()
                        .iso(item.get("iso_3166_1").asText())
                        .nativeName(item.get("name").asText())
                        .build();
                countriesCollect.add(country);
            }
            countriesNew.add(country);
        }
        countryRepository.saveAll(countriesNew);

        // Store data Videos
        Set<Videos> videosCollect = new HashSet<>();
        Set<Videos> videosNew = new HashSet<>();
        for (JsonNode item : videosNode) {
            String videoId = item.get("id").asText();
            Optional<Videos> existingVideos = videosRepository.findVideosByIdTmdb(videoId);
            Videos video;
            //Parse Date
//            LocalDateTime publishedAt = LocalDateTime.parse(item.get("published_at").asText(), formatter);
            //Object new video
            if(existingVideos.isPresent()){
                continue;
            }else{
                video = Videos.builder()
                        .idTmdb(item.get("id").asText())
                        .name(item.get("name").asText())
                        ._key(item.get("key").asText())
                        ._site(item.get("site").asText())
                        ._size(item.get("size").asInt())
                        ._type(item.get("type").asText())
                        ._official(item.get("official").asText())
                        ._publishedAt(LocalDateTime.now())
                        .build();
                videosCollect.add(video);
            }
            videosNew.add(video);
        }
        videosRepository.saveAll(videosNew);

        //Store Media
//        Set<Media> movies = new HashSet<>();
        // Parse movie data and create Movie objects
        //Parse Date
//        LocalDate releaseDate = LocalDate.parse(rootNode.get("release_date").asText(), formatter);
        Media media = Media.builder()
            .idTmdb(idTmdb)
            .idImdb(rootNode.get("imdb_id").asText())
            .title(rootNode.get("title").asText())
            .originalTitle(rootNode.get("original_title").asText())
            .posterPath(rootNode.get("poster_path").asText())
            .backDropPath(rootNode.get("backdrop_path").asText())
            .linkTrailer("Trailer")
            .director("Director")
            .status(rootNode.get("status").asText())
            .releaseDate(LocalDate.now())
            .overview(rootNode.get("overview").asText())
            .shortLink(UUID.randomUUID())
            .originalLanguage(language)
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
//            genresCollect.forEach(media::setGenre);
//            countriesCollect.forEach(media::setCountry);
//            productionsCollect.forEach(media::setProduction);
//            videosCollect.forEach(media::setVideo);
            //store list genre
//            movies.add(media);
        mediaRepository.save(media);
    }



}
