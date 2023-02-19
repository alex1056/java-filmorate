package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    abstract List<User> findAll();

    abstract User add(User user);

    abstract User getUserById(Integer userId);

    abstract User update(User user);

    abstract User remove(User user);

    abstract List<User> getUsersFromList(List<Long> list);
}

