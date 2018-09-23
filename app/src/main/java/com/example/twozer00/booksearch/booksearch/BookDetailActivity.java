package com.example.twozer00.booksearch.booksearch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.twozer00.booksearch.booksearch.adapters.MovieRecomendationAdapter;
import com.example.twozer00.booksearch.booksearch.models.Book;
import com.example.twozer00.booksearch.booksearch.models.MovieRecomendation;
import com.example.twozer00.booksearch.booksearch.net.BookClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView ivBookCover;
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
    private TextView ActorsImage;
    private BookClient client;
    private ArrayList<String> id_Companieslogo=new ArrayList<String>();
    private ListView lvCompanies;
    //private ListView Recomendations;
    //private MovieRecomendationAdapter MovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ivBookCover = (ImageView) findViewById(R.id.ivBookCover);
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
        // Use the book to populate the data into our views
        Book book = (Book) getIntent().getSerializableExtra(BookListActivity.BOOK_DETAIL_KEY);
        Log.d("BookCreate","Creado");
        loadBook(book);


    }

    // Populate data for the book
    private void loadBook(final Book book) {

        //change activity title
        this.setTitle(book.getTitle());
        // Populate data
        Picasso.with(this).load(Uri.parse(book.getLargeCoverUrl())).error(R.drawable.ic_nocover).into(ivBookCover);
        //Picasso.with(this).load(Uri.parse(BookClient.get())).error(R.drawable.ic_nocover).into(ivBookCover);

        //Picasso.with(this).load(Uri.parse(book.getCompanieLogo(id_Companieslogo))).error(R.string.CompanyName).into(ivCompaniesLogo);
        tvTitle.setText(book.getTitle());
        Rate.setText((book.getVote()));
        release_date.setText("Release date: "+book.getRelease_date());
        tvOverview.setText(book.getOverview());
        //tvGenre.setText(book.getGenre());
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
                        tvPageCount.setText(((df.format(hrs))) + " hrs");
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
            setShareIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent() {
        ImageView ivImage = (ImageView) findViewById(R.id.ivBookCover);
        final TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(ivImage);
        // Construct a ShareIntent with link to image
        Intent shareIntent = new Intent();
        // Construct a ShareIntent with link to image
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, (String)tvTitle.getText());
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        // Launch share menu
        startActivity(Intent.createChooser(shareIntent, "Share Image"));

    }

    // Returns the URI path to the Bitmap displayed in cover imageview
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
