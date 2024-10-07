package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.domain.Seat;
import com.clonemovie.Cinemaproject.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/seats/available/{showtimeId}")
    public List<Seat> getAvailableSeats(@PathVariable Long showtimeId) {
        return seatService.getAvailableSeats(showtimeId);
    }

    @PostMapping("/seats/reserve/{showtimeId}/{seatId}")
    public String reserveSeat(@PathVariable Long showtimeId, @PathVariable Long seatId) {
        return seatService.reserveSeat(showtimeId, seatId);
    }

    @PostMapping("/seats/cancel/{seatId}")
    public String cancelReservation(@PathVariable Long seatId) {
        return seatService.cancelReservation(seatId);
    }
}
