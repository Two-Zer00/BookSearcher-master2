package com.example.twozer00.booksearch.booksearch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twozer00.booksearch.booksearch.adapters.BookAdapter;
import com.example.twozer00.booksearch.booksearch.api.movieApi;
import com.example.twozer00.booksearch.booksearch.models.Book;
import com.example.twozer00.booksearch.booksearch.models.DeleteSession;
import com.example.twozer00.booksearch.booksearch.models.MovieAccessToken;
import com.example.twozer00.booksearch.booksearch.net.BookClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.twozer00.booksearch.booksearch.LoginActivity.session_id;

public class BookListActivity extends AppCompatActivity {
    public static final String BOOK_DETAIL_KEY = "movie";
    SharedPreferences ShPref;
    public static  String request_Token="";
    //public static final String BOOK_DETAIL_RECOMENDATION_KEY = "recommendations";
    //private String popularMovies=BookClient.;
    //private String popular =""
    private ListView lvBooks;
    //private ListView PopularMovie;
    private BookAdapter bookAdapter;
    private BookClient client;
    private ProgressBar progress;
    private static String API_BASE_URL = "https://api.themoviedb.org/3/";
    public static String API_KEY = "a36aa66b935c743a91a78e97f0e4bc9c";
    private TextView Actor;

    //public static String MediaType;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShPref = getSharedPreferences("Save",Context.MODE_PRIVATE);
        Log.d("SharedPref",ShPref.getString("SessionId",""));
        setContentView(R.layout.activity_book_list);
        lvBooks = (ListView) findViewById(R.id.lvBooks);
        Actor=(TextView) findViewById(R.id.releasedate);
        //PopularMovie = (ListView) findViewById(R.id.PopularMovie);
        ArrayList<Book> aBooks = new ArrayList<Book>();
        bookAdapter = new BookAdapter(this, aBooks);
        //MovieAdapter = new PopularMoviesAdapter(this,aBooks);
        lvBooks.setAdapter(bookAdapter);
        progress = (ProgressBar) findViewById(R.id.progress);
        Log.d("query",client.PopularMovies());
        PopularMovies();

        setupBookSelectedListener();
        //fetchBooks(client.getPopular(new JsonHttpResponseHandler()));
        new getMovieAccesToken().execute();
    }



    public void setupBookSelectedListener() {
        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(BookListActivity.this, BookDetailActivity.class);
                //Intent intent1 = new Intent(BookListActivity.this, BookDetailActivity.class);
                intent.putExtra(BOOK_DETAIL_KEY, bookAdapter.getItem(position));
                //intent.putExtra("MediaType",MediaType);
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
                    Toast toast1=Toast.makeText(getBaseContext(),getResources().getText(R.string.toast_results)+response.getString("total_results")+getResources().getText(R.string.toast_results1),Toast.LENGTH_LONG);
                    //toast1.setGravity(0,0,((Gravity.END)-50));
                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if(response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("results");
                        if(docs.length()!=0){
                        // Parse json array into array of model objects
                        final ArrayList<Book> books = Book.fromJson(docs);
                        //Toast.makeText(getApplicationContext(),docs.length(),Toast.LENGTH_SHORT).show();
                        Log.d("IsTv",  String.valueOf(books.size()));
                        // Remove all books from the adapter
                        bookAdapter.clear();
                        // Load model objects into the adapter
                        for (Book book : books) {

                            bookAdapter.add(book);



                            /*MediaType=book.getMedia_type();
                            Log.d("MediaType",MediaType);*/
                            //bookAdapter.
                            // add book through the adapter
                        }
                        bookAdapter.notifyDataSetChanged();

                            //Toast.makeText(getApplicationContext(),books.size(),Toast.LENGTH_SHORT).show();
                        }
                        else{
                            toast1=Toast.makeText(getBaseContext(),getResources().getText(R.string.noresults),Toast.LENGTH_LONG);
                            toast1.show();
                        }
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


    private void PopularMovies() {
        progress.setVisibility(ProgressBar.VISIBLE);

        BookListActivity.this.setTitle(getResources().getText(R.string.popular));
        client = new BookClient();
        client.getPopularMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d("Ejecutando","Me estoy ejecutando");
                    Toast toast1=Toast.makeText(getBaseContext(),getResources().getText(R.string.toast_results)+response.getString("total_results")+getResources().getText(R.string.toast_results1),Toast.LENGTH_LONG);
                    //toast1.setGravity(0,0,((Gravity.END)-50));

                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if(response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("results");
                        // Parse json array into array of model objects
                        final ArrayList<Book> books = Book.fromJson(docs);
                        //Toast.makeText(getApplicationContext(),docs.length(),Toast.LENGTH_SHORT).show();
                        // Remove all books from the adapter
                        bookAdapter.clear();
                        // Load model objects into the adapter
                        for (Book book : books) {
                            bookAdapter.add(book);
                            // add book through the adapter
                        }
                        bookAdapter.notifyDataSetChanged();

                        //Toast.makeText(getApplicationContext(),books.size(),Toast.LENGTH_SHORT).show();
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
    private void UpComingMovies() {
        progress.setVisibility(ProgressBar.VISIBLE);
        BookListActivity.this.setTitle(getResources().getText(R.string.upcoming));
        client = new BookClient();
        client.getUpcomingMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d("Ejecutando","Me estoy ejecutando");
                    Toast toast1=Toast.makeText(getBaseContext(),getResources().getText(R.string.toast_results)+response.getString("total_results")+" results",Toast.LENGTH_LONG);
                    //toast1.setGravity(0,0,((Gravity.END)-50));

                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if(response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("results");
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
    private void NowPlayingMovies() {
        progress.setVisibility(ProgressBar.VISIBLE);
        BookListActivity.this.setTitle(getResources().getText(R.string.nowplaying));
        client = new BookClient();
        client.getNowPlayingMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d("Ejecutando","Me estoy ejecutando");
                    Toast toast1=Toast.makeText(getBaseContext(),getResources().getText(R.string.toast_results)+response.getString("total_results")+getResources().getText(R.string.toast_results1),Toast.LENGTH_LONG);
                    //toast1.setGravity(0,0,((Gravity.END)-50));

                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if(response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("results");
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
        final MenuItem searchItem=menu.findItem(R.id.action_search);
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

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.action_options){
            final CharSequence[] options={getResources().getText(R.string.popular),getResources().getText(R.string.upcoming),getResources().getText(R.string.nowplaying)};
            final AlertDialog.Builder alertOpcion= new AlertDialog.Builder(BookListActivity.this);
            alertOpcion.setTitle(getResources().getText(R.string.alerDialog));
            alertOpcion.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (options[i].equals(getResources().getText(R.string.popular))){
                        PopularMovies();
                    }
                    else if(options[i].equals(getResources().getText(R.string.upcoming))){
                        UpComingMovies();

                    }
                    else if(options[i].equals(getResources().getText(R.string.nowplaying))){
                        NowPlayingMovies();

                    }

                    else{
                        Toast.makeText(getApplicationContext(),getResources().getText(R.string.nothing_choose), Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                }
            });
            alertOpcion.show();


            return true;
        }
        if(id==R.id.action_leng){
            client = new BookClient();
            final CharSequence[] options={"Español","English","Italian"};
            final AlertDialog.Builder alertOpcion= new AlertDialog.Builder(BookListActivity.this);
            alertOpcion.setTitle(getResources().getText(R.string.select_lenguage));
            alertOpcion.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (options[i].equals("Español")){
                        client.getLenguage("es");
                    }
                    else if(options[i].equals("English")){
                        client.getLenguage("en");

                    }
                    else if(options[i].equals("Italian")){
                        client.getLenguage("it");

                    }
                    else{
                        Toast.makeText(getApplicationContext(),getResources().getText(R.string.nothing_choose), Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                }
            });
            alertOpcion.show();


            return true;
        }
        if(id==R.id.action_login){
            Intent intent = new Intent(BookListActivity.this, LoginActivity.class);
            //Intent intent1 = new Intent(BookListActivity.this, BookDetailActivity.class);
            //intent.putExtra(BOOK_DETAIL_KEY, bookAdapter.getItem(position));
            //intent1.putExtra(BOOK_DETAIL_KEY, MovieAdapter.getItem(position));
            startActivity(intent);


            return true;
        }

        if(id==R.id.action_acount){
            //validateAccount(item);
            Intent intent = new Intent(BookListActivity.this, AcountDetailsActivity.class);
            //Intent intent1 = new Intent(BookListActivity.this, BookDetailActivity.class);
            //intent.putExtra(BOOK_DETAIL_KEY, bookAdapter.getItem(position));
            //intent1.putExtra(BOOK_DETAIL_KEY, MovieAdapter.getItem(position));
            startActivity(intent);


            return true;
        }

        if(id==R.id.action_logout){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            movieApi movieApi = retrofit.create(movieApi.class);

            Call<DeleteSession> call = movieApi.getSuccess(API_KEY,session_id);
            call.enqueue(new Callback<DeleteSession>() {
                @Override
                public void onResponse(Call<DeleteSession> accessTokenCall, Response<DeleteSession> response) {
                    Log.d("LOGOUT", "I WORKED");
                    Log.d("LOGOUT", response.toString());
                    //Log.d("RETROFIT", response.body().getRequest_token());
                    //request_Token=response.body().getRequest_token();
                    SharedPreferences.Editor edit =ShPref.edit();
                    edit.remove("SessionId");
                    edit.apply();

                    Toast.makeText(BookListActivity.this,"Logout success",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<DeleteSession> accessTokenCall, Throwable t) {
                    Log.d("LOGOUT", "I FAILED");
                    // handle failure
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private class getMovieAccesToken extends AsyncTask<URL, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(URL... urls) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            movieApi movieApi = retrofit.create(movieApi.class);

            Call<MovieAccessToken> call = movieApi.getAccesToken(API_KEY);
            call.enqueue(new Callback<MovieAccessToken>() {
                @Override
                public void onResponse(Call<MovieAccessToken> accessTokenCall, Response<MovieAccessToken> response) {
                    Log.d("RETROFIT", "I WORKED");
                    Log.d("RETROFIT", response.toString());
                    Log.d("RETROFIT", response.body().getRequest_token());
                    request_Token=response.body().getRequest_token();
                }

                @Override
                public void onFailure(Call<MovieAccessToken> accessTokenCall, Throwable t) {
                    Log.d("RETROFIT", "I FAILED");
                    // handle failure
                }
            });

            return null;
        }
    }
}
