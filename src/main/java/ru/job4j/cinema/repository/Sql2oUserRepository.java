package ru.job4j.cinema.repository;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.User;

import java.util.Collection;
import java.util.Optional;

public class Sql2oUserRepository implements UserRepository {
    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (Connection conn = sql2o.open()) {
            int generatedId = (int) conn.createQuery(
                    "INSERT INTO users "
                    + "(full_name, email, password, creation_date)"
                    + "VALUES (:fullName, :email, :password, :creationDateTime)"
            )
                    .addParameter("fullName", user.getFullName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword())
                    .addParameter("creationDateTime", user.getCreationDateTime())
                    .executeUpdate()
                    .getKey();
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM USERS WHERE id = :id");
            query.addParameter("id", id);
            var user = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public Collection<User> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM USERS");
            return query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetch(User.class);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM USERS WHERE ID = :id");
            query.addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
       try (Connection conn = sql2o.open()) {
           User user = conn.createQuery("SELECT * FROM users WHERE email = :email AND password = :password")
                   .addParameter("email", email)
                   .addParameter("password", password)
                   .setColumnMappings(User.COLUMN_MAPPING)
                   .executeAndFetchFirst(User.class);
           return Optional.ofNullable(user);
       } catch (Exception e) {
           e.printStackTrace();
           return Optional.empty();
       }
     }
}
