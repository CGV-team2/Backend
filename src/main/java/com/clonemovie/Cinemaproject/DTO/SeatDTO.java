package com.clonemovie.Cinemaproject.DTO;

import com.clonemovie.Cinemaproject.domain.Member;
import com.clonemovie.Cinemaproject.domain.Screen;
import com.clonemovie.Cinemaproject.domain.Seat;
import com.clonemovie.Cinemaproject.domain.Showtime;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

public class SeatDTO {
    @Data
    @AllArgsConstructor
    public static class ResponseSeat {
        private Long id;
        private String seatNumber;
        private LocalDateTime showtimeDate;
        private String screenName;
        public String memberName;
        public String movieName;

        public ResponseSeat(Seat seat) {
            this.id = seat.getId();
            this.seatNumber = seat.getSeatNumber();
            this.showtimeDate = seat.getShowtime().getStartTime();
            this.screenName = seat.getScreen().getName();
            this.memberName = seat.getMember().getName();
            this.movieName = seat.getShowtime().getMovie().getTitle();
        }
    }
}
