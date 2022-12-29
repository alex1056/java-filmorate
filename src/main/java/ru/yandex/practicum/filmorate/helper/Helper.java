package ru.yandex.practicum.filmorate.helper;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Helper {
    public static boolean isUserEmailExists(User user, Map<Integer,User> users) {
       List<User> usersList = new ArrayList<>(users.values());
       if(usersList.size()>0) {
           return usersList.stream().filter(currUser->currUser.getEmail().equals(user.getEmail())).count() != 0;
       }
       return false;
    }
    
    public static boolean isFilmExists(Film film, List<Film> films) {
        if(films.size()>0) {
            return films.stream().filter(currFilm->currFilm.getId() == film.getId()).count() != 0;
        }
        return false;
    }
}
