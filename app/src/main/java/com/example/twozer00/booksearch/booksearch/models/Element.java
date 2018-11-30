package com.example.twozer00.booksearch.booksearch.models;

import java.util.ArrayList;

public class Element {

    private ArrayList<ElementForm> results;
    private int total_results;

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public ArrayList<ElementForm> getResults() {
        return results;
    }

    public void setResults(ArrayList<ElementForm> results) {
        this.results = results;
    }
}
