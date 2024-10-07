package com.clonemovie.Cinemaproject.service;

import com.clonemovie.Cinemaproject.domain.Member;
import com.clonemovie.Cinemaproject.domain.Reservation;
import com.clonemovie.Cinemaproject.domain.Showtime;
import com.clonemovie.Cinemaproject.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation makeReservation(Showtime showtime, String seatNumber, Member member) {
        Reservation reservation = new Reservation(showtime, seatNumber, member);
        return reservationRepository.save(reservation);  // 예매 정보 저장
    }

    public boolean isSeatAvailable(Long showtimeId, String seatNumber) {
        List<Reservation> reservations = reservationRepository.findByShowtimeId(showtimeId);
        for (Reservation reservation : reservations) {
            if (reservation.getSeatNumber().equals(seatNumber)) {
                return false;  // 이미 예약된 좌석
            }
        }
        return true;  // 예약 가능
    }
}
