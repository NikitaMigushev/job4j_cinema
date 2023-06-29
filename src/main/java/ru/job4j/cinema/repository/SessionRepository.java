package ru.job4j.cinema.repository;

import ru.job4j.cinema.dto.SessionDto;

import java.util.Collection;
import java.util.Optional;

public interface SessionRepository {
    Optional<SessionDto> findById(int id);
    Collection<SessionDto> findAll();
}
