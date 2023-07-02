package ru.job4j.cinema.dto;

import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SessionDto {

    public static final Map<String, String> COLUMN_MAPPING = new HashMap<>();
    static {
        COLUMN_MAPPING.put("id", "id");
        COLUMN_MAPPING.put("film_id", "filmId");
        COLUMN_MAPPING.put("film_name", "filmName");
        COLUMN_MAPPING.put("genre_name", "genreName");
        COLUMN_MAPPING.put("halls_id", "hallId");
        COLUMN_MAPPING.put("hall_name", "hallName");
        COLUMN_MAPPING.put("start_time", "startTime");
        COLUMN_MAPPING.put("end_time", "endTime");
        COLUMN_MAPPING.put("prise", "price");
        COLUMN_MAPPING.put("duration_in_minutes", "durationInMinutes");
        COLUMN_MAPPING.put("row_count", "rowCount");
        COLUMN_MAPPING.put("place_count", "placeCount");
    }
    private int id;
    private int filmId;
    private String filmName;
    private String genreName;
    private int hallId;
    private String hallName;
    private int rowCount;
    private int placeCount;
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
        this.rowCount = hall.getRowCount();
        this.placeCount = hall.getPlaceCount();
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

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
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
