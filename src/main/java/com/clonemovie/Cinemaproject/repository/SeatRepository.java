package com.clonemovie.Cinemaproject.repository;

import com.clonemovie.Cinemaproject.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findByShowTimeAndSeatNumber(LocalDateTime showTime, String seatNumber);
    List<Seat> findSeatsByShowTime(LocalDateTime showTime);
}
