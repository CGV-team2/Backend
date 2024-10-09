package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.DTO.SeatDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SeatService seatService;

    @PostMapping("/book")
    public ResponseEntity<List<SeatDTO.ResponseSeat>> bookTickets(@RequestHeader("Authorization") String token,
                                                                  @RequestBody SeatDTO.BookingRequest bookingRequest) {
        try {
            // 회원 인증
            Member member = memberService.tokenToMember(token);
            if (member == null) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED); // 401 Unauthorized
            }

            // 좌석별 예약 가능 여부 확인
            for (String seatNumber : bookingRequest.getSeatNumbers()) {
                if (seatService.isBookAvailable(bookingRequest.getShowtime(), seatNumber, bookingRequest.getMovieName(), bookingRequest.getTheaterName(), bookingRequest.getScreenName())) {
                    return new ResponseEntity<>(null, HttpStatus.CONFLICT); // 409 Conflict
                }
            }

            // 모든 좌석 예약 진행
            List<SeatDTO.ResponseSeat> bookedSeats = bookingRequest.getSeatNumbers().stream()
                    .map(seatNumber -> {
                        Seat seat = seatService.bookSeat(bookingRequest.getShowtime(), seatNumber, member, bookingRequest.getScreenName(), bookingRequest.getMovieName(), bookingRequest.getTheaterName());
                        return new SeatDTO.ResponseSeat(seat);
                    }).collect(Collectors.toList());

            return new ResponseEntity<>(bookedSeats, HttpStatus.OK); // 200 OK

        } catch (Exception e) {
            System.err.println("좌석 예약 중 오류 발생: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }


    // 예약 가능한 좌석 조회 메서드
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
