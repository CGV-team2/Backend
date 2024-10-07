package com.clonemovie.Cinemaproject.repository;

import com.clonemovie.Cinemaproject.domain.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
    // 특정 극장에 속한 상영관을 조회하는 메서드
    List<Screen> findByTheater_TheaterId(Long theaterId);
}
