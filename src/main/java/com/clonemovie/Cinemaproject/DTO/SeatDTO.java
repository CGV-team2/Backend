package com.clonemovie.Cinemaproject.DTO;

import com.clonemovie.Cinemaproject.domain.Seat;
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
        public String userId;
        public String movieName;
        public String theaterName;

        public ResponseSeat(Seat seat) {
            this.id = seat.getId();
            this.seatNumber = seat.getSeatNumber();
            this.showtimeDate = seat.getShowTime();
            this.screenName = seat.getScreenName();
            this.userId = seat.getMember().getUser_id();
            this.memberName = seat.getMember().getName();
            this.movieName = seat.getMovieName();
            this.theaterName = seat.getTheaterName();
        }
    }
}
