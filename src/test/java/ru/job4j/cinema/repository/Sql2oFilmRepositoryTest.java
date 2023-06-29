package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;

import java.util.Collection;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oFilmRepositoryTest {
    private static Sql2oFilmRepository sql2oFilmRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DataSourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    @Test
    public void whenFindByIdSuccess() {
        var filmDtoOptional = sql2oFilmRepository.findById(1);
        assertThat(filmDtoOptional).isPresent();
        var filmDto = filmDtoOptional.get();
        assertThat(filmDto.getName()).isEqualTo("Boogeman");
        assertThat(filmDto.getGenre()).isEqualTo("Action");
    }

    @Test
    public void whenFindByIdFails() {
        var filmDtoOptional = sql2oFilmRepository.findById(25);
        assertThat(filmDtoOptional).isEmpty();
    }

    @Test
    public void whenFindAllSuccess() {
        FilmDto filmA = new FilmDto(new Film(1, "Boogeman", "Boogeman description", 2023, 18, 120, 1), new Genre(1, "Action"));
        FilmDto filmB = new FilmDto(new Film(2, "Elemental", "Elemental description", 2023, 14, 120, 2), new Genre(2, "Comedy"));
        Collection<FilmDto> result = sql2oFilmRepository.findAll();
        assertThat(result).extracting(FilmDto::getName).contains(filmA.getName(), filmB.getName());
        assertThat(result).extracting(FilmDto::getGenre).contains(filmA.getGenre(), filmB.getGenre());
    }
}