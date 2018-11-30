package com.example.twozer00.booksearch.booksearch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twozer00.booksearch.booksearch.adapters.ReviewAdapter;
import com.example.twozer00.booksearch.booksearch.api.movieApi;
import com.example.twozer00.booksearch.booksearch.models.Book;
import com.example.twozer00.booksearch.booksearch.models.Rating;
import com.example.twozer00.booksearch.booksearch.models.Review;
import com.example.twozer00.booksearch.booksearch.net.BookClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.twozer00.booksearch.booksearch.BookListActivity.API_KEY;
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
    private ListView lvreviews;
    private ReviewAdapter reviewAdapter;
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
        ArrayList<Review> aReviews = new ArrayList<Review>();
        reviewAdapter = new ReviewAdapter(this,aReviews);
        lvreviews = (ListView)findViewById(R.id.LVreviews);
        setListViewHeightBasedOnChildren(lvreviews);
        lvreviews.setAdapter(reviewAdapter);

        Intent i= getIntent();
        Bundle b = i.getExtras();


        // Use the book to populate the data into our views
        final Book book = (Book) getIntent().getSerializableExtra(BookListActivity.BOOK_DETAIL_KEY);
        /*String MediaType=book.getMedia_type();
        Log.d("MediaType",MediaType);*/
        Log.d("BookCreate","Creado");

        final Button Send = findViewById(R.id.sendRate);


        //Log.d("Mediatype1", book.getMedia_type());

        if(book.media_type.equals(null)){
            book.setMedia_type("movie");
        }

        if(ShPref.getString("SessionId","").equals("")){
            stars.setIsIndicator(true);
        }

        if (book.getMedia_type().equals("person")){
            Send.setVisibility(View.INVISIBLE);
        }
        else {


            Send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ShPref.getString("SessionId","").equals("")){
                        Toast.makeText(getBaseContext(),getResources().getText(R.string.rate_error1),Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(API_BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();

                        final movieApi movieApi = retrofit.create(movieApi.class);


                        Call<Rating> call = movieApi.setRating(book.getMedia_type(), book.getId_Movie(), API_KEY, ShPref.getString("SessionId", ""), String.valueOf(stars.getRating() * 2));
                        call.enqueue(new Callback<Rating>() {
                            @Override
                            public void onResponse(Call<Rating> rate, Response<Rating> response) {
                                Log.d("RATING", "I WORKED");
                                Log.d("RATING", response.toString());
                                Log.d("RATING", String.valueOf(stars.getRating()));

                                Toast.makeText(BookDetailActivity.this, "Tu califcación ha sido enviada", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Rating> rate, Throwable t) {
                                Log.d("RATING", "I FAILED");
                                // handle failure
                            }


                        });


                    }}
            });
        }


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

    protected  void fetchReviewes (String id,String Media){
        client = new BookClient();
        Log.d("Review ", "Hola");
        client.getReviews( id, Media, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess (int statusCode, Header[] headers, JSONObject response){
                try {
                    Log.d("Review" ,"Ejecutando");
                    JSONArray docs = null;
                    if (response != null){
                        docs = response.getJSONArray("results");
                        if (docs.length()!= 0){
                            final ArrayList<Review> reviews = Review.fromJson(docs);
                            Log.d("Review" ,String.valueOf(reviews.size()));

                            reviewAdapter.clear();
                            int i =1;
                            for (Review review: reviews){
                                Log.d("Rcount1" ,String.valueOf(i));
                                i++;
                                reviewAdapter.add(review);
                            }
                            reviewAdapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(getBaseContext(),getResources().getText(R.string.noresults),Toast.LENGTH_LONG).show();

                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Review" , String.valueOf(statusCode)+ "  "+ responseString);

            }
        });

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ReviewAdapter listAdapter = (ReviewAdapter) listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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
        Log.d("REjecutando", "Si llegue");
        fetchReviewes(book.getId_Movie(),book.getMedia_type());
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
