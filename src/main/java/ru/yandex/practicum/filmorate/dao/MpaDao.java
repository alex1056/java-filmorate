package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.Optional;

public interface MpaDao {

    Mpa findMpaById(Integer mpaId);

    String getMpaNameById(Integer mpaId);

    Integer getMpaIdByName(String mpaName);

    Collection<Mpa> findAll();

}