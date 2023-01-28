package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.helper.Helper;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class FilmDaoImpl implements FilmDao {
    private final Logger log = LoggerFactory.getLogger(ru.yandex.practicum.filmorate.dao.impl.UserDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final FilmGenreDaoImpl filmGenreDao;
    private final LikeDaoImpl likeDao;
    private final MpaDaoImpl mpaDao;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate, FilmGenreDaoImpl filmGenreDao, LikeDaoImpl likeDao, MpaDaoImpl mpaDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmGenreDao = filmGenreDao;
        this.likeDao = likeDao;
        this.mpaDao = mpaDao;
    }

    @Override
    public Optional<Film> getFilmById(Integer filmId) {
        String sql = "SELECT f.FILM_ID, f.NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, f.MPA_ID, m.NAME\n" +
                "FROM FILMS f  \n" +
                "LEFT JOIN MPA AS m ON m.MPA_ID = f.MPA_ID\n" +
                "WHERE f.film_id = ?";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sql, filmId);
        if (filmRows.next()) {
            Film film = new Film(
                    filmRows.getInt("film_id"),
                    Helper.trimNullableString(filmRows.getString("name")),
                    Helper.trimNullableString(filmRows.getString("description")),
                    filmRows.getDate("release_date").toLocalDate(),
                    filmRows.getInt("duration"),
                    new Mpa(filmRows.getInt("MPA_ID"), Helper.trimNullableString(filmRows.getString(7))),
                    (List<Genre>) filmGenreDao.getFilmGengeIdsByFilmId(filmId),
                    likeDao.findLikes(filmId)
            );
            log.info("Найден фильм: {} {}", film.getId(), film.getName());
            return Optional.of(film);
        } else {
            log.info("Фильм с идентификатором {} не найден.", filmId);
            throw new FilmNotFoundException("id=" + filmId);
        }
    }

    @Override
    public Optional<Film> addFilm(Film film) {
        Integer mpaId = film.getMpa().getId();
        mpaDao.findMpaById(mpaId);
        String sql = "INSERT INTO films (name,description,release_date,duration, mpa_id) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                mpaId
        );

        sql = "SELECT f.FILM_ID, f.NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, f.MPA_ID, m.NAME\n" +
                "FROM FILMS f  \n" +
                "LEFT JOIN MPA AS m ON m.MPA_ID = f.MPA_ID\n" +
                "WHERE f.name=? AND f.description=?";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sql, film.getName(), film.getDescription());
        Film filmResult;
        if (filmRows.next()) {
            Integer filmIdResult = filmRows.getInt("film_id");
            filmGenreDao.setFilmGenges(filmIdResult, film.getGenres());
            filmResult = new Film(
                    filmIdResult,
                    Helper.trimNullableString(filmRows.getString("name")),
                    Helper.trimNullableString(filmRows.getString("description")),
                    filmRows.getDate("release_date").toLocalDate(),
                    filmRows.getInt("duration"),
                    new Mpa(filmRows.getInt("MPA_ID"), Helper.trimNullableString(filmRows.getString(7))),
                    (List<Genre>) filmGenreDao.getFilmGengeIdsByFilmId(filmIdResult),
                    likeDao.findLikes(filmIdResult)
            );
            return Optional.of(filmResult);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Film> updateFilm(Film film) {
        Integer mpaId = film.getMpa().getId();
        getFilmById(film.getId());
        mpaDao.findMpaById(mpaId);
        String sql = "UPDATE films SET name=?,description=?,release_date=?,duration=?,mpa_id=? WHERE film_id=?";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                mpaId,
                film.getId()
        );
        filmGenreDao.setFilmGenges(film.getId(), film.getGenres());
        Optional<Film> filmOpt = getFilmById(film.getId());
        return filmOpt;
    }

    @Override
    public Collection<Film> getPopular(Integer count) {
        String sql = "SELECT f.FILM_ID, f.NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, f.MPA_ID, m.NAME, l.like_amount\n" +
                "FROM FILMS f \n" +
                "LEFT JOIN \n" +
                "(SELECT LIKE_FILM_ID, COUNT(LIKE_USER_ID) AS like_amount\n" +
                "FROM LIKES l \n" +
                "GROUP BY LIKE_FILM_ID) AS l\n" +
                "ON f.FILM_ID = l.LIKE_FILM_ID \n" +
                "LEFT JOIN MPA AS m ON m.MPA_ID = f.MPA_ID\n" +
                "ORDER BY l.like_amount DESC\n" +
                "LIMIT ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), count);
    }

    @Override
    public Collection<Film> findAll() {
        String sql = "SELECT f.FILM_ID, f.NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, f.MPA_ID, m.NAME\n" +
                "FROM FILMS f\n" +
                "LEFT JOIN MPA AS m ON m.MPA_ID = f.MPA_ID";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Integer filmIdResult = rs.getInt("film_id");
        return new Film(
                filmIdResult,
                Helper.trimNullableString(rs.getString("name")),
                Helper.trimNullableString(rs.getString("description")),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                new Mpa(rs.getInt("MPA_ID"), Helper.trimNullableString(rs.getString(7))),
                (List<Genre>) filmGenreDao.getFilmGengeIdsByFilmId(filmIdResult),
                likeDao.findLikes(filmIdResult)
        );
    }
}
