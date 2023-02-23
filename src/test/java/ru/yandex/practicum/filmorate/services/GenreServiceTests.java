package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GenreServiceTests {
    private final GenreService genreService;

    @Test
    public void testGetAllGenres() {
        List<Genre> genres = genreService.findAll();
        assertThat(genres).isNotNull();
        assertThat(genres).asList().hasSize(6);
        assertThat(genres.get(0)).hasFieldOrPropertyWithValue("id", 1);
        assertThat(genres.get(0)).hasFieldOrPropertyWithValue("name", "Комедия");
    }

    @Test
    public void testGetGenreById() {
        Genre genre = genreService.findGenreById(1);
        assertThat(genre).isNotNull();
        assertThat(genre).hasFieldOrPropertyWithValue("id", 1);
        assertThat(genre).hasFieldOrPropertyWithValue("name", "Комедия");
    }
}
