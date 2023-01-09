package ru.yandex.practicum.filmorate.helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBuilder {
    private String message;
    
    public ResponseBuilder(String message) {
        this.message = message;
    }
}
