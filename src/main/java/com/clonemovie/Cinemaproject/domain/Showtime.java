package com.clonemovie.Cinemaproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Showtime() {}

    public Showtime(LocalDateTime startTime, Screen screen, Movie movie) {
        this.startTime = startTime;
        this.screen = screen;
        this.movie = movie;
    }
}