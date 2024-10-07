package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.domain.Screen;
import com.clonemovie.Cinemaproject.domain.Theater;
import com.clonemovie.Cinemaproject.repository.ScreenRepository;
import com.clonemovie.Cinemaproject.service.ScreenService;
import com.clonemovie.Cinemaproject.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/screens")
public class ScreenController {

    @Autowired
    private ScreenService screenService;
    @Autowired
    private ScreenRepository screenRepository;
    @Autowired
    private TheaterService theaterService;

    @GetMapping("/theater/{theaterId}")
    public List<Screen> getScreensByTheater(@PathVariable Long theaterId) {
        return screenService.getScreensByTheaterId(theaterId);
    }

    @PostMapping("/save")
    public Screen saveScreen(@RequestParam String name, @RequestParam Long theaterId) {
        Theater theater = theaterService.getTheaterById(theaterId);
        if(theater == null){
            return null;
        }
        return screenService.saveScreen(name, theater);
    }
}
