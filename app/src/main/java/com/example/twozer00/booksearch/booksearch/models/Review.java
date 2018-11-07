package com.example.twozer00.booksearch.booksearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Review implements Serializable {
    private String author;
    private String content;
    private String id;
    private String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Review fromJson(JSONObject jsonObject) {
        Review review = new Review();
        try {
            review.author=jsonObject.getString("author");
            review.content = jsonObject.getString("content");
            review.id = jsonObject.getString("id");
            review.url= jsonObject.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return review;
    }
    // Decodes array of book json results into business model objects
    public static ArrayList<Review> fromJson(JSONArray jsonArray) {
        ArrayList<Review> review = new ArrayList<Review>(jsonArray.length());
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
            Review review1 = Review.fromJson(bookJson );
            if (review != null) {
                review.add(review1);
            }
        }
        return review;
    }

}
