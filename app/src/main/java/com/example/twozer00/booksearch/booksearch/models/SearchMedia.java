package com.example.twozer00.booksearch.booksearch.models;

import java.util.ArrayList;

public class SearchMedia {
    ArrayList <SearchForm> results;
    private int total_results;

    public ArrayList<SearchForm> getResults() {
        return results;
    }

    public void setResults(ArrayList<SearchForm> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
