package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.LikeDao;
import ru.yandex.practicum.filmorate.exception.LikeException;

import java.util.List;

@Component
public class LikeDaoImpl implements LikeDao {
    private final JdbcTemplate jdbcTemplate;

    public LikeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private boolean isFilmHasLike(Integer filmId, Integer userId) {
        String sql = "SELECT COUNT(*) AS likes_amount FROM likes\n" +
                "WHERE LIKE_FILM_ID  = ? AND LIKE_USER_ID = ?";
        List<Integer> result = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("likes_amount"), filmId, userId);
        if (result.size() > 0) {
            return result.get(0) == 1;
        }
        return false;
    }

    @Override
    public boolean addLike(Integer filmId, Integer userId) {
        if (isFilmHasLike(filmId, userId)) {
            throw new LikeException("лайк уже существует для фильма с id=" + filmId + " пользователя c id=" + userId);
        }
        String sql = "INSERT INTO LIKES (LIKE_FILM_ID, LIKE_USER_ID)\n" +
                "VALUES (?, ?)";
        int result = jdbcTemplate.update(sql, filmId, userId);
        if (result > 0) {
            return true;
        }
        throw new LikeException("не удалось добавить лайк для фильма с id=" + filmId + " пользователя c id=" + userId);
    }

    @Override
    public List<Integer> findLikes(Integer filmId) {
        String sql = "select LIKE_USER_ID from LIKES\n" +
                "WHERE LIKE_FILM_ID=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("LIKE_USER_ID"), filmId);
    }

    @Override
    public boolean removeLike(Integer filmId, Integer userId) {
        String sql = "DELETE FROM LIKES\n" +
                "WHERE LIKE_FILM_ID = ? AND LIKE_USER_ID = ?";
        int result = jdbcTemplate.update(sql, filmId, userId);
        if (result > 0) {
            return true;
        } else
            throw new LikeException("не удалось удалить у фильма id=" + filmId + " и userId=" + userId);
    }
}