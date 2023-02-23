package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.helper.Helper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
public class UserDaoImpl implements UserDao {
    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final FriendDaoImpl friendDao;

    public UserDaoImpl(JdbcTemplate jdbcTemplate, FriendDaoImpl friendDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.friendDao = friendDao;
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE user_id = ?", id);
        if (userRows.next()) {
            User user = new User(
                    userRows.getInt("user_id"),
                    Helper.trimNullableString(userRows.getString("email")),
                    Helper.trimNullableString(userRows.getString("login")),
                    Helper.trimNullableString(userRows.getString("name")),
                    userRows.getDate("birthday").toLocalDate(),
                    (ArrayList<Integer>) friendDao.findFriendsIdsByUserId(id)
            );
            log.info("Найден пользователь: {} {}", user.getId(), user.getEmail());
            return Optional.of(user);
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            throw new UserNotFoundException("id=" + id);
        }
    }

    @Override
    public Optional<User> addUser(User user) {
        Validator.userValidatorDB(user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setLoginAsName();
        }

        String sql = "INSERT INTO users (email,login,name,birthday) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
        sql = "SELECT user_id FROM users WHERE email=?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, user.getEmail());
        if (userRows.next()) {
            user.setId(userRows.getInt("user_id"));
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> updateUser(User user) {
        findUserById(user.getId());
        Validator.userValidatorDB(user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setLoginAsName();
        }
        String sql = "UPDATE users SET email=?,login=?,name=?,birthday=? WHERE user_id=?";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );

        sql = "SELECT user_id FROM users WHERE email=?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql, user.getEmail());
        if (userRows.next()) {
            user.setId(userRows.getInt("user_id"));
        }
        return Optional.of(user);
    }

    @Override
    public Collection<User> getFriendsIntersection(Integer userId, Integer otherId) {
        String sql = "SELECT * \n" +
                "FROM USERS u \n" +
                "WHERE U.USER_ID IN (\n" +
                "SELECT F1.FRIEND_TWO \n" +
                "FROM (\n" +
                "SELECT * FROM FRIENDS f \n" +
                "WHERE F.FRIEND_ONE = ?\n" +
                ") AS F1\n" +
                "JOIN (\n" +
                "SELECT * FROM FRIENDS f \n" +
                "WHERE F.FRIEND_ONE = ?\n" +
                ") AS F2\n" +
                "ON F1.FRIEND_TWO = F2.FRIEND_TWO\n" +
                ")";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), userId, otherId);
    }

    @Override
    public Collection<User> findAll() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    private User makeUser(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("user_id");
        String email = Helper.trimNullableString(rs.getString("email"));
        String login = Helper.trimNullableString(rs.getString("login"));
        String name = Helper.trimNullableString(rs.getString("name"));
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        ArrayList<Integer> friends = (ArrayList<Integer>) friendDao.findFriendsIdsByUserId(id);
        return new User(id, email, login, name, birthday, friends);
    }

    @Override
    public User removeFriend(Integer userId, Integer friendId) {
        checkTwoUsersExistance(userId, friendId);
        friendDao.removeFriend(userId, friendId);
        Optional<User> userOpt = findUserById(userId);
        return userOpt.orElse(null);
    }

    @Override
    public User addFriend(Integer friendOne, Integer friendTwo) {
        checkTwoUsersExistance(friendOne, friendTwo);

        if (friendDao.addFriend(friendOne, friendTwo).isPresent()) {
            Optional<User> userOpt = findUserById(friendOne);
            return userOpt.orElse(null);
        } else {
            return null;
        }
    }

    private void checkTwoUsersExistance(Integer userOne, Integer userTwo) {
        if (findUserById(userOne).isEmpty()) {
            throw new UserNotFoundException("Пользователь с id= " + userOne + " не найден");
        }
        if (findUserById(userTwo).isEmpty()) {
            throw new UserNotFoundException("Пользователь с id=" + userTwo + " не найден");
        }
    }

    private void checkOneUsersExistance(Integer user_one) {
        if (findUserById(user_one).isEmpty()) {
            throw new UserNotFoundException("Пользователь с id= " + user_one + " не найден");
        }
    }

    @Override
    public Collection<User> getFriendsList(Integer userId) {
        checkOneUsersExistance(userId);
        String sql = "SELECT * FROM users WHERE USER_ID IN \n" +
                "(SELECT friend_two FROM FRIENDS f \n" +
                "WHERE friend_one = ?)";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), userId);
    }
}