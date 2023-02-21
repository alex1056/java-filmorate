package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/films")
public class FilmController {
    private FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10", required = false) Integer count) {
        log.info("Получен Get запрос /film/popular, count= {}", count);
        return filmService.getPopular(count);
    }

    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        log.info("Получен Get запрос /film {}", id);
        return filmService.getFilmById(id);
    }


    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен POST запрос /film {}", film);
        return filmService.create(film);
    }


    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Получен Put запрос /film/{id}/like/{userId}, id={}, userId={}", id, userId);
        Optional<Film> film = filmService.addLike(id, userId);
        return film.orElse(null);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен PUT запрос /film {}", film);
        return filmService.update(film);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Получен Delete запрос /film/{id}/like/{userId}, id={}, userId={}", id, userId);
        return filmService.removeLike(id, userId);
    }
}
