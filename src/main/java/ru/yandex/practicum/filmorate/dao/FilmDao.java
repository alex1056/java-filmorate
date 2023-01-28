package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmDao {
    Optional<Film> getFilmById(Integer filmId);

    Optional<Film> addFilm(Film film);

    Collection<Film> findAll();

    Optional<Film> updateFilm(Film film);

    Collection<Film> getPopular(Integer count);
}
