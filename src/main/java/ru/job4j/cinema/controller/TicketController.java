package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.SessionDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;
    private final SessionService sessionService;
    private final FilmService filmService;
    private final HallService hallService;

    public TicketController(TicketService ticketService, SessionService sessionService, FilmService filmService, HallService hallService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
        this.filmService = filmService;
        this.hallService = hallService;
    }

    @GetMapping("/buyTicket/{filmSessionId}")
    public String getBuyTicketPage(@PathVariable("filmSessionId") int sessionId, Model model, HttpSession session) {
        SessionDto sessionDto = sessionService.findById(sessionId).get();
        Optional<FilmDto> filmDto = filmService.findById(sessionDto.getFilmId());
        Hall hall = hallService.findById(sessionDto.getHallId()).get();
        model.addAttribute("filmSession", sessionDto);
        model.addAttribute("film", filmDto.get());
        model.addAttribute("hall", hall);
        session.setAttribute("filmSession", sessionDto);
        session.setAttribute("film", filmDto.get());
        session.setAttribute("hall", hall);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "ticket/buyTicket";
    }

    @PostMapping("/buyTicket")
    public String buyTicket(Model model, @ModelAttribute Ticket ticket, HttpSession session) {
        var savedTicket = ticketService.save(ticket);
        if (savedTicket.isEmpty()) {
            return "ticket/buyTicketFail";
        }
        session.setAttribute("ticket", savedTicket.get());
        return "redirect:/ticket/viewTicket";
    }

    @GetMapping("/buyTicketFail")
    public String getBuyTicketFailPage(Model model) {
        return "ticket/buyTicketFail";
    }

    @GetMapping("/viewTicket")
    public String getViewTicketPage(Model model, HttpSession session) {
        Ticket ticket = (Ticket) session.getAttribute("ticket");
        FilmDto film = (FilmDto) session.getAttribute("film");
        Hall hall = (Hall) session.getAttribute("hall");
        SessionDto filmSession = (SessionDto) session.getAttribute("filmSession");
        model.addAttribute("ticket", ticket);
        model.addAttribute("film", film);
        model.addAttribute("hall", hall);
        model.addAttribute("filmSession", filmSession);
        return "ticket/viewTicket";
    }
}
