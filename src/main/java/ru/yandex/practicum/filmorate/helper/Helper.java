package ru.yandex.practicum.filmorate.helper;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public class Helper {
    public static boolean isUserEmailExists(User user, Map<Integer, User> users) {
        List<User> usersList = new ArrayList<>(users.values());
        if (usersList.size() > 0) {
            return usersList.stream().filter(currUser -> currUser.getEmail().equals(user.getEmail())).count() != 0;
        }
        return false;
    }

    public static boolean isFilmExists(Film film, HashMap<Integer, Film> films) {

        if (films.get(film.getId()) != null) {
            return true;
        }
        return false;
    }
}
