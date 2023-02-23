package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Friend;

import java.util.Collection;
import java.util.Optional;

public interface FriendDao {

    Optional<Friend> addFriend(Integer friend_one, Integer friend_two);

    Friend findFriendById(Integer friend_one, Integer friend_two);

    Integer removeFriend(Integer friend_one, Integer friend_two);

    Collection<Integer> findFriendsIdsByUserId(Integer friendOne);
}