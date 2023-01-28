package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findAll() {
        return (List<User>) userDao.findAll();
    }

    public User addFriend(Integer userId, Integer friendId) {
        return userDao.addFriend(userId, friendId);
    }

    public User removeFriend(Integer userId, Integer friendId) {
        return userDao.removeFriend(userId, friendId);
    }

    public List<User> getFriendsList(Integer userId) {
        return (List<User>) userDao.getFriendsList(userId);
    }

    public List<User> getFriendsIntersection(Integer userId, Integer otherId) {
        return (List<User>) userDao.getFriendsIntersection(userId, otherId);
    }

    public Optional<User> findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    public Optional<User> createUser(User user) {
        return userDao.addUser(user);
    }

    public Optional<User> updateUser(User user) {
        return userDao.updateUser(user);
    }
}
