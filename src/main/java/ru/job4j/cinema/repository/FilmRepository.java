package ru.job4j.cinema.repository;

import ru.job4j.cinema.dto.FilmDto;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {
    Optional<FilmDto> findById(int id);
    Collection<FilmDto> findAll();
}
