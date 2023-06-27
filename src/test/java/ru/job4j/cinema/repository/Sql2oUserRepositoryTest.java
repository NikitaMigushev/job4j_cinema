package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Optional;
import java.util.Properties;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.*;

class Sql2oUserRepositoryTest {
    private static Sql2oUserRepository sql2oUserRepository;

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

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void cleanUsers() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        var user = sql2oUserRepository.save(new User(1, "test", "test3@test.ru", "123", creationDate));
        var savedUser = sql2oUserRepository.findById(user.get().getId());
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenFindByIdFails() {
        var creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        var user = sql2oUserRepository.save(new User(1, "test", "test3@test.ru", "123", creationDate));
        var savedUser = sql2oUserRepository.findById(10);
        assertThat(savedUser).isEqualTo(Optional.empty());
    }

    @Test
    public void whenSaveEmailThenGetError() {
        var creationDate = now().truncatedTo(ChronoUnit.MINUTES);
        var userA = sql2oUserRepository.save(new User(1, "test", "test3@test.ru", "123", creationDate));
        assertThat(sql2oUserRepository.save(new User(2, "test2", "test3@test.ru", "123", creationDate)))
                .isEqualTo(Optional.empty());
    }

    @Test
    public void whenFindByIdSuccess() {
        var creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        var user = sql2oUserRepository.save(new User(10, "test", "test3@test.ru", "123", creationDate));
        var savedUser = sql2oUserRepository.findById(user.get().getId());
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenFindAllSuccess() {
        // Create test users
        LocalDateTime creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        Optional<User> userA = sql2oUserRepository.save(new User(1, "John Doe", "john@example.com", "passwordA", creationDate));
        Optional<User> userB = sql2oUserRepository.save(new User(2, "Jane Smith", "jane@example.com", "passwordB", creationDate));
        Optional<User> userC = sql2oUserRepository.save(new User(3, "Mike Johnson", "mike@example.com", "passwordC", creationDate));
        Collection<User> result = sql2oUserRepository.findAll();
        assertThat(result)
                .hasSize(3)
                .extracting(User::getId)
                .containsExactlyInAnyOrder(userA.get().getId(), userB.get().getId(), userC.get().getId());
    }
}