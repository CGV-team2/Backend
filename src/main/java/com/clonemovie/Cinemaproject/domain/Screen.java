package com.clonemovie.Cinemaproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screenId;

    @Getter @Setter
    private String name;  // 상영관 이름

    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;  // 극장과의 연관 관계 (극장에 속한 상영관)

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
    private List<Showtime> showtimes;  // 상영 시간 목록

    public Screen() {}

    public Screen(String name, Theater theater) {
        this.name = name;
        this.theater = theater;
    }
}