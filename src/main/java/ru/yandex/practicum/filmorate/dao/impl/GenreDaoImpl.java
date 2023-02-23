package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.LikeDao;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.LikeException;
import ru.yandex.practicum.filmorate.helper.Helper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class GenreDaoImpl implements GenreDao {
    private final JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre findGenreById(Integer genreId) {
        String sql = "select * from genre\n" +
                "where genre_id=?";
        List<Genre> result = jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), genreId);
        if (result.size() == 1) {
            return result.get(0);
        }
        throw new GenreNotFoundException("id=" + genreId);
    }

    @Override
    public Collection<Genre> findAll() {
        String sql = "select * from genre";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        return new Genre(
                rs.getInt("genre_id"),
                Helper.trimNullableString(rs.getString("name"))
        );
    }
}