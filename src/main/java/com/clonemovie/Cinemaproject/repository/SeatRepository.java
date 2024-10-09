package com.clonemovie.Cinemaproject.repository;

import com.clonemovie.Cinemaproject.domain.Member;
import com.clonemovie.Cinemaproject.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findByShowTimeAndSeatNumberAndMovieNameAndScreenNameAndTheaterName(LocalDateTime showTime, String seatNumber, String movieName, String screenName, String theaterName);
    List<Seat> findSeatsByShowTimeAndMovieNameAndScreenNameAndTheaterName(LocalDateTime showTime, String movieName, String screenName, String theaterName);
    List<Seat> findSeatsByMember(Member member);
}
