package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;


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
    private Set<Long> likes;

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = new HashSet<>();
    }

    public List<Long> getLikes() {
        return likes.stream().collect(Collectors.toList());
    }

    public boolean addLike(Long userId) {
        return likes.add(userId);
    }

    public boolean removeLike(Integer userId) {
        return likes.remove((long) userId);
    }
}
