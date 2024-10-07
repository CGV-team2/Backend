package com.clonemovie.Cinemaproject.repository;

import com.clonemovie.Cinemaproject.domain.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    // 상영 시간 조회
    List<Showtime> findByScreen_ScreenId(Long screenId);
}
