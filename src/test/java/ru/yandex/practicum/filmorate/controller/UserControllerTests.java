package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserControllerTests {
    
    User userCompletedContent = null;
    
    @Test
    void filmCorrectDataTests() {
        UserController userController = new UserController();
        userCompletedContent = new User(null,"asd@asd.ru", "Титаник", "Вася",  LocalDate.of(2000,5,3));
        ResponseEntity<?> result = userController.create(userCompletedContent);
        User user = (User) result.getBody();
        Assertions.assertEquals(result.getStatusCode().value(),200, "Ответ сервера должен быть 200");
        Assertions.assertEquals(user.getId(),1, "Id должен быть 1");
        user.setId(null);
        Assertions.assertTrue(user.equals(userCompletedContent), "Экземпляр user возвращенный из контроллера не равен первоначальному");
    }
    
    @Test
    void filmIncorrectDataTests() {
        UserController userController = new UserController();
        userCompletedContent = new User(null,null, null,  null, null);
        ResponseEntity<?> result = userController.create(userCompletedContent);
        Assertions.assertEquals(result.getStatusCode().value(),400, "Ответ сервера должен быть 400 (все поля null)");
        userCompletedContent = new User(null,null, "Титаник", "Вася",  LocalDate.of(2000,5,3));
        result = userController.create(userCompletedContent);
        Assertions.assertEquals(result.getStatusCode().value(),400, "Ответ сервера должен быть 400 (не заполнено поле email)");
        userCompletedContent = new User(null,"asd@asd.ru", "Титаник", "Вася",  LocalDate.of(2025,5,3));
        result = userController.create(userCompletedContent);
        Assertions.assertEquals(result.getStatusCode().value(),400, "Ответ сервера должен быть 400 (дата рождения не должна быть в будущем)");
    }
    
    @Test
    void filmUpdateDataTests() {
        UserController userController = new UserController();
        userCompletedContent = new User(null,"asd@asd.ru", "Титаник", "Вася",  LocalDate.of(2000,5,3));
        ResponseEntity<?> result = userController.create(userCompletedContent);
        User user = (User) result.getBody();
        Integer id = user.getId();
        User userUpdatedContent = new User(id,"asd@asd-upd.ru", "Титаник", "Вася",  LocalDate.of(2000,5,3));
        result = userController.update(userUpdatedContent);
        user = (User) result.getBody();
        Assertions.assertTrue(user.getEmail().equals("asd@asd-upd.ru"), "Поле email должно быть обновленным");
    }
}
