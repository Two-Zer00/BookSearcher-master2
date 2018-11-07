package com.example.twozer00.booksearch.booksearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.twozer00.booksearch.booksearch.adapters.BookAdapter;
import com.example.twozer00.booksearch.booksearch.adapters.ReviewAdapter;
import com.example.twozer00.booksearch.booksearch.models.Book;
import com.example.twozer00.booksearch.booksearch.models.Review;
import com.example.twozer00.booksearch.booksearch.net.BookClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieReviewActivity extends AppCompatActivity {
    private ListView reviewList;
    private ReviewAdapter reviewAdapter;
    private ProgressBar progress;
    private BookClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_list);
        reviewList = (ListView) findViewById(R.id.reviewList);
        //PopularMovie = (ListView) findViewById(R.id.PopularMovie);
        ArrayList<Review> reviews = new ArrayList<Review>();
        reviewAdapter = new ReviewAdapter(this, reviews);
        //MovieAdapter = new PopularMoviesAdapter(this,aBooks);
        reviewList.setAdapter(reviewAdapter);
        progress = (ProgressBar) findViewById(R.id.Rprogressbar);
        Book book = (Book) getIntent().getSerializableExtra(BookListActivity.BOOK_DETAIL_KEY);
        Reviews(book);
    }

    private void Reviews(final Book review){
        progress.setVisibility(ProgressBar.VISIBLE);
        client = new BookClient();
        Log.d("onSucess",review.getId_Movie());
        client.getReviews(review.getId_Movie(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d("Ejecutando","Me estoy ejecutando");
                    //Toast toast1=Toast.makeText(getBaseContext(),getResources().getText(R.string.toast_results)+response.getString("total_results")+getResources().getText(R.string.toast_results1),Toast.LENGTH_LONG);
                    //toast1.setGravity(0,0,((Gravity.END)-50));
                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray docs = null;
                    if(response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("results");
                        if(docs.length()!=0){
                            // Parse json array into array of model objects
                            final ArrayList<Review> reviews = Review.fromJson(docs);
                            // Remove all books from the adapter
                            reviewAdapter.clear();
                            // Load model objects into the adapter
                            for (Review review : reviews) {
                                reviewAdapter.add(review);
                                //bookAdapter.
                                // add book through the adapter
                            }
                            reviewAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getBaseContext(),getResources().getText(R.string.noresults),Toast.LENGTH_LONG);
                        }
                    }
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
}
