package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.DTO.GenreDTO.*;
import com.clonemovie.Cinemaproject.domain.Genre;
import com.clonemovie.Cinemaproject.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping("/update")
    public List<ResponseGenre> updateGenres() {
        genreService.fetchAndSaveGenres();

        List<Genre> responseGenres = genreService.getAllGenres();
        return responseGenres.stream().
                map(ResponseGenre::new).
                collect(Collectors.toList());

    }
}
