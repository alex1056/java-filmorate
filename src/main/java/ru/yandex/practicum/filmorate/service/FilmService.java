package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.impl.FilmDaoImpl;
import ru.yandex.practicum.filmorate.dao.impl.LikeDaoImpl;
import ru.yandex.practicum.filmorate.dao.impl.UserDaoImpl;
import ru.yandex.practicum.filmorate.exception.FilmNotCreatedException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.FilmNotUpdatedException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    private final FilmDaoImpl filmDao;
    private final LikeDaoImpl likeDao;
    private final UserDaoImpl userDao;

    @Autowired
    public FilmService(FilmDaoImpl filmDao, LikeDaoImpl likeDao, UserDaoImpl userDao) {
        this.filmDao = filmDao;
        this.likeDao = likeDao;
        this.userDao = userDao;
    }

    public Film getFilmById(Integer filmId) {
        Optional<Film> filmOpt = filmDao.getFilmById(filmId);
        if (filmOpt.isPresent()) {
            return filmOpt.get();
        } else {
            throw new FilmNotFoundException("Фильм не найден с id=" + filmId);
        }
    }

    public List<Film> findAll() {
        return (List<Film>) filmDao.findAll();
    }

    public Film create(Film film) {
        Validator.filmValidator(film);
        Optional<Film> filmOpt = filmDao.addFilm(film);
        if (filmOpt.isPresent()) {
            return filmOpt.get();
        } else {
            throw new FilmNotCreatedException("фильм не создан");
        }
    }

    public Film update(Film film) {
        Validator.filmValidator(film);
        Optional<Film> filmOpt = filmDao.updateFilm(film);
        if (filmOpt.isPresent()) {
            return filmOpt.get();
        }
        throw new FilmNotUpdatedException("id= " + film.getId());
    }

    public List<Film> getPopular(Integer count) {
        return (List<Film>) filmDao.getPopular(count);
    }

    public Optional<Film> addLike(Integer filmId, Integer userId) {
        Optional<Film> filmOpt = filmDao.getFilmById(filmId);
        Optional<User> userOpt = userDao.findUserById(userId);
        if (filmOpt.isEmpty()) {
            throw new FilmNotFoundException("фильм не найден с filmId=" + filmId);
        }
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("с id=" + userId);
        }
        if (likeDao.addLike(filmId, userId)) {
            return filmDao.getFilmById(filmId);
        }
        return Optional.of(null);
    }

    public Film removeLike(Integer filmId, Integer userId) {
        filmDao.getFilmById(filmId);
        userDao.findUserById(userId);
        if (likeDao.removeLike(filmId, userId)) {
            Optional<Film> filmOpt = filmDao.getFilmById(filmId);
            if (filmOpt.isPresent()) {
                return filmOpt.get();
            }
        }
        return null;
    }
}
