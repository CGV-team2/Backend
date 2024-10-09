package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.DTO.SeatDTO.ResponseSeat;
import com.clonemovie.Cinemaproject.domain.Member;
import com.clonemovie.Cinemaproject.domain.Seat;
import com.clonemovie.Cinemaproject.service.MemberService;
import com.clonemovie.Cinemaproject.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseSeat> bookTicket(@RequestHeader("Authorization") String token,
                                                   @RequestParam String movieName,
                                                   @RequestParam String screenName,
                                                   @RequestParam LocalDateTime showtime,
                                                   @RequestParam String theaterName,
                                                   @RequestParam String seatNumber) {
        try {
            Member member = memberService.tokenToMember(token);
            if (member == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED); // 401 Unauthorized
            }

            if (seatService.isBookAvailable(showtime, seatNumber, movieName, theaterName, screenName)) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT); // 409 Conflict
            }

            Seat seat = seatService.bookSeat(showtime, seatNumber, member, screenName, movieName, theaterName);
            return new ResponseEntity<>(new ResponseSeat(seat), HttpStatus.OK); // 200 OK

        } catch (Exception e) {
            System.err.println("좌석 예약 중 오류 발생: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<Seat>> getAvailableSeats(@RequestParam LocalDateTime showtime,
                                                        @RequestParam String screenName,
                                                        @RequestParam String movieName,
                                                        @RequestParam String theaterName) {
        try {
            List<Seat> availableSeats = seatService.getBookedSeats(showtime, screenName, movieName, theaterName);
            return new ResponseEntity<>(availableSeats, HttpStatus.OK); // 200 OK
        } catch (Exception e) {
            System.err.println("예약 가능한 좌석 조회 중 오류 발생: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
