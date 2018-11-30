package com.example.twozer00.booksearch.booksearch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.twozer00.booksearch.booksearch.adapters.GridAdapter;
import com.example.twozer00.booksearch.booksearch.api.movieApi;
import com.example.twozer00.booksearch.booksearch.models.Book;
import com.example.twozer00.booksearch.booksearch.models.DeleteSession;
import com.example.twozer00.booksearch.booksearch.models.Element;
import com.example.twozer00.booksearch.booksearch.models.ElementForm;
import com.example.twozer00.booksearch.booksearch.models.MovieAccessToken;
import com.example.twozer00.booksearch.booksearch.models.SearchForm;
import com.example.twozer00.booksearch.booksearch.models.SearchMedia;
import com.example.twozer00.booksearch.booksearch.net.BookClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.twozer00.booksearch.booksearch.LoginActivity.session_id;

public class GridMovies extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private GridAdapter Listelem;
    private boolean aptoParaCargar;
    int totalItemCount;
    private int page;



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

        final GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.d("SCROLL", " Llegamos al final."+ " " +visibleItemCount+ " " +pastVisibleItems+ " " +totalItemCount);

                            aptoParaCargar = false;
                            page += 1;
                            LoadData(page);
                        }
                    }
                }
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aptoParaCargar = true;
        page = 1;
        LoadData(page);

/*

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApi movieApi = retrofit.create(movieApi.class);

        Call<Element> call = movieApi.getPopular(API_KEY,page);
        call.enqueue(new Callback<Element>() {
            @Override
            public void onResponse(Call<Element> getPopular, Response<Element> response) {
                Log.d("RETROFITGrid", "I WORKED");
                Log.d("RETROFITGrid", "I WORKED1" + response.body().getResults());

                if(response.isSuccessful()){
                    Element elemResponse =response.body();
                    ArrayList<ElementForm> List = elemResponse.getResults();

                    Listelem.addElemList(List);
                    Toast.makeText(GridMovies.this, "Tamaño"+List.size(), Toast.LENGTH_SHORT).show();
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




*/
    }

    private void LoadData(int pages) {
        movieApi movieApi = retrofit.create(movieApi.class);

        Call<Element> call = movieApi.getPopular(API_KEY,pages);
        call.enqueue(new Callback<Element>() {
            @Override
            public void onResponse(Call<Element> getPopular, Response<Element> response) {
                Log.d("RETROFITGrid", "I WORKED");
                Log.d("RETROFITGrid", "I WORKED1" + response.body().getResults());

                if(response.isSuccessful()){
                    Element elemResponse =response.body();
                    ArrayList<ElementForm> List = elemResponse.getResults();
                    totalItemCount=response.body().getTotal_results();

                    Listelem.addElemList(List);
                    Toast.makeText(GridMovies.this, "Pagina "+ page, Toast.LENGTH_SHORT).show();
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
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
        final MenuItem searchItem=menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                search(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                GridMovies.this.setTitle(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }


    private void getSearch(String query, int id) {
        movieApi movieApi = retrofit.create(movieApi.class);
        Call<SearchMedia> call = movieApi.getSearch(API_KEY,query,page);
        //id = id - offset - 1;
        final int finalId = id;
        final String[] name = {""};
        call.enqueue(new Callback<SearchMedia>() {
            @Override
            public void onResponse(Call<SearchMedia> call, Response<SearchMedia> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()) {
                    SearchMedia pokemonRespuesta = response.body();
                    ArrayList<SearchForm> listaPokemon = pokemonRespuesta.getResults();
                    ArrayList<SearchForm> listP = new ArrayList<SearchForm>();
                    //listP.add(listaPokemon.get(finalId));

                    Log.d("listPokes", listaPokemon.toString().toUpperCase());

                    Listelem.adicionarListaPokemon(listP);
                    name[0] = listP.get(0).getName().toUpperCase();
                    PokemonListActivity.this.setTitle(name[0].substring(0,1) + listP.get(0).getName().substring(1));

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<SearchMedia> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }


    private void search(String offset) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        Listelem = new GridAdapter(this);
        recyclerView.setAdapter(Listelem);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        getSearch(offset, id);
    }

*/

}
