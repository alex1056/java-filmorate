package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.List;

import lombok.*;

import javax.validation.constraints.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Film {
    private Integer id;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    @Size(min = 1, max = 200)
    private String description;

    @NotNull
    private LocalDate releaseDate;
    @Min(1)
    private Integer duration;
    private Mpa mpa;
    private List<Genre> genres;
    private List<Integer> likes;

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration, Mpa mpa, List<Genre> genre, List<Integer> likes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres = genre;
        this.likes = likes;
    }
}
