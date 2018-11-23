package com.example.twozer00.booksearch.booksearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.twozer00.booksearch.booksearch.adapters.GridAdapter;
import com.example.twozer00.booksearch.booksearch.api.movieApi;
import com.example.twozer00.booksearch.booksearch.models.Book;
import com.example.twozer00.booksearch.booksearch.models.Element;
import com.example.twozer00.booksearch.booksearch.models.MovieAccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GridMovies extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private GridAdapter Listelem;



    private String API_BASE_URL = "https://api.themoviedb.org/3/";

    public static String API_KEY = "a36aa66b935c743a91a78e97f0e4bc9c";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_movies);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        Listelem = new GridAdapter(this);

        recyclerView.setAdapter(Listelem);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApi movieApi = retrofit.create(movieApi.class);

        Call<Element> call = movieApi.getPopular(API_KEY);
        call.enqueue(new Callback<Element>() {
            @Override
            public void onResponse(Call<Element> getPopular, Response<Element> response) {
                Log.d("RETROFIT", "I WORKED");
                Log.d("RETROFIT", "I WORKED" + response.body().getResults());

                if(response.isSuccessful()){
                    Element elemResponse =response.body();
                    ArrayList<Book> List = elemResponse.getResults();

                    Listelem.addElemList(List);
                }
                else{

                }

            }

            @Override
            public void onFailure(Call<Element> getPopular, Throwable t) {
                Log.d("RETROFIT", "I FAILED");
                // handle failure
            }
        });






    }


}
