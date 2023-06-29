package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.dto.SessionDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Session;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oSessionRepositoryTest {
    private static Sql2oSessionRepository sql2oSessionRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oSessionRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DataSourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);
        sql2oSessionRepository = new Sql2oSessionRepository(sql2o);
    }

    @Test
    public void whenFindByIdSuccess() {
        var sessionDtoOptional = sql2oSessionRepository.findById(1);
        assertThat(sessionDtoOptional).isPresent();
        var sessionDto = sessionDtoOptional.get();
        assertThat(sessionDto.getFilmName()).isEqualTo("Boogeman");
        assertThat(sessionDto.getStartTime()).isEqualTo(LocalDateTime.of(2023, 6, 26, 9, 0, 0));
        assertThat(sessionDto.getEndTime()).isEqualTo(LocalDateTime.of(2023, 6, 26, 11, 0, 0));
        assertThat(sessionDto.getDurationInMinutes()).isEqualTo(120);
    }

    @Test
    public void whenFindByIdFails() {
        var sessionDtoOptional = sql2oSessionRepository.findById(25);
        assertThat(sessionDtoOptional).isEmpty();
    }

    @Test
    public void whenFindAllSuccess() {
        SessionDto sessionA = new SessionDto(
                new Session(1,
                        1,
                        1,
                        LocalDateTime.of(2023, 6, 26, 9, 0, 0),
                        LocalDateTime.of(2023, 6, 26, 11, 0, 0),
                        100),
                new Film(1, "Boogeman", "Boogeman description", 2023, 18, 120, 1),
                new Hall(1, "Green", 30, 30, "test"),
                new Genre(1, "Action"));
        SessionDto sessionB = new SessionDto(
                new Session(2,
                        2,
                        1,
                        LocalDateTime.of(2023, 6, 26, 12, 0, 0),
                        LocalDateTime.of(2023, 6, 26, 14, 0, 0),
                        100),
                new Film(2, "Elemental", "Elemental description", 2023, 18, 120, 2),
                new Hall(1, "Green", 30, 30, "test"),
                new Genre(2, "Comedy"));
        Collection<SessionDto> result = sql2oSessionRepository.findAll();
        assertThat(result).extracting(SessionDto::getFilmName).contains("Boogeman", "Elemental");
        assertThat(result).extracting(SessionDto::getHallName).contains("Green");
        assertThat(result).extracting(SessionDto::getStartTime).contains(
                LocalDateTime.of(2023, 6, 26, 9, 0, 0),
                LocalDateTime.of(2023, 6, 26, 12, 0, 0));

        assertThat(result).extracting(SessionDto::getEndTime).contains(
                LocalDateTime.of(2023, 6, 26, 11, 0, 0),
                LocalDateTime.of(2023, 6, 26, 14, 0, 0));
        assertThat(result).extracting(SessionDto::getDurationInMinutes).contains(120);
    }
}