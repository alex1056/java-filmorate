package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Friend {
    private Integer friendOne;
    private Integer friendTwo;
    private Integer status;


    public Friend(Integer friendOne, Integer friendTwo) {
        this.friendOne = friendOne;
        this.friendTwo = friendTwo;
    }
}
