package com.youcode.korea2tv.seeder.dbSeeders;

import com.youcode.korea2tv.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;import java.net.http.HttpClient;


@Component
public class CountrySeeder {
    @Value("${spring.tmdb.api.key}")
    private String TMDB_API_KEY;
    @Value("${spring.tmdb.api.link-v3}")
    private String TMDB_BASE_URL_V3;
    private final CountryRepository countryRepository;
    private final HttpClient httpClient;

    public CountrySeeder(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
        this.httpClient = HttpClient.newHttpClient();
    }

    public void fetchAndSaveCountries() throws IOException, InterruptedException {
//        if (countryRepository.findAll().isEmpty()){
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(TMDB_BASE_URL_V3 + "/configuration/countries?api_key=" + TMDB_API_KEY))
//                    .build();
//
//            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(response.body());
//
//
//            Set<Country> countries = new HashSet<>();
//            for (JsonNode genreNode : rootNode) {
//                // Parse movie data and create Movie objects
//                Country country = Country.builder()
//                        .englishName(genreNode.get("english_name").asText())
//                        .nativeName(genreNode.get("native_name").asText())
//                        .iso(genreNode.get("iso_3166_1").asText())
//                        .build();
//
//                //store list genre
//                countries.add(country);
//            }
//            countryRepository.saveAll(countries);
//        }
    }

}
