package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.helper.Helper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final List<Film> films = new ArrayList<>();
    private int currentId = 0;
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @Override
    public List<Film> findAll() {
        return films;
    }

    @Override
    public Film add(Film film) {
        Validator.filmValidator(film);
        film.setId(++currentId);
        films.add(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        Validator.filmValidator(film);
        Integer id = film.getId();
        if (id == null) {
            throw new ValidationException("Нужен фильм id");
        }

        if (!Helper.isFilmExists(film, films)) {
            log.info("Фильм с id: {} не существует!\"", id);
            throw new FilmNotFoundException("Фильм с id: {" + id + "} не существует!");
        }
        films.removeIf(currFilm -> currFilm.getId() == id);
        films.add(film);
        return film;
    }

    @Override
    public Film getFilmById(Integer filmId) {
        return films.stream()
                .filter(film -> film.getId().equals(filmId))
                .findFirst()
                .orElseThrow(() -> new FilmNotFoundException("Фильм с id: {" + filmId + "} не существует!"));
    }

    @Override
    public List<Film> getPopular(Integer count) {
        return films.stream()
                .sorted((p0, p1) -> -1 * (p0.getLikes().size() - p1.getLikes().size()))
                .skip(0) // beginning index 0
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Film remove(Film film) {
        Validator.filmValidator(film);
        Integer id = film.getId();
        if (id == null) {
            throw new ValidationException("Нужен фильм id");
        }

        if (!Helper.isFilmExists(film, films)) {
            log.info("Фильм с id: {} не существует!\"", id);
            throw new FilmNotFoundException("Фильм с id: {" + id + "} не существует!");
        }
        films.removeIf(currFilm -> currFilm.getId() == id);
        return film;
    }
}
