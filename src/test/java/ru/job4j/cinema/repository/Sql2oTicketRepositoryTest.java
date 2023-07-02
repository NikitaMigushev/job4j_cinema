package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Ticket;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Properties;
import static org.assertj.core.api.Assertions.*;


class Sql2oTicketRepositoryTest {
    private static Sql2oTicketRepository sql2oTicketRepository;

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

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    @AfterEach
    public void cleanTickets() {
        var tickets = sql2oTicketRepository.findAll();
        for (var ticket : tickets) {
            sql2oTicketRepository.deleteById(ticket.getId());
        }
        try (var connection = sql2oTicketRepository.getSql2o().open()) {
            var resetQuery = "ALTER TABLE TICKETS ALTER COLUMN id RESTART WITH 1";
            connection.createQuery(resetQuery).executeUpdate();
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        var ticket = sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, 1, creationDate));
        var savedTicket = sql2oTicketRepository.findById(ticket.get().getId());
        assertThat(savedTicket).usingRecursiveComparison().isEqualTo(ticket);
    }

    @Test
    public void whenSaveSameTicketThenError() {
        var creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        var ticket = sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, 1, creationDate));
        assertThat(sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, 1, creationDate))).isEqualTo(Optional.empty());
    }
}