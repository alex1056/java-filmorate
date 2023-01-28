package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserServiceTests {
    private final UserService userService;
    private final User userTest1 = User.builder()
            .id(0)
            .email("user1@asd.ru")
            .login("User1_login")
            .name("")
            .friends(null)
            .birthday(LocalDate.parse("1990-01-12"))
            .build();

    @Test
    public void testCreateUser() {
        Optional<User> userOptional = userService.createUser(userTest1);
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
    }

    @Test
    public void testGetAllUsers() {
        userService.createUser(userTest1);
        List<User> users = userService.findAll();
        assertThat(users).asList().hasSize(1);
    }

    @Test
    public void testUpdateUser() {
        User userTestUpdate = User.builder()
                .id(1)
                .email("user1@asd.ru")
                .login("User1_login_updates")
                .name("")
                .friends(null)
                .birthday(LocalDate.parse("1990-01-12"))
                .build();
        userService.createUser(userTest1);
        Optional<User> userOptional = userService.updateUser(userTestUpdate);
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("login", "User1_login_updates")
                );
    }

    @Test
    public void testAddFriendUser() {
        User userTest2 = User.builder()
                .id(2)
                .email("user2@asd.ru")
                .login("User2_login")
                .name("")
                .friends(null)
                .birthday(LocalDate.parse("1991-01-01"))
                .build();
        userService.createUser(userTest1);
        userService.createUser(userTest2);
        userService.addFriend(1, 2);
        List<User> users = userService.getFriendsList(1);
        Optional<User> friend = Optional.ofNullable(users.get(0));
        assertThat(friend)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 2)
                );
        userService.removeFriend(1, 2);
        List<User> usersUpdated = userService.getFriendsList(1);
        assertThat(usersUpdated.size()).isEqualTo(0);
    }

    @Test
    public void testFriendsIntersection() {
        User userTest2 = User.builder()
                .id(2)
                .email("user2@asd.ru")
                .login("User2_login")
                .name("")
                .friends(null)
                .birthday(LocalDate.parse("1991-02-02"))
                .build();

        User userTest3 = User.builder()
                .id(3)
                .email("user3@asd.ru")
                .login("User3_login")
                .name("")
                .friends(null)
                .birthday(LocalDate.parse("1992-03-03"))
                .build();

        userService.createUser(userTest1);
        userService.createUser(userTest2);
        userService.createUser(userTest3);
        userService.addFriend(1, 2);
        userService.addFriend(1, 3);
        userService.addFriend(2, 3);

        List<User> users = userService.getFriendsIntersection(1, 2);
        Optional<User> friend = Optional.ofNullable(users.get(0));
        assertThat(users.size()).isEqualTo(1);
        assertThat(friend)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 3)
                );
    }
}
