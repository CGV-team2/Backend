package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.DTO.ShowTimeDTO.*;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
    public ShowTimeResponse saveShowtime(@RequestParam String startTime,
                                         @RequestParam Long screenId,
                                         @RequestParam Long movieId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime;

        try {
            startDateTime = LocalDateTime.parse(startTime, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid start time format. Use 'yyyy-MM-dd HH:mm'.");
        }

        Screen screen = screenService.getScreenById(screenId);
        if(screen == null) return null;
        Movie movie = movieServices.getMovieByMovieId(movieId);
        if(movie == null) return null;
        Showtime showtime = showtimeService.saveShowtime(startDateTime, screen, movie);
        if(showtime == null) return null;
        return new ShowTimeResponse(showtime);
    }
}
