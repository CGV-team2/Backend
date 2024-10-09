package com.clonemovie.Cinemaproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Genre {
    @Id
    @Getter
    private Long id;

    @Getter @Setter
    private String name;

    public Genre() {}
    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
