package com.example.twozer00.booksearch.booksearch;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);
        //new Handler().postDelayed(new Runnable() {
        Intent intent = new Intent(this,GridMovies.class);
        startActivity(intent);
        finish();
           // },4000);
    }
}
