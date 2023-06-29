package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.service.FilmService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FilmControllerTest {
    @Mock
    private FilmService filmService;

    @Mock
    private Model model;

    @InjectMocks
    private FilmController filmController;

    @BeforeEach
    public void setup() {
        filmService = mock(FilmService.class);
        filmController = new FilmController(filmService);
        model = mock(Model.class);
    }

    @Test
    public void testGetAll() {
        List<FilmDto> films = new ArrayList<>();
        Film film1 = new Film(1, "Film 1", "Description 1", 2022, 18, 120, 1);
        Genre genre1 = new Genre(1, "Action");
        films.add(new FilmDto(film1, genre1));
        Film film2 = new Film(2, "Film 2", "Description 2", 2021, 12, 90, 2);
        Genre genre2 = new Genre(2, "Comedy");
        films.add(new FilmDto(film2, genre2));
        when(filmService.findAll()).thenReturn(films);
        String viewName = filmController.getAll(model);
        verify(filmService, times(1)).findAll();
        verify(model, times(1)).addAttribute("filmList", films);
        assertEquals("films/filmList", viewName);
    }
}