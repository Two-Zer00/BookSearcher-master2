package com.example.twozer00.booksearch.booksearch;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.twozer00.booksearch.booksearch.api.movieApi;
import com.example.twozer00.booksearch.booksearch.models.Book;
import com.example.twozer00.booksearch.booksearch.models.Rating;
import com.example.twozer00.booksearch.booksearch.net.BookClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.twozer00.booksearch.booksearch.BookListActivity.API_KEY;
import static com.example.twozer00.booksearch.booksearch.LoginActivity.session_id;
import static com.example.twozer00.booksearch.booksearch.net.BookClient.API_BASE_URL;

public class BookDetailActivity extends AppCompatActivity {
    SharedPreferences ShPref;
    private ImageView ivBookCover;
    private ImageView bgImage;
    private ImageView MovieActor;
    /*private ImageView ivMovieCoverR;
    private ImageView ivCompaniesLogo;
    private TextView CompaniesLogo;*/
    private TextView tvTitle;
    private TextView Rate;
    private TextView tvOverview;
    private TextView tvGenre;
    private TextView tvPublisher;
    private TextView tvPageCount;
    private TextView release_date;
    private TextView cast;
    private RatingBar stars;
    private TextView ActorsImage;
    private BookClient client;
    private ArrayList<String> id_Companieslogo=new ArrayList<String>();
    private ListView lvCompanies;
    //private ListView Recomendations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShPref =getSharedPreferences("Save",Context.MODE_PRIVATE);
        setContentView(R.layout.activity_book_detail);
        ivBookCover = (ImageView) findViewById(R.id.ivBookCover);
        stars = (RatingBar) findViewById(R.id.starRate);
        bgImage = (ImageView) findViewById(R.id.BgImage);
        //ivMovieCoverR = (ImageView) findViewById(R.id.ivMovieCoverR);
        //ivCompaniesLogo = (ImageView) findViewById(R.id.ivCompaniesLogo);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvGenre = (TextView) findViewById(R.id.tvGenre);
        Rate=(TextView) findViewById(R.id.RateAvs);
        tvPublisher = (TextView) findViewById(R.id.tvPublisher);
        tvPageCount = (TextView) findViewById(R.id.tvPageCount);
        release_date = (TextView) findViewById(R.id.release_date);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        cast=(TextView) findViewById(R.id.cast);
        Intent i= getIntent();
        Bundle b = i.getExtras();
        // Use the book to populate the data into our views
        final Book book = (Book) getIntent().getSerializableExtra(BookListActivity.BOOK_DETAIL_KEY);
        Log.d("BookCreate","Creado");
        final Button button = findViewById(R.id.button1);
        final Button Send = findViewById(R.id.sendRate);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailActivity.this, MovieReviewActivity.class);
                //Intent intent1 = new Intent(BookListActivity.this, BookDetailActivity.class);
                //intent1.putExtra(BOOK_DETAIL_KEY, MovieAdapter.getItem(position));
                startActivity(intent);
                            }
        }        );
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                movieApi movieApi = retrofit.create(movieApi.class);

                Call<Rating> call = movieApi.setRating(book.getId_Movie(),API_KEY,ShPref.getString("SessionId",""),String.valueOf(stars.getRating()*2));
                call.enqueue(new Callback<Rating>() {
                    @Override
                    public void onResponse(Call<Rating> rate, Response<Rating> response) {
                        Log.d("RATING", "I WORKED");
                        Log.d("RATING", response.toString());
                        Log.d("RATING", String.valueOf(stars.getRating()));

                        Toast.makeText(BookDetailActivity.this,"Tu califcación ha sido enviada",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Rating> rate, Throwable t) {
                        Log.d("RATING", "I FAILED");
                        // handle failure
                    }
                });


            }
        });

        /*stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, final float rating,
                                        boolean fromUser) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                movieApi movieApi = retrofit.create(movieApi.class);

                Call<Rating> call = movieApi.setRating(book.getId_Movie(),API_KEY,session_id,String.valueOf(rating*2));
                call.enqueue(new Callback<Rating>() {
                    @Override
                    public void onResponse(Call<Rating> rate, Response<Rating> response) {
                        Log.d("RATING", "I WORKED");
                        Log.d("RATING", response.toString());
                        Log.d("RATING", String.valueOf(rating));

                        //Toast.makeText(BookListActivity.this,"Logout success",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Rating> rate, Throwable t) {
                        Log.d("RATING", "I FAILED");
                        // handle failure
                    }
                });
            }
        });*/

        loadBook(book);


    }

    // Populate data for the book
    private void loadBook(final Book book) {

        //change activity title
        this.setTitle(book.getTitle());
        stars.setRating(Float.valueOf(book.getVote())/2);
        // Populate data
        Picasso.get().load(Uri.parse(book.getLargeCoverUrl())).error(R.drawable.ic_nocover).into(ivBookCover);
        //Picasso.with(this).load(Uri.parse(BookClient.get())).error(R.drawable.ic_nocover).into(ivBookCover);
        Picasso.get().load(Uri.parse(book.getbgUrl())).error(R.drawable.ic_nocover).into(bgImage);
        //Picasso.with(this).load(Uri.parse(book.getCompanieLogo(id_Companieslogo))).error(R.string.CompanyName).into(ivCompaniesLogo);
        tvTitle.setText(book.getTitle());
        Rate.setText((book.getVote()));
        release_date.setText(book.getRelease_date());
        tvOverview.setText(book.getOverview());
        // fetch extra book data from books API
        client = new BookClient();
        Log.d("onSucess",book.getId_Movie());
        client.getExtraBookDetails(book.getId_Movie(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("onSucess","execute123");
                try {
                    if (response.has("production_companies")) {
                        // display comma separated list of publishers
                        final JSONArray production=response.getJSONArray("production_companies");
                        //JSONObject pro_com_id=pro_com.getJSONObject("id");
                        final int numCompanies = production.length();
                        final String[] companies = new String[numCompanies];
                        JSONArray arraycompanies=response.getJSONArray("production_companies");
                        for (int i = 0; i < numCompanies; ++i) {
                            JSONObject object = arraycompanies.getJSONObject(i);
                            companies[i]=object.getString("name");
                            //id_Companieslogo.add(i,object.getString("logo_path"));
                            //Picasso.with(getBaseContext()).load(Uri.parse(book.getLargeCoverUrl())).error(R.drawable.ic_nocover).into(ivBookCover);

                        }

                        tvPublisher.setText(TextUtils.join("\n", companies));


                    }
                    if(response.has("genres")){
                        Log.d("genres","execute");
                        final JSONArray genre=response.getJSONArray("genres");
                        final int numGenres = genre.length();
                        final String[] genres = new String[numGenres];
                        JSONArray arraygenres=response.getJSONArray("genres");
                        for (int i = 0; i < numGenres; ++i) {
                            JSONObject object = arraygenres.getJSONObject(i);
                            genres[i]=object.getString("name");
                            //id_Companieslogo.add(i,object.getString("logo_path"));
                            //Picasso.with(getBaseContext()).load(Uri.parse(book.getLargeCoverUrl())).error(R.drawable.ic_nocover).into(ivBookCover);

                        }
                        tvGenre.setText(TextUtils.join(", ",genres));

                    }
                    if (response.has("runtime")) {
                        Log.d("runtime","execute1234");
                        double time=(response.getDouble("runtime"));
                        DecimalFormat df = new DecimalFormat("#.0");
                        double hrs= (time/60);
                        tvPageCount.setText(((df.format(hrs))));
                        //Picasso.with(getBaseContext()).load(Uri.parse(book.getLargeCoverUrl())).error(R.drawable.ic_nocover).into(ivBookCover);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        client.getCastMovie(book.getId_Movie(),new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("onSucess","execute123");
                try {
                    if (response.has("cast")) {
                        // display comma separated list of publishers
                        final JSONArray actor=response.getJSONArray("cast");
                        //JSONObject pro_com_id=pro_com.getJSONObject("id");
                        final int numCast = actor.length();
                        final String[] actors = new String[numCast];
                        final String[] imagecode =new String[numCast];
                        JSONArray arraycast=response.getJSONArray("cast");
                        for (int i = 0; i < numCast; ++i) {
                            JSONObject object = arraycast.getJSONObject(i);
                            actors[i]=object.getString("name");
                            imagecode[i]=object.getString("profile_path");
                            //Picasso.with(getBaseContext()).load(Uri.parse(book.getLargeCoverUrl())).error(R.drawable.ic_nocover).into(ivBookCover);

                        }

                        cast.setText(TextUtils.join("\n", actors));
                        //Picasso.load(Uri.parse(book.getCoverUrl())).error(R.drawable.ic_nocover).into(MovieActor);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_share) {
            //shareImage();
            return true;
        }if(id==R.id.action_downloadimage){
            if(Permissions()){
                saveImage(ivBookCover);
            }
            else{
                Toast.makeText(this,"You have permission denied",Toast.LENGTH_LONG);
            }
            //saveImage(ivBookCover);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean Permissions(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }
        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
                &&(checkSelfPermission(READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }
        if(shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)
                ||(shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE))){
            loadDialogInformation();
        }
        else {

            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE},100);
        }
return false;
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if(grantResults.length == 2
                    && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                saveImage(ivBookCover);
            }
            else{
                AllowPermissionsManually();
            }
        }
        }

    private void AllowPermissionsManually() {
        AlertDialog.Builder dialog= new AlertDialog.Builder(BookDetailActivity.this);
        dialog.setTitle("Unable permissions");
        dialog.setMessage("You must accept the permission for the correctly working of the app");
        dialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE},100);
            }
        });
        dialog.show();
    }


    private void loadDialogInformation() {
        final CharSequence[] options={"yes","no"};
        final AlertDialog.Builder alertOpcion= new AlertDialog.Builder(BookDetailActivity.this);
        alertOpcion.setTitle("You want configurated manually permissions");
        alertOpcion.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (options[i].equals("yes")){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"The permission wasn't allow", Toast.LENGTH_LONG).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpcion.show();
    }

    public void saveImage(ImageView imageView) {
        //convertir imagen
        imageView.buildDrawingCache();
        Bitmap bmap = imageView.getDrawingCache();

        //guardar imagen
        save savefile = new save();
        savefile.SaveImage(getApplicationContext(), bmap);
    }

    public void loadReviews(){
        Intent intent = new Intent();



    }
}
