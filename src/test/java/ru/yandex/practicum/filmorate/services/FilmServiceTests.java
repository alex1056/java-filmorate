package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FilmServiceTests {
    private final FilmService filmService;
    private final UserService userService;

    private final List<Genre> immutableListGenres = List.of(new Genre(1));
    private final Film filmTest1 = Film.builder()
            .id(0)
            .name("Хороший фильм 1")
            .description("О хор. людях")
            .releaseDate(LocalDate.parse("1990-01-12"))
            .duration(100)
            .mpa(new Mpa(1))
            .genres(immutableListGenres)
            .likes(null)
            .build();
    private final User userTest1 = User.builder()
            .id(0)
            .email("user1@asd.ru")
            .login("User1_login")
            .name("")
            .friends(null)
            .birthday(LocalDate.parse("1990-01-12"))
            .build();

    @Test
    public void testCreateFilm() {
        Film film = filmService.create(filmTest1);
        assertThat(film).isNotNull();
        assertThat(film).hasFieldOrPropertyWithValue("id", 1);

        List<Film> filmList = filmService.findAll();
        assertThat(filmList).asList().hasSize(1);
    }

    @Test
    public void testGetFilmById() {
        filmService.create(filmTest1);
        Film film = filmService.getFilmById(1);
        assertThat(film).isNotNull();
        assertThat(film).hasFieldOrPropertyWithValue("id", 1);
        assertThat(film).hasFieldOrPropertyWithValue("name", "Хороший фильм 1");
    }

    @Test
    public void testAddRemoveLike() {
        userService.createUser(userTest1);
        filmService.create(filmTest1);
        Optional<Film> filmOtional = filmService.addLike(1, 1);
        assertThat(filmOtional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film.getLikes().contains(1))
                );
        Film film2 = filmService.removeLike(1, 1);
        assertThat(film2.getLikes()).asList().hasSize(0);
    }

    @Test
    public void testGetPopular() {
        userService.createUser(userTest1);
        filmService.create(filmTest1);
        filmService.create(filmTest1);
        filmService.addLike(1, 1);
        filmService.addLike(2, 1);
        List<Film> films = filmService.getPopular(10);
        assertThat(films).asList().hasSize(2);
        Film film = films.get(0);
        assertThat(film).hasFieldOrPropertyWithValue("id", 1);
        Film film2 = films.get(1);
        assertThat(film2).hasFieldOrPropertyWithValue("id", 2);
    }
}
