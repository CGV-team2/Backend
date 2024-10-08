package com.clonemovie.Cinemaproject.service;

import com.clonemovie.Cinemaproject.DTO.ShowTimeDTO;
import com.clonemovie.Cinemaproject.domain.Member;
import com.clonemovie.Cinemaproject.domain.Screen;
import com.clonemovie.Cinemaproject.domain.Seat;
import com.clonemovie.Cinemaproject.domain.Showtime;
import com.clonemovie.Cinemaproject.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ShowtimeService showtimeService;
    @Autowired
    private MemberService memberService;

    public List<Seat> getBookedSeats(Long showtimeId) {
        List<Seat> seats = seatRepository.findByShowtime_Id(showtimeId);
        List<Seat> bookedSeats = new ArrayList<>();
        for (Seat seat : seats) {
            bookedSeats.add(seat);
        }
        return bookedSeats;
    }

    public boolean isBookAvailable(Showtime showtime, String seatNumber) {
        Seat seat =  seatRepository.findByShowtime_IdAndSeatNumber(showtime.getId(), seatNumber);

        if(seat != null) {
            return true;
        }else{
            return false;
        }
    }

    public Seat bookSeat(Showtime showtime, String seatNumber, Member member, Screen screen) {
        if(isBookAvailable(showtime, seatNumber)) {
            return null;
        }

        Seat seat = new Seat(seatNumber, showtime, screen, member);

        return seatRepository.save(seat);
    }

    public String cancelReservation(Long seatId) {
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("좌석을 찾을 수 없습니다."));

        if (seat == null) {
            return "이 좌석은 예약되어 있지 않습니다.";
        }
        seatRepository.delete(seat);

        return "좌석 예약이 취소되었습니다.";
    }

}