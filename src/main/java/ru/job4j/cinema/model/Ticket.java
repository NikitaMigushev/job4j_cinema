package ru.job4j.cinema.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

public class Ticket {
    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "session_id", "sessionId",
            "row_number", "rowNumber",
            "place_number", "placeNumber",
            "user_id", "userId",
            "creation_date", "creationDateTime"
    );
    private int id;
    private int sessionId;
    private int rowNumber;
    private int placeNumber;
    private int userId;
    private LocalDateTime creationDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

    public Ticket() {
    }

    public Ticket(int id, int sessionId, int rowNumber, int placeNumber, int userId, LocalDateTime creationDateTime) {
        this.id = id;
        this.sessionId = sessionId;
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
        this.userId = userId;
        this.creationDateTime = creationDateTime;
    }

    public Ticket(int sessionId, int rowNumber, int placeNumber, int userId) {
        this.sessionId = sessionId;
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return sessionId == ticket.sessionId && rowNumber == ticket.rowNumber
                && placeNumber == ticket.placeNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, rowNumber, placeNumber);
    }
}
