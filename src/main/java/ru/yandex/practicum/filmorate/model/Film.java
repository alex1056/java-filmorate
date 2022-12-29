package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


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
    private String description;

    @NotNull
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
    
    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
