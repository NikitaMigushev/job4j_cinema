package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.SessionRepository;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {
    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;

    public SimpleTicketService(TicketRepository ticketRepository, SessionRepository sessionRepository) {
        this.ticketRepository = ticketRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        var filmSession = sessionRepository.findById(ticket.getSessionId()).get();
        if (ticket.getRowNumber() > filmSession.getRowCount()
                || ticket.getRowNumber() <= 0
                || ticket.getPlaceNumber() > filmSession.getPlaceCount()
                || ticket.getPlaceNumber() < 0) {
            return Optional.empty();
        }
        return ticketRepository.save(ticket);
    }

    @Override
    public boolean deleteById(int id) {
        return ticketRepository.deleteById(id);
    }

    @Override
    public Optional<Ticket> findById(int id) {
        return ticketRepository.findById(id);
    }

    @Override
    public Collection<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
