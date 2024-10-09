package com.clonemovie.Cinemaproject.service;

import com.clonemovie.Cinemaproject.domain.Genre;
import com.clonemovie.Cinemaproject.repository.GenreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

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

            if (response.statusCode() != 200) {
                throw new RuntimeException("API 요청 실패: " + response.body());
            }

            genreRepository.deleteAll(); // 이전 장르 데이터 삭제

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, List<Map<String, Object>>> responseBody = objectMapper.readValue(response.body(), Map.class);

            List<Map<String, Object>> genres = responseBody.get("genres");

            if (genres == null || genres.isEmpty()) {
                throw new RuntimeException("장르 데이터가 없습니다.");
            }

            for (Map<String, Object> genreData : genres) {
                Long id = ((Integer) genreData.get("id")).longValue();
                String name = (String) genreData.get("name");

                Genre genre = new Genre(id, name);
                genreRepository.save(genre);
            }

        } catch (Exception e) {
            // 예외 발생 시 로그 출력 및 사용자 정의 예외 처리
            System.err.println("장르 업데이트 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            // 예외를 던져서 호출자에게 알림
            throw new RuntimeException("장르 업데이트에 실패했습니다.", e);
        }
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
}