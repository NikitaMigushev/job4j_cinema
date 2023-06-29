package ru.job4j.cinema.dto;

import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;

import java.util.Map;
import java.util.Objects;

public class FilmDto {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "name", "name",
            "description", "description",
            "release_year", "releaseYear",
            "minimal_age", "minimalAge",
            "duration_in_minutes", "durationInMinutes",
            "file_id", "fileId",
            "genre", "genre"
    );
    private int id;
    private String name;
    private String description;
    private int releaseYear;
    private int minimalAge;
    private int durationInMinutes;
    private String genre;
    private int fileId;

    public FilmDto() {
    }

    public FilmDto(Film film, Genre genre) {
        this.id = film.getId();
        this.name = film.getName();
        this.releaseYear = film.getYear();
        this.minimalAge = film.getMinimalAge();
        this.durationInMinutes = film.getDurationInMinutes();
        this.genre = genre.getName();
        this.fileId = film.getFileId();
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmDto filmDto = (FilmDto) o;
        return id == filmDto.id && releaseYear == filmDto.releaseYear
                && minimalAge == filmDto.minimalAge
                && durationInMinutes == filmDto.durationInMinutes
                && Objects.equals(name, filmDto.name)
                && Objects.equals(description, filmDto.description)
                && Objects.equals(genre, filmDto.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, releaseYear, minimalAge, durationInMinutes, genre);
    }
}
