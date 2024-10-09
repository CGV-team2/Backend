package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.DTO.MovieDTO.*;
import com.clonemovie.Cinemaproject.domain.Movie;
import com.clonemovie.Cinemaproject.repository.MovieRepository;
import com.clonemovie.Cinemaproject.service.GenreService;
import com.clonemovie.Cinemaproject.service.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<ResponseMovie> updateMovies() {
        genreService.fetchAndSaveGenres(); // 장르 업데이트

        movieServices.updateNowPlayingMovies(); // 영화 업데이트

        List<Movie> updatedMovies = movieServices.getAllMovies();
        return updatedMovies.stream()
                .map(ResponseMovie::new)
                .collect(Collectors.toList());
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
