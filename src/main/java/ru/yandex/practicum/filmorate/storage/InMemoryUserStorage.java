package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int currentId = 0;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Override
    public List<User> findAll() {
        List<User> usersList = new ArrayList<>();

        for (Integer id : users.keySet()) {
            User currUser = users.get(id);
            usersList.add(currUser);
        }
        return usersList;
    }

    @Override
    public User add(User user) {
        Validator.userValidator(user, users, false);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setLoginAsName();
        }
        user.setId(++currentId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getUserById(Integer userId) {
        if (!users.containsKey(userId)) {
            log.info("Пользователь с id: {} не существует!\"", userId);
            throw new UserNotFoundException("Пользователь с id: {" + userId + "} не существует!");
        }
        return users.get(userId);
    }

    @Override
    public List<User> getUsersFromList(List<Long> list) {
        return list.stream().map(id -> users.get(id.intValue())).collect(Collectors.toList());
    }

    @Override
    public User update(User user) {
        Validator.userValidator(user, users, true);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setLoginAsName();
        }

        Integer id = user.getId();
        if (!users.containsKey(id)) {
            log.info("Пользователь с id: {} не существует!\"", id);
            throw new UserNotFoundException("Пользователь с id: {" + id + "} не существует!");
        }
        users.put(id, user);
        return user;
    }

    @Override
    public User remove(User user) {
        Validator.userValidator(user, users, true);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setLoginAsName();
        }

        Integer id = user.getId();
        if (!users.containsKey(id)) {
            log.info("Пользователь с id: {} не существует!\"", id);
            throw new UserNotFoundException("Пользователь с id: {" + id + "} не существует!");
        }
        return users.remove(id);
    }
}
