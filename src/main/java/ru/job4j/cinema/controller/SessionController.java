package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.SessionService;

@Controller
@RequestMapping("/filmSession")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/filmSessionList")
    public String getAll(Model model) {
        model.addAttribute("filmSessionList", sessionService.findAll());
        return "filmSession/filmSessionList";
    }
}
