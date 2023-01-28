package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.helper.Helper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class MpaDaoImpl implements MpaDao {
    private final JdbcTemplate jdbcTemplate;

    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa findMpaById(Integer mpaId) {
        String sql = "select * from mpa\n" +
                "where mpa_id=?";
        List<Mpa> result = jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs), mpaId);
        if (result.size() == 1) {
            return result.get(0);
        }
        throw new MpaNotFoundException("id=" + mpaId);
    }

    @Override
    public Collection<Mpa> findAll() {
        String sql = "select * from mpa";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs));
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException {
        return new Mpa(
                rs.getInt("mpa_id"),
                Helper.trimNullableString(rs.getString("name"))
        );
    }

    @Override
    public String getMpaNameById(Integer mpaId) {
        return findMpaById(mpaId).getName();
    }

    @Override
    public Integer getMpaIdByName(String mpaName) {
        String sql = "select * from mpa\n" +
                "where name=?";
        List<Mpa> result = jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs), mpaName);
        if (result.size() == 1) {
            return result.get(0).getId();
        }
        throw new MpaNotFoundException("name=" + mpaName);
    }
}