package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.domain.Movie;
import com.clonemovie.Cinemaproject.domain.Screen;
import com.clonemovie.Cinemaproject.domain.Showtime;
import com.clonemovie.Cinemaproject.repository.MovieRepository;
import com.clonemovie.Cinemaproject.service.MovieServices;
import com.clonemovie.Cinemaproject.service.ScreenService;
import com.clonemovie.Cinemaproject.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;
    @Autowired
    private ScreenService screenService;
    @Autowired
    private MovieServices movieServices;


    @PostMapping("/save")
    public Showtime saveShowtime(@RequestParam String startTime, @RequestParam Long screenId, @RequestParam Long movieId) {
        // Screen과 Movie는 서비스에서 ID로 조회
        Screen screen = screenService.getScreenById(screenId);
        Movie movie = movieServices.getMovieByMovieId(movieId);
        LocalDateTime startDateTime = LocalDateTime.parse(startTime);
        return showtimeService.saveShowtime(startDateTime, screen, movie);
    }
}
