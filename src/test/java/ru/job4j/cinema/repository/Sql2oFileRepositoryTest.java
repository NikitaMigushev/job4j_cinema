package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.File;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oFileRepositoryTest {
    private static Sql2oFileRepository sql2oFileRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFileRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");
        var configuration = new DataSourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);

        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT MAX(id) FROM files");
            Integer lastId = query.executeScalar(Integer.class);
            int startValue = lastId != null ? lastId + 1 : 1;

            query = connection.createQuery("ALTER TABLE files ALTER COLUMN id RESTART WITH :startValue")
                    .addParameter("startValue", startValue);
            query.executeUpdate();
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var file = sql2oFileRepository.save(new File("Test", "files\\test.img"));
        var savedFile = sql2oFileRepository.findById(file.getId()).get();
        assertThat(savedFile).isEqualTo(file);
        sql2oFileRepository.deleteById(file.getId());
    }
}