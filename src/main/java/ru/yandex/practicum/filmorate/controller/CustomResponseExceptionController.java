package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.helper.ResponseBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
//@ControllerAdvice
public class CustomResponseExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handle(MethodArgumentNotValidException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + ": " + x.getDefaultMessage())
                .collect(Collectors.toList());
        log.info("Ошибка валидации: {}", errors);
        body.put("errors", errors);
        return new ResponseEntity<>(body, null, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity<ResponseBuilder> handleValidationException(ValidationException exception) {
//        String errorMessage = exception.getMessage();
//        log.info("Ошибка валидации: {}", errorMessage);
//        ResponseBuilder response = new ResponseBuilder(errorMessage);
//        return new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(FilmNotFoundException.class)
//    public ResponseEntity<ResponseBuilder> handleFilmNotFoundException(FilmNotFoundException exception) {
//        String errorMessage = exception.getMessage();
//        ResponseBuilder response = new ResponseBuilder(errorMessage);
//        return new ResponseEntity<>(response, null, HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ResponseBuilder> handleUserNotFoundException(UserNotFoundException exception) {
//        String errorMessage = exception.getMessage();
//        ResponseBuilder response = new ResponseBuilder(errorMessage);
//        return new ResponseEntity<>(response, null, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseBuilder> handleThrowableException(Throwable exception) {
        ResponseBuilder response = new ResponseBuilder(exception.getMessage());
        return new ResponseEntity<>(response, null, HttpStatus.NOT_FOUND);
    }
}
