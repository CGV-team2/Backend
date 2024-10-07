package com.clonemovie.Cinemaproject.service;

import com.clonemovie.Cinemaproject.domain.Screen;
import com.clonemovie.Cinemaproject.domain.Theater;
import com.clonemovie.Cinemaproject.repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    public Screen saveScreen(String name, Theater theater) {
        Screen screen = new Screen(name, theater);
        return screenRepository.save(screen);  // 상영관 저장
    }

    public List<Screen> getScreensByTheaterId(Long theaterId) {
        return screenRepository.findByTheater_TheaterId(theaterId);
    }

    public Screen getScreenById(Long id) {
        return screenRepository.findById(id).orElse(null);
    }
}
