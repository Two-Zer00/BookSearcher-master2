package com.example.twozer00.booksearch.booksearch.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;

public class AccountDetails {
    private JsonObject avatar;

    private JSONArray gravatar;

    private String hash;
    private String name;
    private String username;
    private String iso_3166_1;
    private String iso_639_1;

    public JsonObject getAvatar() {
        return avatar;
    }

    public void setAvatar(JsonObject avatar) {
        this.avatar = avatar;
    }

    public JSONArray getGravatar() {
        return gravatar;
    }

    public void setGravatar(JSONArray gravatar) {
        this.gravatar = gravatar;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getHash() {

        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        return "name: "+ this.name+"\n"+"username" + this.name + "hash: " + this.hash +"\n";
    }



}
