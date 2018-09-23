package com.example.twozer00.booksearch.booksearch;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.twozer00.booksearch.booksearch.adapters.BookAdapter;
import com.example.twozer00.booksearch.booksearch.adapters.MovieCompaniesAdapter;
import com.example.twozer00.booksearch.booksearch.adapters.MovieRecomendationAdapter;
import com.example.twozer00.booksearch.booksearch.adapters.PopularMoviesAdapter;
import com.example.twozer00.booksearch.booksearch.models.Book;
import com.example.twozer00.booksearch.booksearch.models.MovieRecomendation;
import com.example.twozer00.booksearch.booksearch.models.PopularMovies;
import com.example.twozer00.booksearch.booksearch.net.BookClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class BookListActivity extends AppCompatActivity {
    public static final String BOOK_DETAIL_KEY = "movie";
    public static final String BOOK_DETAIL_RECOMENDATION_KEY = "recommendations";
    //private String popularMovies=BookClient.;
    //private String popular =""
    private ListView lvBooks;
    private ListView PopularMovie;
    private BookAdapter bookAdapter;
    private PopularMoviesAdapter MovieAdapter;


    private BookClient client;
    private ProgressBar progress;
    //private AsyncHttpClient client1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        lvBooks = (ListView) findViewById(R.id.lvBooks);
        //PopularMovie = (ListView) findViewById(R.id.PopularMovie);
        ArrayList<Book> aBooks = new ArrayList<Book>();
        bookAdapter = new BookAdapter(this, aBooks);
        MovieAdapter = new PopularMoviesAdapter(this,aBooks);
        lvBooks.setAdapter(bookAdapter);
        progress = (ProgressBar) findViewById(R.id.progress);
        Log.d("query",client.PopularMovies());
        PopularMovies();
        setupBookSelectedListener();
        //fetchBooks(client.getPopular(new JsonHttpResponseHandler()));
    }
    public void setupBookSelectedListener() {
        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
                //Intent intent1 = new Intent(BookListActivity.this, BookDetailActivity.class);
                intent.putExtra(BOOK_DETAIL_KEY, bookAdapter.getItem(position));
                //intent1.putExtra(BOOK_DETAIL_KEY, MovieAdapter.getItem(position));
                startActivity(intent);
                //startActivity(intent1);
            }
        });
    }
    // Executes an API call to the OpenLibrary search endpoint, parses the results
    // Converts them into an array of book objects and adds them to the adapter
    protected void fetchBooks(String query) {
        progress.setVisibility(ProgressBar.VISIBLE);
        client = new BookClient();
        client.getBooks(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d("Ejecutando","Me estoy ejecutando");
                    Toast toast1=Toast.makeText(getBaseContext(),"Has been found: "+response.getString("total_results")+" results",Toast.LENGTH_LONG);
                    //toast1.setGravity(0,0,((Gravity.END)-50));

                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if(response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("results");
                        if(docs.length()!=0){
                        // Parse json array into array of model objects
                        final ArrayList<Book> books = Book.fromJson(docs);
                        // Remove all books from the adapter
                        bookAdapter.clear();
                        // Load model objects into the adapter
                        for (Book book : books) {
                            bookAdapter.add(book);
                            // add book through the adapter
                        }
                        bookAdapter.notifyDataSetChanged();
                        }
                        else{
                            toast1=Toast.makeText(getBaseContext(),"There's not results :( try again :D",Toast.LENGTH_LONG);
                            toast1.show();
                        }
                    }
                    toast1.show();
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.setVisibility(ProgressBar.GONE);
            }
        });
    }

    private void PopularMovies() {
        progress.setVisibility(ProgressBar.VISIBLE);
        BookListActivity.this.setTitle("PopularMovies");
        client = new BookClient();
        client.getPopularMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d("Ejecutando","Me estoy ejecutando");
                    Toast toast1=Toast.makeText(getBaseContext(),"Has been found: "+response.getString("total_results")+" results",Toast.LENGTH_LONG);
                    //toast1.setGravity(0,0,((Gravity.END)-50));

                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if(response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("results");
                        // Parse json array into array of model objects
                        final ArrayList<Book> books = Book.fromJson(docs);
                        // Remove all books from the adapter
                        MovieAdapter.clear();
                        // Load model objects into the adapter
                        for (Book book : books) {
                            MovieAdapter.add(book);
                            // add book through the adapter
                        }
                        MovieAdapter.notifyDataSetChanged();
                    }
                    //toast1.show();
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.setVisibility(ProgressBar.GONE);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                fetchBooks(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                BookListActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }
}
