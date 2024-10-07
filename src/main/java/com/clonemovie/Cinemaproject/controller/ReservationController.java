package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.domain.*;
import com.clonemovie.Cinemaproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ShowtimeService showtimeService;
    @Autowired
    private TheaterService theaterService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ScreenService screenService;

    @PostMapping("/book")
    public Reservation bookTicket(@RequestParam Long theaterId, @RequestParam Long screenId, @RequestParam Long showtimeId, @RequestParam String seatNumber, @RequestParam Long memberId) {
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
        if (showtime == null) {
            throw new RuntimeException("상영 시간이 존재하지 않습니다.");
        }

        if (!reservationService.isSeatAvailable(showtimeId, seatNumber)) {
            throw new RuntimeException("해당 좌석은 이미 예약되었습니다.");
        }
        return reservationService.makeReservation(showtime, seatNumber, member);
    }
}
