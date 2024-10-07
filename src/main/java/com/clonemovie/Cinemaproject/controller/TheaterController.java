package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.DTO.TheaterDTO.*;
import com.clonemovie.Cinemaproject.domain.Theater;
import com.clonemovie.Cinemaproject.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theaters")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @GetMapping
    public List<Theater> getAllTheaters() {
        return theaterService.getAllTheaters();
    }

    @GetMapping("/{theaterId}")
    public Theater getTheaterById(@PathVariable Long theaterId) {
        return theaterService.getTheaterById(theaterId);
    }

    @PostMapping
    public ResponseEntity<Theater> createTheater(@RequestBody TheaterRequest theaterRequest) {
        Theater theater = theaterService.saveTheater(theaterRequest.getName(), theaterRequest.getLocation());
        return ResponseEntity.ok(theater);
    }
}
