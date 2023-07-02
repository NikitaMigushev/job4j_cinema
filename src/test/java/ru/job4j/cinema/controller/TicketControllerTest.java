package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.SessionDto;
import ru.job4j.cinema.model.*;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {
    private TicketService ticketService;
    private SessionService sessionService;
    private FilmService filmService;
    private HallService hallService;
    private TicketController ticketController;
    private HttpSession session;
    private int filmSessionId;
    private Optional<SessionDto> sessionDto;
    private Optional<FilmDto> filmDto;
    private Optional<Hall> hall;

    @BeforeEach
    public void initServices() {
        ticketService = mock(TicketService.class);
        sessionService = mock(SessionService.class);
        filmService = mock(FilmService.class);
        hallService = mock(HallService.class);
        ticketController = new TicketController(ticketService, sessionService, filmService, hallService);
        filmSessionId = 1;
        sessionDto = Optional.of(new SessionDto(
                new Session(1,
                        1,
                        1,
                        LocalDateTime.of(2023, 6, 26, 9, 0, 0),
                        LocalDateTime.of(2023, 6, 26, 11, 0, 0),
                        100),
                new Film(1, "Boogeman", "Boogeman description", 2023, 18, 120, 1),
                new Hall(1, "Green", 30, 30, "test"),
                new Genre(1, "Action")));
        filmDto = Optional.of(new FilmDto(new Film(1, "Boogeman", "Boogeman description", 2023, 18, 120, 1), new Genre(1, "Action")));
        hall = Optional.of(new Hall(1, "Green", 10, 20, "test"));

        session = mock(HttpSession.class);
        session.setAttribute("user", new User(1, "Test", "test@test.ru", "123"));
    }

    @Test
    public void whenRequestBuyTicketPageTheGetIt() {


        when(sessionService.findById(filmSessionId)).thenReturn(sessionDto);
        when(filmService.findById(sessionDto.get().getFilmId())).thenReturn(filmDto);
        when(hallService.findById(sessionDto.get().getHallId())).thenReturn(hall);
        var model = new ConcurrentModel();
        var view = ticketController.getBuyTicketPage(filmSessionId, model, session);
        var expectedFilmSession = model.getAttribute("filmSession");
        var expectedFilm = model.getAttribute("film");
        var expectedHall = model.getAttribute("hall");
        assertThat(view).isEqualTo("ticket/buyTicket");
        assertThat(expectedFilmSession).isEqualTo(sessionDto.get());
        assertThat(expectedFilm).isEqualTo(filmDto.get());
        assertThat(expectedHall).isEqualTo(hall.get());
    }

    @Test
    public void whenBuyTicketSuccessThenRedirectToViwTicket() {
        Ticket ticket = new Ticket(1, 1, 1, 1);
        when(ticketService.save(ticket)).thenReturn(Optional.of(ticket));
        var model = new ConcurrentModel();
        model.addAttribute("ticket", ticket);
        var view = ticketController.buyTicket(model, ticket, session);
        assertThat(view).isEqualTo("redirect:/ticket/viewTicket");
    }

    @Test
    public void whenBuyTicketSuccessThenShowBuyTicketFailPage() {
        Ticket ticket = new Ticket(1, 99, 99, 1);
        when(ticketService.save(ticket)).thenReturn(Optional.empty());
        var model = new ConcurrentModel();
        model.addAttribute("ticket", ticket);
        var view = ticketController.buyTicket(model, ticket, session);
        assertThat(view).isEqualTo("ticket/buyTicketFail");
    }
}