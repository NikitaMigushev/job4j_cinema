package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {
    private final Sql2o sql2o;
    private final SessionRepository sessionRepository;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
        this.sessionRepository = new Sql2oSessionRepository(sql2o);
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
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO TICKETS (session_id, row_number, place_number, user_id, creation_date)
                    VALUES (:sessionId, :rowNumber, :placeNumber, :userId, :creationDateTime)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId())
                    .addParameter("creationDateTime", ticket.getCreationDateTime());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM TICKETS WHERE id = :id");
            query.addParameter("id", id);
            var ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM TICKETS WHERE ID = :id");
            query.addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Collection<Ticket> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM TICKETS");
            return query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetch(Ticket.class);
        }
    }

    public Sql2o getSql2o() {
        return sql2o;
    }
}
