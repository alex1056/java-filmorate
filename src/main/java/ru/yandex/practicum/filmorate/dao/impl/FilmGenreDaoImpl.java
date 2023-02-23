package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.helper.Helper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilmGenreDaoImpl implements FilmGenreDao {
    private final Logger log = LoggerFactory.getLogger(FilmGenreDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final GenreDaoImpl genreDao;

    public FilmGenreDaoImpl(JdbcTemplate jdbcTemplate, GenreDaoImpl genreDao) {

        this.jdbcTemplate = jdbcTemplate;
        this.genreDao = genreDao;
    }

    @Override
    public Collection<Genre> getFilmGengeIdsByFilmId(Integer filmId) {
        String sql = "SELECT FG_GENRE_ID, g.name\n" +
                "FROM FILM_GENRE AS fg\n" +
                "LEFT JOIN GENRE AS g ON g.genre_id = fg.FG_GENRE_ID\n" +
                "WHERE FG_FILM_ID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Genre(rs.getInt("FG_GENRE_ID"),
                Helper.trimNullableString(rs.getString("NAME"))
        ), filmId);
    }

    @Override
    public boolean setFilmGenges(Integer filmId, List<Genre> genreList) {
        String sql = "INSERT INTO film_genre (FG_FILM_ID, FG_GENRE_ID) VALUES";
        StringBuilder sb = new StringBuilder(sql);
        if (genreList != null) {
            if (genreList.size() > 0) {
                List<Genre> uniqueGenreList = genreList.stream().distinct().collect(Collectors.toList());
                for (int i = 0; i < uniqueGenreList.size(); i++) {
                    Integer genreId = uniqueGenreList.get(i).getId();
                    genreDao.findGenreById(genreId);
                    sb.append(" (" + filmId + "," + genreId + ")");
                    if (i < uniqueGenreList.size() - 1) {
                        sb.append(",");
                    }
                }
                sql = sb.toString();
                removeFilmGenres(filmId);
                Integer result = jdbcTemplate.update(sql);
                if (result > 0) {
                    return true;
                }
            } else {
                removeFilmGenres(filmId);
            }
        }
        return false;
    }

    private Integer removeFilmGenres(Integer filmId) {
        String sql = "DELETE FROM film_genre\n" +
                "WHERE fg_film_id = ?";
        return jdbcTemplate.update(sql, filmId);
    }
}