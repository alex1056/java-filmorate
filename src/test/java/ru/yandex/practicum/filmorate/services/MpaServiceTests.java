package ru.yandex.practicum.filmorate.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MpaServiceTests {
    private final MpaService mpaService;

    @Test
    public void testGetAllMpa() {
        List<Mpa> mpas = mpaService.findAll();
        assertThat(mpas).isNotNull();
        assertThat(mpas).asList().hasSize(5);
        assertThat(mpas.get(0)).hasFieldOrPropertyWithValue("id", 1);
        assertThat(mpas.get(0)).hasFieldOrPropertyWithValue("name", "G");
    }

    @Test
    public void testGetMpaById() {
        Mpa mpa = mpaService.findMpaById(1);
        assertThat(mpa).isNotNull();
        assertThat(mpa).hasFieldOrPropertyWithValue("id", 1);
        assertThat(mpa).hasFieldOrPropertyWithValue("name", "G");
    }
}
