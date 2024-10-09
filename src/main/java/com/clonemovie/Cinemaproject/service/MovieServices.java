package com.clonemovie.Cinemaproject.service;

import com.clonemovie.Cinemaproject.domain.Genre;
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

    private static final String NowPlaying_API_URL = "https://api.themoviedb.org/3/movie/now_playing?language=ko-KR&page=1";
    private static final String API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkODNmZjdlOWNjYzE0MTkxMGUwNjBjZmM1ZjQzMjllMiIsIm5iZiI6MTcyNjEwODA0My43MDU1MDksInN1YiI6IjY2ZTI0YmM1MDAwMDAwMDAwMDk1MGM3NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.njkW7B4TAAGMUBSlXXMI00c5h-5atfuyE2JcnrPtQFs";
    @Autowired
    private GenreService genreService;

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

            List<Genre> allGenres = genreService.getAllGenres(); // 조회
            Map<Integer, String> genreMap = new HashMap<>();

            for (Genre genre : allGenres) {
                genreMap.put(genre.getId().intValue(), genre.getName().toString());
            }

            for(JsonNode movieNode: responseBody.get("results")) {
                Long movieId = movieNode.get("id").asLong();
                nowPlayingMovieIds.add(movieId); //상영되고 있는 영화 리스트에 추가

                if(movieRepository.findByMovieId(movieId) == null) { //저장되어 있지않은 영화 저장
                    String title = movieNode.get("title").asText();
                    String overview = movieNode.get("overview").asText();
                    Double popularity = movieNode.get("popularity").asDouble();
                    String backdropPath = movieNode.get("backdrop_path").asText();
                    boolean adult = movieNode.get("adult").asBoolean();
                    String releaseDate = movieNode.get("release_date").asText();
                    String posterPath = movieNode.get("poster_path").asText();
                    double voteAverage = movieNode.get("vote_average").asDouble();
                    String originalLanguage = movieNode.get("original_language").asText(); // 왜 왜 왜 나 배고프단말이야아 머리도 말려야하는데

                    List<String> movieGenres = new ArrayList<>(); // 영화 장르 리스트
                    JsonNode genreNodes = movieNode.get("genre_ids");
                    if (genreNodes != null && genreNodes.isArray()) {
                        for (JsonNode genreNode : genreNodes) {
                            Integer genreId = genreNode.asInt();
                            String genreName = genreMap.get(genreId); // 매핑된 장르 이름 가져오기
                            if (genreName != null) {
                                movieGenres.add(genreName); // 장르 추가
                            }
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
                            movieGenres,
                            originalLanguage,
                            posterPath,
                            voteAverage));
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

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }
}