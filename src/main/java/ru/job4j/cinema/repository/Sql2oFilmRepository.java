package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.dto.FilmDto;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFilmRepository implements FilmRepository {
    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select "
                    + "films.id,"
                    + "films.name,"
                    + "films.description,"
                    + "films.release_year,"
                    + "films.minimal_age,"
                    + "films.duration_in_minutes,"
                    + "films.file_id,"
                    + "genres.name genre "
                    + "from films left join genres on films.genre_id = genres.id "
                    + "where films.id = :id");
            query.addParameter("id", id);
            var filmDto = query.setColumnMappings(FilmDto.COLUMN_MAPPING).executeAndFetchFirst(FilmDto.class);
            return Optional.ofNullable(filmDto);
        }
    }

    @Override
    public Collection<FilmDto> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select "
                    + "films.id, "
                    + "films.name, "
                    + "films.description, "
                    + "films.release_year, "
                    + "films.minimal_age, "
                    + "films.duration_in_minutes, "
                    + "films.file_id, "
                    + "genres.name genre "
                    + "from films left join genres on films.genre_id = genres.id");
            return query.setColumnMappings(FilmDto.COLUMN_MAPPING).executeAndFetch(FilmDto.class);
        }
    }
}
