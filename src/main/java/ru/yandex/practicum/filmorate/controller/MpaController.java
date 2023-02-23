package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class MpaController {
    private MpaService mpaService;


    @Autowired
    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    private static final Logger log = LoggerFactory.getLogger(MpaController.class);

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable Integer id) {
        log.info("Получен GET запрос /mpa/{id}, id= {}", id);
        return mpaService.findMpaById(id);
    }

    @GetMapping
    public List<Mpa> findAll() {
        log.info("Получен GET запрос /mpa");
        return mpaService.findAll();
    }
}
