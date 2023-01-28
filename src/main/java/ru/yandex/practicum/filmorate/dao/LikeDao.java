package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LikeDao {
    boolean addLike(Integer filmId, Integer userId);

    List<Integer> findLikes(Integer filmId);

    boolean removeLike(Integer filmId, Integer userId);
}
