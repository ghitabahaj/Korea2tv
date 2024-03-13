package com.youcode.korea2tv.seeder.dbSeeders;

import com.youcode.korea2tv.models.entity.Genre;
import com.youcode.korea2tv.repositories.GenreRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class GenreSeeder {
    @Value("${spring.tmdb.api.key}")
    private String TMDB_API_KEY;
    @Value("${spring.tmdb.api.link-v3}")
    private String TMDB_BASE_URL_V3;
    private final GenreRepository genreRepository;
    private final HttpClient httpClient;

    public GenreSeeder(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
        this.httpClient = HttpClient.newHttpClient();
    }


    public void fetchAndSaveGenre() throws IOException, InterruptedException {
        if (genreRepository.findAll().isEmpty()){
            this.requestHttpMethode("/genre/movie/list?api_key=");
            this.requestHttpMethode("/genre/tv/list?api_key=");
        }
    }

    public void requestHttpMethode(String  endPoint) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TMDB_BASE_URL_V3 + endPoint + TMDB_API_KEY))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.body());
        JsonNode resultsNode = rootNode.get("genres");


        Set<Genre> genres = new HashSet<>();
        for (JsonNode genreNode : resultsNode) {
            //check genre if exist
            Optional<Genre> genreCheck = genreRepository.findGenreByIdTmdb(genreNode.get("id").asLong());
            if(genreCheck.isPresent()){
                continue;
            }

            // Parse movie data and create Movie objects
            Genre genre = Genre.builder()
                    .idTmdb(genreNode.get("id").asLong())
                    .name(genreNode.get("name").asText())
                    .build();

            //store list genre
            genres.add(genre);
        }
        genreRepository.saveAll(genres);
    }

}