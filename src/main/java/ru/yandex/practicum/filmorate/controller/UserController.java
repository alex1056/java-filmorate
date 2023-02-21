package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        log.info("Получен GET запрос /users/{id}/friends, id={}", id);
        return userService.getFriendsList(id);
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Integer id) {
        log.info("Получен GET запрос /users/{id}, id={}", id);
        return userService.findUserById(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getFriendsIntersections(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Получен GET запрос /users/{id}/friends/common/{otherId}, id={}, otherId={}", id, otherId);
        return userService.getFriendsIntersection(id, otherId);
    }

    @GetMapping
    public List<User> findAll() {
        log.info("Получен GET запрос /users");
        return userService.findAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Получен POST запрос /users, user={}", user);
        Optional<User> userOptional = userService.createUser(user);
        return userOptional.orElse(null);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Получен PUT запрос /users/{id}/friends/{friendId}, id={}, friendId={}", id, friendId);
        User user = userService.addFriend(id, friendId);
        if (user != null) {
            return user;
        } else {
            throw new RuntimeException("Не получается добавить пользователю " + id + " друга с id: " + friendId);
        }
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Получен PUT запрос /users, user={}", user);
        Optional<User> userOptional = userService.updateUser(user);
        return userOptional.orElse(null);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        User user = userService.removeFriend(id, friendId);
        return user;
    }
}
