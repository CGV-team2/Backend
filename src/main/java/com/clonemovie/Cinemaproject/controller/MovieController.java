package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.DTO.MovieDTO.*;
import com.clonemovie.Cinemaproject.domain.Movie;
import com.clonemovie.Cinemaproject.repository.MovieRepository;
import com.clonemovie.Cinemaproject.service.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @Autowired
    private MovieServices movieServices;

    // 영화 데이터 업데이트
    @GetMapping("/update-movies")
    public String updateMovies() {
        movieServices.updateNowPlayingMovies();
        return "현재 상영 중인 영화 데이터가 업데이트되었습니다!";
    }

    @GetMapping("/movie/getMovieInfo/{movieId}")
    public ResponseMovie getMovie(@PathVariable("movieId") Long movieId) {
        Movie movie = movieServices.getMovieByMovieId(movieId);
        if(movie == null) return null;
        return new ResponseMovie(movie);
    }

    @GetMapping("/movie/getMovieTitle/{movieId}")
    public ResponseTitleAndAdult getMovieTitle(@PathVariable("movieId") Long movieId) {
        Movie movie = movieServices.getMovieByMovieId(movieId);
        if(movie == null) return null;
        return new ResponseTitleAndAdult(movie);
    }
}
