package com.clonemovie.Cinemaproject.service;

import com.clonemovie.Cinemaproject.domain.Seat;
import com.clonemovie.Cinemaproject.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getAvailableSeats(Long showtimeId) {
        List<Seat> seats = seatRepository.findByShowtime_Id(showtimeId);
        List<Seat> availableSeats = new ArrayList<>();
        for (Seat seat : seats) {
            if (!seat.isReserved()) {
                availableSeats.add(seat); // 예약되지 않은 좌석만 추가
            }
        }
        return availableSeats;
    }


    public boolean isReservationAvailable(Long showtimeId) {
        List<Seat> seats = seatRepository.findByShowtime_Id(showtimeId);
        for (Seat seat : seats) {
            if (!seat.isReserved()) {
                return true;
            }
        }
        return false;
    }

    public String reserveSeat(Long showtimeId, Long seatId) {
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("좌석을 찾을 수 없습니다."));

        if (seat.isReserved()) {
            return "이미 예약된 좌석입니다.";
        }

        seat.setReserved(true);
        seatRepository.save(seat);

        return "좌석 예약이 완료되었습니다.";
    }

    public String cancelReservation(Long seatId) {
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("좌석을 찾을 수 없습니다."));

        if (!seat.isReserved()) {
            return "이 좌석은 예약되어 있지 않습니다.";
        }

        seat.setReserved(false);
        seatRepository.save(seat);

        return "좌석 예약이 취소되었습니다.";
    }

}