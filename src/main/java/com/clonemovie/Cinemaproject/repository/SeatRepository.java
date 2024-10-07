package com.clonemovie.Cinemaproject.repository;

import com.clonemovie.Cinemaproject.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByShowtime_Id(Long showtimeId);
}
