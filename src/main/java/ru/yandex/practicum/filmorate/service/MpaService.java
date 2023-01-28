package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.impl.MpaDaoImpl;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Service
public class MpaService {
    private final MpaDaoImpl mpaDao;

    @Autowired
    public MpaService(MpaDaoImpl mpaDao) {
        this.mpaDao = mpaDao;
    }

    public Mpa findMpaById(Integer mpaId) {
        return mpaDao.findMpaById(mpaId);
    }

    public List<Mpa> findAll() {
        return (List<Mpa>) mpaDao.findAll();
    }
}
