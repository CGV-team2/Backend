package com.clonemovie.Cinemaproject.service;

import com.clonemovie.Cinemaproject.domain.Genre;
import com.clonemovie.Cinemaproject.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    private static final String GENRE_API_URL = "https://api.themoviedb.org/3/genre/movie/list?language=ko-KR";
    private static final String API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkODNmZjdlOWNjYzE0MTkxMGUwNjBjZmM1ZjQzMjllMiIsIm5iZiI6MTcyNjEwODA0My43MDU1MDksInN1YiI6IjY2ZTI0YmM1MDAwMDAwMDAwMDk1MGM3NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.njkW7B4TAAGMUBSlXXMI00c5h-5atfuyE2JcnrPtQFs";

    public void fetchAndSaveGenres() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GENRE_API_URL))
                    .header("accept", "application/json")
                    .header("Authorization", API_KEY)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            genreRepository.deleteAll();

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, List<Map<String, Object>>> responseBody = objectMapper.readValue(response.body(), Map.class);

            List<Map<String, Object>> genres = responseBody.get("genres");

            for (Map<String, Object> genreData : genres) {
                Long id = ((Integer) genreData.get("id")).longValue();
                String name = (String) genreData.get("name");

                Genre genre = new Genre(id, name);
                genreRepository.save(genre);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
}
