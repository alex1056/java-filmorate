package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {

    Optional<User> findUserById(Integer id);

    Collection<User> findAll();

    Optional<User> addUser(User user);

    Optional<User> updateUser(User user);

    Collection<User> getFriendsIntersection(Integer userId, Integer otherId);

    User addFriend(Integer userId, Integer friendId);

    User removeFriend(Integer userId, Integer friendId);

    Collection<User> getFriendsList(Integer userId);


}