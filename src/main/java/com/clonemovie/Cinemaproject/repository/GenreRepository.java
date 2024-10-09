package com.clonemovie.Cinemaproject.repository;

import com.clonemovie.Cinemaproject.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
