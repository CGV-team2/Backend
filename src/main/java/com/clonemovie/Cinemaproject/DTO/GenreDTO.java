package com.clonemovie.Cinemaproject.DTO;

import com.clonemovie.Cinemaproject.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

public class GenreDTO {
    @Data
    @AllArgsConstructor
    public static class ResponseGenre {
        private Long id;
        private String name;
        public ResponseGenre(Genre genre) {
            this.id = genre.getId();
            this.name = genre.getName();
        }
    }
}
