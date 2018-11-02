package com.example.twozer00.booksearch.booksearch.api;

import com.example.twozer00.booksearch.booksearch.models.MovieAccessToken;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface movieApi {
    @GET("authentication/token/new")
    Call<MovieAccessToken> getAccesToken(@Query("api_key") String api_key);
}
