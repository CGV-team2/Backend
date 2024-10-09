package com.clonemovie.Cinemaproject.service;

import com.clonemovie.Cinemaproject.domain.*;
import com.clonemovie.Cinemaproject.repository.MemberRepository;
import com.clonemovie.Cinemaproject.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    public List<Seat> getBookedSeats(LocalDateTime showtime, String screenName, String movieName, String theaterName) {
        List<Seat> seats = seatRepository.findSeatsByShowTimeAndMovieNameAndScreenNameAndTheaterName(showtime, movieName, screenName, theaterName);
        List<Seat> bookedSeats = new ArrayList<>();
        for (Seat seat : seats) {
            bookedSeats.add(seat);
        }
        return bookedSeats;
    }

    public boolean isBookAvailable(LocalDateTime showtime, String seatNumber, String movieName, String theaterName, String screenName) {
        Seat seat =  seatRepository.findByShowTimeAndSeatNumberAndMovieNameAndScreenNameAndTheaterName(showtime, seatNumber, movieName, screenName, theaterName);

        if(seat != null) {
            return true;
        }else{
            return false;
        }
    }

    public List<Seat> getSeatByMember(Member member) {
        return seatRepository.findSeatsByMember(member);
    }

    public Seat bookSeat(LocalDateTime showtime, String seatNumber, Member member, String screenName, String movieName, String theaterName) {
        if(isBookAvailable(showtime, seatNumber, movieName, screenName, theaterName)) {
            return null;
        }

        Seat seat = new Seat(seatNumber, showtime, screenName, member, movieName, theaterName);

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