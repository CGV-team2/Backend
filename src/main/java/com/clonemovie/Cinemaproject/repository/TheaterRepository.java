package com.clonemovie.Cinemaproject.repository;

import com.clonemovie.Cinemaproject.domain.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    // 필요한 경우 추가적인 쿼리 메서드를 정의할 수 있습니다.
}