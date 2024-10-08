package com.clonemovie.Cinemaproject.DTO;

import com.clonemovie.Cinemaproject.domain.Screen;
import com.clonemovie.Cinemaproject.domain.Showtime;
import com.clonemovie.Cinemaproject.domain.Theater;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class ScreenDTO {
    @Data
    @AllArgsConstructor
    public static class ResponseScreen{
        private Long screenId;
        private String screenName;
        private String theater;
        private List<Showtime> showtimes;
        private int rows;
        private int cols;

        public ResponseScreen(Screen screen) {
            this.screenId = screen.getScreenId();
            this.screenName = screen.getName();
            this.theater = screen.getTheater().getName();
            this.showtimes = screen.getShowtimes();
            this.rows = screen.getSeatRows();
            this.cols = screen.getSeatCols();
        }
    }
}
