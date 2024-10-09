package com.clonemovie.Cinemaproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Id @Getter
    @GeneratedValue
    private Long id;

    @Getter @Setter
    private Long movieId;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private Double popularity;

    @Getter @Setter
    @Column(columnDefinition = "TEXT")
    private String overview;

    @Getter @Setter
    private String backdropPath;

    @Getter @Setter
    private String posterPath;

    @Getter @Setter
    private Double voteAverage;

    @Getter @Setter
    private String originalLanguage;

    @Getter @Setter
    private boolean adult;

    @Getter @Setter
    private String releaseDate;

    @Getter @Setter @ElementCollection
    private List<String> genres = new ArrayList<>();

    // 기본 생성자
    public Movie() {}

    // 생성자
    public Movie(Long movieId,
                 String title,
                 Double popularity,
                 String overview,
                 String backdropPath,
                 boolean adult,
                 String releaseDate,
                 List<String> genres,
                 String originalLanguage,
                 String posterPath,
                 double voteAverage) {
        this.movieId = movieId;
        this.title = title;
        this.popularity = popularity;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.releaseDate = releaseDate;
        this.genres = genres;
        this.originalLanguage = originalLanguage;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
    }
}
