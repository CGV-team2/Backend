package com.clonemovie.Cinemaproject.DTO;

import lombok.Data;

public class TheaterDTO {
    @Data
    public static class TheaterRequest {
        private String name;
        private String location;
    }
}
