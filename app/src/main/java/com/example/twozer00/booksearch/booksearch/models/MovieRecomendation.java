package com.example.twozer00.booksearch.booksearch.models;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieRecomendation extends Book implements Serializable {
    //
    private static String id_Movie;
    private static String title;
    protected static String imagecode;

    public String getId_Movie() {
        return id_Movie;
    }
    public String getTitle() {
        return title;
    }
    public static MovieRecomendation fromJson(JSONObject jsonObject) {
        MovieRecomendation recomendation = new MovieRecomendation();
        try {
            // Deserialize json into object fields
            // Check if a cover edition is available
            MovieRecomendation.id_Movie=jsonObject.getString("id");
            if (jsonObject.has("poster_path"))  {
                MovieRecomendation.imagecode = jsonObject.getString("poster_path");
            } else if(jsonObject.has("poster_path")) {
                final JSONArray ids = jsonObject.getJSONArray("poster_path");
                MovieRecomendation.imagecode = ids.getString(0);
            }
            MovieRecomendation.title = jsonObject.getString("title");
            //MovieRecomendation.overview = jsonObject.getString("overview");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return recomendation;
    }
    public static ArrayList<Book> fromJson(JSONArray jsonArray) {
        ArrayList<Book> Books = new ArrayList<>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject bookJson = null;
            try {
                bookJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            MovieRecomendation recomendation = MovieRecomendation.fromJson(bookJson);
            if (recomendation != null) {
                Books.add(recomendation);
            }
        }
        return Books;
    }
}
