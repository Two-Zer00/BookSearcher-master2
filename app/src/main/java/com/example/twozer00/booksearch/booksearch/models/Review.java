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

    public static Review fromJson (JSONObject jsonObject) {
        Review review = new Review();
        try{
            review.author = jsonObject.getString("author");
            review.content = jsonObject.getString("content");
            review.id =jsonObject.getString("id");

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        return review;

    }
    public static ArrayList<Review> fromJson(JSONArray jsonArray){
        ArrayList<Review> reviews = new ArrayList<>(jsonArray.length());
        for (int i = 0 ;i< jsonArray.length();i++){
            JSONObject reviewJson = null;
            try {
                reviewJson = jsonArray.getJSONObject(i);
            }catch (Exception e ){
                e.printStackTrace();
                continue;
            }
            Review review = Review.fromJson(reviewJson);
            if(review != null){
                reviews.add(review);
            }
        }
        return reviews;
    }




}
