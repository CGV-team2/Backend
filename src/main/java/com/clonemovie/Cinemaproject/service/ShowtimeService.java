package com.clonemovie.Cinemaproject.service;

import com.clonemovie.Cinemaproject.domain.Movie;
import com.clonemovie.Cinemaproject.domain.Screen;
import com.clonemovie.Cinemaproject.domain.Showtime;
import com.clonemovie.Cinemaproject.repository.ShowtimeRepository;
import org.springdoc.core.parsers.ReturnTypeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private ReturnTypeParser genericReturnTypeParser;

    public Showtime saveShowtime(LocalDateTime startTime, Screen screen, Movie movie) {
        Showtime showtime = new Showtime(startTime, screen, movie);
        return showtimeRepository.save(showtime);  // 상영 시간 저장
    }

    public Showtime findById(Long id) {
        return showtimeRepository.findById(id).orElse(null);
    }
}
