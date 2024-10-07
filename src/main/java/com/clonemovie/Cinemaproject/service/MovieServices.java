package com.clonemovie.Cinemaproject.service;

import com.clonemovie.Cinemaproject.domain.Movie;
import com.clonemovie.Cinemaproject.repository.MovieRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class MovieServices {
    @Autowired
    private MovieRepository movieRepository;

    private static final String NowPlaying_API_URL = "https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1";
    private static final String API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkODNmZjdlOWNjYzE0MTkxMGUwNjBjZmM1ZjQzMjllMiIsIm5iZiI6MTcyNjEwODA0My43MDU1MDksInN1YiI6IjY2ZTI0YmM1MDAwMDAwMDAwMDk1MGM3NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.njkW7B4TAAGMUBSlXXMI00c5h-5atfuyE2JcnrPtQFs";

    public void updateNowPlayingMovies() {
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(NowPlaying_API_URL))
                    .header("accept", "application/json")
                    .header("Authorization", API_KEY)
                    .build();

            // HTTP 요청 보내기
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseBody = objectMapper.readTree(response.body());

            Set<Long> nowPlayingMovieIds = new HashSet<>();

            for(JsonNode movieNode: responseBody.get("results")) {
                Long movieId = movieNode.get("id").asLong();
                nowPlayingMovieIds.add(movieId); //상영되고 있는 영화 리스트에 추가

                if(movieRepository.findByMovieId(movieId) == null) { //저장되어 있지않은 영화 저장
                    String title = movieNode.get("title").asText();
                    String originalTitle = movieNode.get("original_title").asText();
                    String overview = movieNode.get("overview").asText();
                    Double popularity = movieNode.get("popularity").asDouble();
                    String backdropPath = movieNode.get("backdrop_path").asText();
                    boolean adult = movieNode.get("adult").asBoolean();
                    String releaseDate = movieNode.get("release_date").asText();

                    List<Integer>genreIds = new ArrayList<>();
                    JsonNode genreNodes = movieNode.get("genre_ids");
                    if(genreNodes != null && genreNodes.isArray()) {
                        for(JsonNode genreNode: genreNodes) {
                            genreIds.add(genreNode.asInt());
                        }
                    }

                    Movie movie = movieRepository.findById(movieId).orElse(new Movie(
                            movieId,
                            title,
                            popularity,
                            overview,
                            backdropPath,
                            adult,
                            releaseDate,
                            genreIds));
                    movieRepository.save(movie);
                }
            }

            List<Movie> allMovies = movieRepository.findAll();

            for(Movie movie : allMovies){ //상영되고 있지 않은 영화 삭제
                if(!nowPlayingMovieIds.contains(movie.getMovieId())){
                    movieRepository.delete(movie);
                }
            }
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Movie getMovieByMovieId(Long movieId){
        return movieRepository.findByMovieId(movieId);
    }
}
