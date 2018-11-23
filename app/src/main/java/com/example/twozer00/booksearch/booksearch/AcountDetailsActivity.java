package com.example.twozer00.booksearch.booksearch;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twozer00.booksearch.booksearch.api.movieApi;
import com.example.twozer00.booksearch.booksearch.models.AccountDetails;
import com.example.twozer00.booksearch.booksearch.models.MovieAccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.twozer00.booksearch.booksearch.BookListActivity.API_KEY;
import static com.example.twozer00.booksearch.booksearch.LoginActivity.session_id;
import static com.example.twozer00.booksearch.booksearch.net.BookClient.API_BASE_URL;

public class AcountDetailsActivity extends Activity {

    SharedPreferences ShPref;

    private TextView username;
    private TextView lenguage;
    private TextView name;
    private TextView country;
    private ImageView avatar;
    private String gravatar = "https://www.gravatar.com/avatar/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_details);
        name =(TextView) findViewById(R.id.AccountName);
        username =(TextView) findViewById(R.id.AccountUserame);
        avatar = (ImageView) findViewById(R.id.avatar);
        lenguage =(TextView) findViewById(R.id.Accountlenguage);
        country =(TextView) findViewById(R.id.AccountCountry);

        ShPref = getSharedPreferences("Save",Context.MODE_PRIVATE);


        if(ShPref.getString("SessionId","")!=""){
            session_id=ShPref.getString("SessionId","");
            accountDetails();
        }



    }

    public void accountDetails(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieApi movieApi = retrofit.create(movieApi.class);

        Call<AccountDetails> call = movieApi.getAccountDetails(API_KEY,session_id);
        call.enqueue(new Callback<AccountDetails>() {
            @Override
            public void onResponse(Call<AccountDetails> accessTokenCall, Response<AccountDetails> response) {
                Log.d("RETROFIT", "I WORKED");
                Log.d("RETROFIT", response.toString());
                Log.d("RETROFIT", response.body().getName());
                gravatar=gravatar+response.body().getHash();
                Picasso.get().load(Uri.parse(gravatar)).error(R.drawable.ic_nocover).into(avatar);
                username.setText(response.body().getUsername());
                name.setText(response.body().getName());
                country.setText(response.body().getIso_3166_1());
                lenguage.setText(response.body().getIso_639_1());

            }

            @Override
            public void onFailure(Call<AccountDetails> accessTokenCall, Throwable t) {
                Log.d("RETROFIT", "I FAILED");
                // handle failure
            }
        });

    }
}
