package com.clonemovie.Cinemaproject.DTO;

import com.clonemovie.Cinemaproject.domain.Showtime;

import java.time.LocalDateTime;
import java.util.Date;

public class ShowTimeDTO {
    public static class ShowTimeResponse {
        private int id;
        private String movieTitle;
        private String screenName;
        private LocalDateTime startTime;

        public ShowTimeResponse(Showtime showtime){
            this.startTime = showtime.getStartTime();
            this.movieTitle = showtime.getMovie().getTitle();
            this.screenName = showtime.getScreen().getName();
        }
    }
}
