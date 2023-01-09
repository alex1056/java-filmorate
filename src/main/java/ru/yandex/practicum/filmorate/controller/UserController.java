package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.helper.ResponseBuilder;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.Validator;
import ru.yandex.practicum.filmorate.helper.Helper;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int currentId = 0;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    @GetMapping
    public List<User> findAll() {
        List<User> usersList = new ArrayList<>();
        
        for (Integer id: users.keySet()) {
            User currUser = users.get(id);
            usersList.add(currUser);
        }
        return usersList;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user) {
        User addedUser;
        Validator.userValidator(user, users, false);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setLoginAsName();
        }
        user.setId(++currentId);
        users.put(user.getId(), user);
        addedUser = users.get(user.getId());
        return new ResponseEntity<>(addedUser, HttpStatus.OK);
    }
    
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody User user) {
        User addedUser;
        Validator.userValidator(user, users, true);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setLoginAsName();
        }

        Integer id = user.getId();
        if (!users.containsKey(id)) {
            log.info("Пользователь с id: {} не существует!\"", id);
            ResponseBuilder response = new ResponseBuilder("Пользователь с таким id не существует!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        users.put(id, user);
        addedUser = users.get(id);
        return new ResponseEntity<>(addedUser, HttpStatus.OK);
        }
}
