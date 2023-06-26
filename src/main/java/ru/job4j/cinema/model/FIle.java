package ru.job4j.cinema.model;

import java.util.Objects;

public class FIle {
    private int id;
    private String name;
    private String path;

    public FIle() {
    }

    public FIle(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FIle fIle = (FIle) o;
        return id == fIle.id && Objects.equals(path, fIle.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path);
    }

    @Override
    public String toString() {
        return "FIle{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", path='" + path + '\''
                + '}';
    }
}
