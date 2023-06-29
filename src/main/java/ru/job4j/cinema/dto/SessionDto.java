package ru.job4j.cinema.dto;

import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Session;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class SessionDto {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "film_id", "filmId",
            "film_name", "filmName",
            "genre_name", "genreName",
            "halls_id", "hallId",
            "hall_name", "hallName",
            "start_time", "startTime",
            "end_time", "endTime",
            "prise", "price",
            "duration_in_minutes", "durationInMinutes"
    );
    private int id;
    private int filmId;
    private String filmName;
    private String genreName;
    private int hallId;
    private String hallName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;
    private int durationInMinutes;

    public SessionDto() {
    }

    public SessionDto(Session session, Film film, Hall hall, Genre genre) {
        this.id = session.getId();
        this.filmName = film.getName();
        this.filmId = film.getId();
        this.hallName = hall.getName();
        this.startTime = session.getStartTime();
        this.endTime = session.getEndTime();
        this.price = session.getPrice();
        this.hallId = hall.getId();
        this.genreName = genre.getName();
        this.durationInMinutes = film.getDurationInMinutes();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallsName) {
        this.hallName = hallsName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionDto that = (SessionDto) o;
        return id == that.id && filmId == that.filmId
                && price == that.price
                && Objects.equals(filmName, that.filmName)
                && Objects.equals(hallName, that.hallName)
                && Objects.equals(startTime, that.startTime)
                && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmName, filmId, hallName, startTime, endTime, price);
    }

    @Override
    public String toString() {
        return "SessionDto{"
                + "id=" + id
                + ", filmName='" + filmName + '\''
                + ", filmId=" + filmId
                + ", hallsName='" + hallName + '\''
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", price=" + price
                + '}';
    }
}
