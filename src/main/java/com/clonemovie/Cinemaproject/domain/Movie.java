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
    private boolean adult;

    @Getter @Setter
    private String releaseDate;

    @Getter @Setter @ElementCollection
    private List<Integer> genreId = new ArrayList<>();

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
                 List<Integer> genreId) {
        this.movieId = movieId;
        this.title = title;
        this.popularity = popularity;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.releaseDate = releaseDate;
        this.genreId = genreId;
    }
}
