package com.clonemovie.Cinemaproject.repository;

import com.clonemovie.Cinemaproject.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByMovieId(Long movieId);
    // 더 추가 할 것?
}