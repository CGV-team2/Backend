// Reservation.java
package com.clonemovie.Cinemaproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;

    @Getter @Setter
    private String seatNumber;

    @Getter @Setter
    @OneToOne
    @JoinColumn(name = "user_id")
    private Member member;

    public Reservation() {}

    public Reservation(Showtime showtime, String seatNumber, Member member) {
        this.showtime = showtime;
        this.seatNumber = seatNumber;
        this.member = member;
    }
}