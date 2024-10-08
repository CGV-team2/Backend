    package com.clonemovie.Cinemaproject.controller;

    import com.clonemovie.Cinemaproject.DTO.SeatDTO.*;
    import com.clonemovie.Cinemaproject.domain.*;
    import com.clonemovie.Cinemaproject.service.*;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.time.LocalDateTime;
    import java.util.List;

    @RestController
    @RequestMapping("/seats")
    public class SeatController {

        @Autowired
        private MemberService memberService;
        @Autowired
        private SeatService seatService;

        @PostMapping("/book")
        public ResponseSeat bookTicket(@RequestHeader("Authorization") String token,
                                       @RequestParam String movieName,
                                       @RequestParam String screenName,
                                       @RequestParam LocalDateTime showtime,
                                       @RequestParam String theaterName,
                                       @RequestParam String seatNumber) {
            Member member = memberService.tokenToMember(token);
            if(member == null) {
                throw new RuntimeException("유저가 존재하지 않습니다.");
            }

            if (seatService.isBookAvailable(showtime, seatNumber, movieName, theaterName, screenName)) {
                throw new RuntimeException("해당 좌석은 이미 예약되었습니다.");
            }
            Seat seat = seatService.bookSeat(showtime, seatNumber, member, screenName, movieName, theaterName);
            return new ResponseSeat(seat);
        }

        @GetMapping("/available")
        public List<Seat> getAvailableSeats(@RequestParam LocalDateTime showtime,
                                            @RequestParam String screenName,
                                            @RequestParam String movieName,
                                            @RequestParam String theaterName){
            return seatService.getBookedSeats(showtime, screenName, movieName, theaterName);
        }
    }
