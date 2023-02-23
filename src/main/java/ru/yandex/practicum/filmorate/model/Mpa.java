package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class Mpa {
    private Integer id;

    private String name;

    public Mpa(Integer mpaId, String name) {
        this.id = mpaId;
        this.name = name;
    }

    public Mpa(Integer mpaId) {
        this.id = mpaId;
        this.name = "";
    }
}
