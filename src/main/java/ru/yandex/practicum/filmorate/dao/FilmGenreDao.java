package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;


import java.util.Collection;
import java.util.List;

public interface FilmGenreDao {

    boolean setFilmGenges(Integer filmId, List<Genre> genreList);

    Collection<Genre> getFilmGengeIdsByFilmId(Integer filmId);
}
