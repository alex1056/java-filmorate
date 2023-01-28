package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User addFriend(Integer userId, Integer friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId); // проверяем существование друга

        if (user.addFriend(friendId) && friend.addFriend(userId)) {
            return userStorage.update(user);
        }
        throw new RuntimeException("Не получается добавить пользователю " + userId + " друга с id: " + friendId);
    }

    public User removeFriend(Integer userId, Integer friendId) {
        User user = userStorage.getUserById(userId);
        if (user.removeFriend(friendId)) {
            return userStorage.update(user);
        }
        throw new RuntimeException("Не получается удалить у пользователя " + userId + " друга с id: " + friendId);
    }

    public List<User> getFriendsList(Integer userId) {
        User user = userStorage.getUserById(userId);
        return userStorage.getUsersFromList(user.getFriends());
    }

    public List<User> getFriendsIntersection(Integer userId, Integer otherId) {
        User user = userStorage.getUserById(userId);
        User otherUser = userStorage.getUserById(otherId);
        List<Long> intersection = user.getFriends()
                .stream()
                .distinct()
                .filter(otherUser.getFriends()::contains)
                .collect(Collectors.toList());
        return userStorage.getUsersFromList(intersection);
    }

    public User getUserById(Integer userId) {
        return userStorage.getUserById(userId);
    }

    public User createUser(User user) {
        return userStorage.add(user);
    }

    public User updateUser(User user) {
        return userStorage.update(user);
    }

}
