package com.clonemovie.Cinemaproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    private String seatNumber; // 번호

    @Getter @Setter
    private boolean isReserved; // 예약

    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private Showtime showtime; // 상영 시간

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }
}
