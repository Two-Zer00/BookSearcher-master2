package com.example.twozer00.booksearch.booksearch.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.twozer00.booksearch.booksearch.models.AccountDetails;
import com.example.twozer00.booksearch.booksearch.models.DeleteSession;
import com.example.twozer00.booksearch.booksearch.models.Element;
import com.example.twozer00.booksearch.booksearch.models.MovieAccessToken;
import com.example.twozer00.booksearch.booksearch.models.MovieAccessTokenLogin;
import com.example.twozer00.booksearch.booksearch.models.MovieSessionId;
import com.example.twozer00.booksearch.booksearch.models.Rating;
import com.example.twozer00.booksearch.booksearch.models.SearchMedia;
import com.example.twozer00.booksearch.booksearch.models.movieModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface movieApi {

    @GET("authentication/token/new")
    Call<MovieAccessToken> getAccesToken(@Query("api_key") String api_key);


    @GET("movie/popular")
    Call<Element> getPopular(@Query("api_key") String api_key,@Query("page") int page);


    @GET("search/multi")
    Call<SearchMedia> getSearch(@Query("api_key") String api_key, @Query("query") String query, @Query("page") int page);

    @FormUrlEncoded
    @POST("authentication/token/validate_with_login?")
    Call<MovieAccessTokenLogin> getAccesTokenLogin(@Query("api_key")String api_key,@Field("username") String username, @Field("password") String password,@Field("request_token") String request_token);

    @FormUrlEncoded
    @POST("authentication/session/new?")
    Call<MovieSessionId> getSessionId(@Query("api_key")String api_key, @Field("request_token") String request_token);

    @Headers("Accept: application/json;charset=utf-8")
    @FormUrlEncoded
    @POST("movie/{movie_id}/rating?")
    Call<Rating> setRating(@Path("movie_id") String movie_id,@Query("api_key")String api_key, @Query("session_id") String session_id,@Field("value") String value);

    @FormUrlEncoded
    @POST("authentication/session/new?")
    Call<DeleteSession> getSuccess(@Query("api_key")String api_key, @Field("session_id") String session_id);


    @GET("https://api.themoviedb.org/3/account?")
    Call<AccountDetails> getAccountDetails(@Query("api_key") String api_key,@Query("session_id") String session_id);

    /*@GET("/discover/movie?sort_by=popularity.desc")
    void getPopular(@Query("api_key") String api_key, Callback<List<movieModel>> response);*/

    @GET("/discover/movie?sort_by=vote_average.desc")
    void getHighestRated(@Query("api_key") String api_key, Callback<List<movieModel>> response);

}
