package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.DTO.MovieDTO.*;
import com.clonemovie.Cinemaproject.domain.Movie;
import com.clonemovie.Cinemaproject.service.GenreService;
import com.clonemovie.Cinemaproject.service.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieController {

    @Autowired
    private MovieServices movieServices;

    @Autowired
    private GenreService genreService;

    // 영화 데이터 업데이트
    @GetMapping("/update-movies")
    public ResponseEntity<List<ResponseMovie>> updateMovies() {
        try {
            genreService.fetchAndSaveGenres(); // 장르 업데이트
            movieServices.updateNowPlayingMovies(); // 영화 업데이트

            List<Movie> updatedMovies = movieServices.getAllMovies();
            List<ResponseMovie> responseMovies = updatedMovies.stream()
                    .map(ResponseMovie::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(responseMovies, HttpStatus.OK);
        } catch (Exception e) {
            // 예외 발생 시 에러 로그 출력 및 사용자에게 에러 메시지 반환
            System.err.println("영화 데이터 업데이트 중 오류 발생: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/movie/getMovieInfo/{movieId}")
    public ResponseEntity<ResponseMovie> getMovie(@PathVariable("movieId") Long movieId) {
        try {
            Movie movie = movieServices.getMovieByMovieId(movieId);
            if (movie == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 영화가 없는 경우 404 응답
            }
            return new ResponseEntity<>(new ResponseMovie(movie), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("영화 조회 중 오류 발생: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/movie/getMovieTitle/{movieId}")
    public ResponseEntity<ResponseTitleAndAdult> getMovieTitle(@PathVariable("movieId") Long movieId) {
        try {
            Movie movie = movieServices.getMovieByMovieId(movieId);
            if (movie == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 영화가 없는 경우 404 응답
            }
            return new ResponseEntity<>(new ResponseTitleAndAdult(movie), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("영화 제목 조회 중 오류 발생: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
