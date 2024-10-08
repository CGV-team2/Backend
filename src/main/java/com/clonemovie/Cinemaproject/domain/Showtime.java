package com.clonemovie.Cinemaproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Showtime {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    private LocalDateTime startTime;

    @ManyToOne @Getter @Setter
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @ManyToOne @Getter @Setter
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "showtime")
    @Getter @Setter
    private List<Seat> seats;  // 좌석 목록

    public Showtime() {}

    public Showtime(LocalDateTime startTime, Screen screen, Movie movie) {
        this.startTime = startTime;
        this.screen = screen;
        this.movie = movie;
    }
}