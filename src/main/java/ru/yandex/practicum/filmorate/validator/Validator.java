package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {
    public static void filmValidator(Film film) {

        if (film.getName() == null || film.getDescription() == null || film.getReleaseDate() == null || film.getDuration() == null) {
            throw new ValidationException("Поле не должно быть пустым.");
        }
        if (film.getReleaseDate() != null && !film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза — не должна быть раньше 28 декабря 1895 года.");
        }
    }

    public static void userValidatorDB(User user) {

        if (user.getEmail() == null || user.getLogin() == null || user.getBirthday() == null) {
            throw new ValidationException("Поле не должно быть пустым.");
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения — не должна быть в будущем.");
        }
    }
}
