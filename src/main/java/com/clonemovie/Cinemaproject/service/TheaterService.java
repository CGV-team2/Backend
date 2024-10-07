package com.clonemovie.Cinemaproject.service;

import com.clonemovie.Cinemaproject.domain.Theater;
import com.clonemovie.Cinemaproject.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    public Theater getTheaterById(Long theaterId) {
        return theaterRepository.findById(theaterId).orElse(null);
    }

    public Theater saveTheater(String name, String location) {
        return theaterRepository.save(new Theater(name, location));
    }
}
