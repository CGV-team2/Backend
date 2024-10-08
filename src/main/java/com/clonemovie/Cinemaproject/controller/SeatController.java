package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.DTO.SeatDTO.*;
import com.clonemovie.Cinemaproject.domain.*;
import com.clonemovie.Cinemaproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.coyote.http11.Constants.A;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private ShowtimeService showtimeService;
    @Autowired
    private TheaterService theaterService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ScreenService screenService;
    @Autowired
    private SeatService seatService;

    @PostMapping("/book")
    public ResponseSeat bookTicket(@RequestParam Long theaterId,
                                           @RequestParam Long screenId,
                                           @RequestParam Long showtimeId,
                                           @RequestParam String seatNumber,
                                           @RequestParam Long memberId) {
        Member member = memberService.findById(memberId);
        if(member == null){
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }

        Theater theater = theaterService.getTheaterById(theaterId);
        if(theater == null) {
            throw new RuntimeException("영화관이 존재하지 않습니다.");
        }

        Screen screen = screenService.getScreenById(screenId);
        if(screen == null) {
            throw new RuntimeException("상영관이 존재하지 않습니다.");
        }

        Showtime showtime = showtimeService.findById(showtimeId);
        if (showtime == null || showtime.getScreen() != screen) {
            throw new RuntimeException("상영 시간이 존재하지 않습니다.");
        }

        int seatRows = (seatNumber.toUpperCase().charAt(0) - A) + 1;
        int seatCols = Integer.parseInt(seatNumber.substring(1));
        if(seatRows > screen.getSeatRows() || seatCols > screen.getSeatCols()) {
            throw new RuntimeException("해당 좌석의 번호는 존재하지 않습니다.");
        }

        if (seatService.isBookAvailable(showtime, seatNumber)) {
            throw new RuntimeException("해당 좌석은 이미 예약되었습니다.");
        }
        Seat seat = seatService.bookSeat(showtime, seatNumber, member, screen);
        return new ResponseSeat(seat);
    }

    @GetMapping("/available")
    public List<Seat> getAvailableSeats(@RequestParam Long showtimeId){
        return seatService.getBookedSeats(showtimeId);
    }
}
