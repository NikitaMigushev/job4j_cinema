package ru.job4j.cinema.dto;

import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Session;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessionDto {
    private int id;
    private String filmName;
    private int filmId;
    private String hallsName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;

    public SessionDto() {
    }

    public SessionDto(Session session, Film film, Hall hall) {
        this.id = session.getId();
        this.filmName = film.getName();
        this.filmId = film.getId();
        this.hallsName = hall.getName();
        this.startTime = session.getStartTime();
        this.endTime = session.getEndTime();
        this.price = session.getPrice();
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

    public String getHallsName() {
        return hallsName;
    }

    public void setHallsName(String hallsName) {
        this.hallsName = hallsName;
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
                && Objects.equals(hallsName, that.hallsName)
                && Objects.equals(startTime, that.startTime)
                && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmName, filmId, hallsName, startTime, endTime, price);
    }

    @Override
    public String toString() {
        return "SessionDto{"
                + "id=" + id
                + ", filmName='" + filmName + '\''
                + ", filmId=" + filmId
                + ", hallsName='" + hallsName + '\''
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", price=" + price
                + '}';
    }
}
