package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    abstract List<Film> findAll();

    abstract Film add(Film film);

    abstract Film update(Film film);

    abstract Film remove(Film film);

    abstract Film getFilmById(Integer filmId);

    abstract List<Film> getPopular(Integer count);
}
