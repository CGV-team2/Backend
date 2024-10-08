package com.clonemovie.Cinemaproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Seat {
    @Id @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    private String seatNumber;

    @ManyToOne @Getter @Setter
    @JoinColumn(name = "showtime_id")
    private Showtime showtime; // 상영 시간

    @ManyToOne @Getter @Setter
    @JoinColumn(name = "screen_id")
    private Screen screen; // 상영관

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private Member member;

    public Seat(){}
    public Seat(String seatNumber, Showtime showtime, Screen screen, Member member) {
        this.seatNumber = seatNumber;
        this.showtime = showtime;
        this.screen = screen;
        this.member = member;
    }
}
