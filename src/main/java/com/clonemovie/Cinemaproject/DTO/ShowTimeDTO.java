package com.clonemovie.Cinemaproject.DTO;

import com.clonemovie.Cinemaproject.domain.Seat;
import com.clonemovie.Cinemaproject.domain.Showtime;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ShowTimeDTO {
    @Data
    @AllArgsConstructor
    public static class ShowTimeResponse {
        private Long id;
        private String movieTitle;
        private String screenName;
        private LocalDateTime startTime;
        private List<Seat> seats;

        public ShowTimeResponse(Showtime showtime){
            this.id = showtime.getId();
            this.startTime = showtime.getStartTime();
            this.movieTitle = showtime.getMovie().getTitle();
            this.screenName = showtime.getScreen().getName();
            this.seats = showtime.getSeats();
        }
    }
}
