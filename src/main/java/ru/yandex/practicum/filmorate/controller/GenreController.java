package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;


@RestController
@RequestMapping("/genres")
public class GenreController {
    private GenreService genreService;


    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    private static final Logger log = LoggerFactory.getLogger(GenreController.class);

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable Integer id) {
        log.info("Получен GET запрос /genres/{id}, id= {}", id);
        return genreService.findGenreById(id);
    }

    @GetMapping
    public List<Genre> findAll() {
        log.info("Получен GET запрос /genres");
        return genreService.findAll();
    }
}
