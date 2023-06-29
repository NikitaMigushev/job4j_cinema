package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;
import ru.job4j.cinema.dto.SessionDto;
import ru.job4j.cinema.service.SessionService;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SessionControllerTest {

    @Mock
    private SessionService sessionService;

    @Mock
    private Model model;

    @BeforeEach
    public void setup() {
        sessionService = mock(SessionService.class);
        model = mock(Model.class);
    }

    @Test
    public void testGetAll() {
        Collection<SessionDto> sessionDtos = Collections.singletonList(new SessionDto());
        when(sessionService.findAll()).thenReturn(sessionDtos);
        SessionController sessionController = new SessionController(sessionService);
        String viewName = sessionController.getAll(model);
        verify(sessionService, times(1)).findAll();
        verify(model, times(1)).addAttribute("filmSessionList", sessionDtos);
        assertEquals("filmSession/filmSessionList", viewName);
    }
}