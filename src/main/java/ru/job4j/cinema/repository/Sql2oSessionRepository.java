package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.dto.SessionDto;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oSessionRepository implements SessionRepository {
    private final Sql2o sql2o;

    public Sql2oSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<SessionDto> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select "
                    + "fs.id,"
                    + "fs.film_id, "
                    + "f.name as film_name, "
                    + "g.name as genre_name, "
                    + "f.duration_in_minutes, "
                    + "fs.halls_id, "
                    + "h.name as hall_name, "
                    + "fs.start_time, "
                    + "fs.end_time, "
                    + "fs.price "
                    + "from film_sessions as fs "
                    + "left join films as f on f.id = fs.film_id "
                    + "left join halls as h on h.id = halls_id "
                    + "left join genres as g on g.id = f.genre_id "
                    + "where fs.id = :id");
            query.addParameter("id", id);
            var sessionDto = query.setColumnMappings(SessionDto.COLUMN_MAPPING).executeAndFetchFirst(SessionDto.class);
            return Optional.ofNullable(sessionDto);
        }
    }

    @Override
    public Collection<SessionDto> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("select "
                    + "fs.id,"
                    + "fs.film_id, "
                    + "f.name as film_name, "
                    + "f.duration_in_minutes, "
                    + "g.name as genre_name, "
                    + "fs.halls_id, "
                    + "h.name as hall_name, "
                    + "fs.start_time, "
                    + "fs.end_time, "
                    + "fs.price "
                    + "from film_sessions as fs "
                    + "left join films as f on f.id = fs.film_id "
                    + "left join halls as h on h.id = halls_id "
                    + "left join genres as g on g.id = f.genre_id");
            return query.setColumnMappings(SessionDto.COLUMN_MAPPING).executeAndFetch(SessionDto.class);
        }
    }
}
