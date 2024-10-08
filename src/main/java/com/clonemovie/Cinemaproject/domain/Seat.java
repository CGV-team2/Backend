package com.clonemovie.Cinemaproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Seat {
    @Id @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    private String seatNumber;

    @Getter @Setter
    private LocalDateTime showTime;

    @Getter @Setter
    private String screenName; // 상영관

    @Getter @Setter
    private String movieName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private Member member;

    public Seat(){}

    public Seat(String seatNumber, LocalDateTime showtime, String screenName, Member member, String movieName) {
        this.seatNumber = seatNumber;
        this.showTime = showtime;
        this.screenName = screenName;
        this.member = member;
        this.movieName = movieName;
    }
}
