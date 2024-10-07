package com.clonemovie.Cinemaproject.DTO;

import com.clonemovie.Cinemaproject.domain.Theater;
import lombok.Data;

public class TheaterDTO {
    @Data
    public static class TheaterRequest {
        private String name;
        private String location;
    }
}
