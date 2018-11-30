package com.example.twozer00.booksearch.booksearch.models;

public class ElementForm {
    private int id;
    private String poster_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


    public String getCoverUrl() {
        return "https://image.tmdb.org/t/p/w185/" + poster_path;
    }
}
