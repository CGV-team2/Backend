package com.clonemovie.Cinemaproject.DTO;

import com.clonemovie.Cinemaproject.domain.Genre;
import com.clonemovie.Cinemaproject.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class MovieDTO {
    @Data
    @AllArgsConstructor
    public static class ResponseTitleAndAdult{
        private String title;
        private Boolean adult;

        public ResponseTitleAndAdult(Movie movie){
            this.title = movie.getTitle();
            this.adult = movie.isAdult();
        }
    }

    @Data
    @AllArgsConstructor
    public static class ResponseMovie {
        private Long id;
        private Long movieId;
        private String title;
        private String originalLanguage;
        private String overview;
        private String releaseDate;
        private String backdropPath;
        private String posterPath;
        private List<String> genres;
        private Double popularity;
        private Double voteAverage;
        private boolean adult;

        public ResponseMovie(Movie movie){
            this.id = movie.getId();
            this.movieId = movie.getMovieId();
            this.title = movie.getTitle();
            this.overview = movie.getOverview();
            this.releaseDate = movie.getReleaseDate();
            this.backdropPath = movie.getBackdropPath();
            this.genres = movie.getGenres();
            this.popularity = movie.getPopularity();
            this.adult = movie.isAdult();
            this.posterPath = movie.getPosterPath();
            this.voteAverage = movie.getVoteAverage();
            this.originalLanguage = movie.getOriginalLanguage();
        }
    }
}
