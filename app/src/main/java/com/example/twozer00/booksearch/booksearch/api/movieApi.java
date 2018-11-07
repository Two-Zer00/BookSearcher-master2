package com.example.twozer00.booksearch.booksearch.api;

import com.example.twozer00.booksearch.booksearch.models.AccountDetails;
import com.example.twozer00.booksearch.booksearch.models.MovieAccessToken;
import com.example.twozer00.booksearch.booksearch.models.MovieAccessTokenLogin;
import com.example.twozer00.booksearch.booksearch.models.MovieSessionId;
import com.example.twozer00.booksearch.booksearch.models.movieModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface movieApi {
    @GET("authentication/token/new")
    Call<MovieAccessToken> getAccesToken(@Query("api_key") String api_key);

    @FormUrlEncoded
    @POST("authentication/token/validate_with_login?")
    Call<MovieAccessTokenLogin> getAccesTokenLogin(@Query("api_key")String api_key,@Field("username") String username, @Field("password") String password,@Field("request_token") String request_token);

    @FormUrlEncoded
    @POST("authentication/session/new?")
    Call<MovieSessionId> getSessionId(@Query("api_key")String api_key, @Field("request_token") String request_token);

    @GET("https://api.themoviedb.org/3/account?")
    Call<AccountDetails> getAccountDetails(@Query("api_key") String api_key,@Query("session_id") String session_id);

    @GET("/discover/movie?sort_by=popularity.desc")
    void getPopular(@Query("api_key") String api_key, Callback<List<movieModel>> response);

    @GET("/discover/movie?sort_by=vote_average.desc")
    void getHighestRated(@Query("api_key") String api_key, Callback<List<movieModel>> response);

}
