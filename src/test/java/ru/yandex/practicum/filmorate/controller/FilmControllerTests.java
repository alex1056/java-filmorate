package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest
public class FilmControllerTests {
    Film filmCompletedContent = null;
    
    @Test
    void filmCorrectDataTests() {
        FilmController filmController = new FilmController();
        filmCompletedContent = new Film(null,"Титаник", "Вода и только",  LocalDate.of(2000,5,3), 120);
        ResponseEntity<?> result = filmController.create(filmCompletedContent);
        Film film = (Film) result.getBody();
        Assertions.assertEquals(result.getStatusCode().value(),200, "Ответ сервера должен быть 200");
        Assertions.assertEquals(film.getId(),1, "Id должен быть 1");
        film.setId(null);
        Assertions.assertTrue(film.equals(filmCompletedContent), "Экземпляр film возвращенный из контроллера не равен первоначальному");
    }
    
    @Test
    void filmIncorrectDataTests() {
        FilmController filmController = new FilmController();
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            filmCompletedContent = new Film(null,null, null,  null, null);
            filmController.create(filmCompletedContent);
        });
        Assertions.assertEquals("Поле не должно быть пустым.", thrown.getMessage());
    
        thrown = Assertions.assertThrows(ValidationException.class, () -> {
            filmCompletedContent = new Film(null,"Титаник", "Вода и только",  LocalDate.of(1700,5,3), 120);
            filmController.create(filmCompletedContent);
        });
        Assertions.assertEquals("Дата релиза — не должна быть раньше 28 декабря 1895 года.", thrown.getMessage());
    }
    
    @Test
    void filmUpdateDataTests() {
        FilmController filmController = new FilmController();
        filmCompletedContent = new Film(null,"Титаник", "Вода и только",  LocalDate.of(2000,5,3), 120);
        ResponseEntity<?> result = filmController.create(filmCompletedContent);
        Film film = (Film) result.getBody();
        Integer id = film.getId();
        Film filmUpdatedContent = new Film(id,"Титаник-Updated", "Вода и только",  LocalDate.of(2000,5,3), 120);
        result = filmController.update(filmUpdatedContent);
        film = (Film) result.getBody();
        Assertions.assertTrue(film.getName().equals("Титаник-Updated"), "Поле name должно быть обновленным");
    }
}

