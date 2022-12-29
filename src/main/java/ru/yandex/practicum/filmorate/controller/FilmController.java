package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.helper.ResponseBuilder;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import ru.yandex.practicum.filmorate.helper.Helper;
import ru.yandex.practicum.filmorate.validator.Validator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final List<Film> films = new ArrayList<>();
    private int currentId = 0;
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    
    @GetMapping
    public List<Film> findAll() {
        return films;
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Film film) {
        log.info("Получен POST запрос /film {}",film);
        Validator.filmValidator(film);
        film.setId(++currentId);
        films.add(film);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }
    
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Film film) {
        log.info("Получен PUT запрос /film {}", film);
        Validator.filmValidator(film);
        Integer id = film.getId();
        if (id == null) {
            throw new ValidationException("Нужен фильм id");
        }

        if (!Helper.isFilmExists(film, films)) {
            log.info("Фильм с id: {} не существует!\"", id);
            ResponseBuilder response = new ResponseBuilder("Фильм с таким id не существует!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        films.removeIf(currFilm -> currFilm.getId() == id);
        films.add(film);
        return new ResponseEntity<>(film, HttpStatus.OK);
    }
}
