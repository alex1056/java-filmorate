package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendDao;
import ru.yandex.practicum.filmorate.exception.FriendException;
import ru.yandex.practicum.filmorate.model.Friend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Component
public class FriendDaoImpl implements FriendDao {

    private final JdbcTemplate jdbcTemplate;

    public FriendDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Integer> findFriendsIdsByUserId(Integer friendOne) {
        String sql = "SELECT FRIEND_TWO FROM friends \n" +
                "WHERE FRIEND_ONE = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("friend_two"), friendOne);
    }

    private Friend makeFriend(ResultSet rs) throws SQLException {
        Integer friendOne = rs.getInt("friend_one");
        Integer friendTwo = rs.getInt("friend_two");
        return new Friend(friendOne, friendTwo);
    }

    @Override
    public Friend findFriendById(Integer friend_one, Integer friend_two) {
        String sql = "select * from friends where friend_one = ? AND friend_two=?";
        Optional<Friend> friend = jdbcTemplate.query(sql, (rs, rowNum) -> makeFriend(rs), friend_one, friend_two).stream().findFirst();
        return friend.orElse(null);
    }

    @Override
    public Integer removeFriend(Integer friendOne, Integer friendTwo) {
        String sql = "DELETE FROM FRIENDS \n" +
                "WHERE FRIEND_ONE = ? AND FRIEND_TWO = ? \n";
        int result = jdbcTemplate.update(sql, friendOne, friendTwo);
        if (result > 0) {
            return friendOne;
        } else
            throw new FriendException("не удалось удалить friendOneId=" + friendOne + " и friendTwoId=" + friendTwo);
    }

    @Override
    public Optional<Friend> addFriend(Integer friendOneId, Integer friendTwoId) {
        Friend friend = findFriendById(friendOneId, friendTwoId);
        if (friend != null) {
            throw new FriendException("пара уже существует friendOneId=" + friendOneId + " и friendTwoId=" + friendTwoId);
        }
        String sql = "INSERT INTO friends (friend_one, friend_two) \n" +
                "VALUES (?, ?)";
        int result = jdbcTemplate.update(sql, friendOneId, friendTwoId);
        if (result > 0) {
            return Optional.ofNullable(findFriendById(friendOneId, friendTwoId));
        } else {
            throw new FriendException("не удалось добавить friendOneId=" + friendOneId + " и friendTwoId=" + friendTwoId);
        }
    }
}