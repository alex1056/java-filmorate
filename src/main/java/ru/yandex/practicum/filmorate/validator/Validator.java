package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.helper.Helper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Map;

public class Validator {
    public static void filmValidator(Film film) {
        if(film.getName() == null || film.getName().isEmpty()) {
            throw new ValidationException("Ошибка: название не должно быть пустым.");
        }
    
        if(film.getDescription() != null && film.getDescription().length() > 200) {
            throw new ValidationException("Ошибка: описание не должно быть больше 200 символов.");
        }
    
        if(film.getReleaseDate() != null && !film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28)) ) {
            throw new ValidationException("Ошибка: дата релиза — не должна быть раньше 28 декабря 1895 года.");
        }
    
        if(film.getDuration() != null && !(film.getDuration() > 0)) {
            throw new ValidationException("Ошибка: продолжительность должна быть больше 0.");
        }
    }
    public static void userValidator(User user, Map<Integer,User> users, boolean isUpdatingFlag) {
    
        if(user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ValidationException("Пустой email.");
        }
    
        if(!user.getEmail().contains("@")) {
            throw new ValidationException("Email не содержит знак @.");
        }
    
        if(user.getLogin() == null || user.getLogin().isBlank()) {
            throw new ValidationException("Пустой login.");
        }
    
        if(user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения — не должна быть в будущем.");
        }
        if(!isUpdatingFlag) {
            if(users.containsKey(user.getId()) || Helper.isUserEmailExists(user, users)) {
                throw new ValidationException("Пользователь уже существует.");
            }
        }
        
    }
}
