package com.example.twozer00.booksearch.booksearch.models;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import com.example.twozer00.booksearch.booksearch.net.BookClient;

public class Book implements Serializable{
    private String poster_path;
    protected  String id_Movie;
    protected  String title;
    public  String media_type;
    private String overview;
    private String voteAvs;
    private String releaseDate;
    protected  String imagecode;
    protected  String bgimagecode;
    public static String mediaType;
    private String profilepath;

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }
    //private String[] profile_path;


    public String getMedia_type() {
        return media_type;
    }

    public String getId_Movie() {
        return id_Movie;
    }

    public String getRelease_date() {
        return releaseDate;
    }

    public String getVote(){
        return voteAvs;
    }
    public String getTitle() {
        return title;
    }
    public String getOverview() {
        return overview;
    }

    // Get medium sized book cover from covers API
    public String getCoverUrl() {
        return "https://image.tmdb.org/t/p/w185/" + imagecode;
    }
    public String getbgUrl() {
        return "https://image.tmdb.org/t/p/original/" + bgimagecode;
    }
    // Get large sized book cover from covers API
    public String getLargeCoverUrl() {
        return "https://image.tmdb.org/t/p/w780/" + imagecode;
    }
    // Returns a Book given the expected JSON
    public static Book fromJson(JSONObject jsonObject) {
        Book book = new Book();
        try {
            if(jsonObject.has("media_type")){

                if(jsonObject.getString("media_type").equals("movie")){
                    Log.d("MOVIE_MEDIATYPE","MOVIE");
                    book.id_Movie=jsonObject.getString("id");
                    if (jsonObject.has("poster_path"))  {
                        book.imagecode = jsonObject.getString("poster_path");
                    } else if(jsonObject.has("poster_path")) {
                        final JSONArray ids = jsonObject.getJSONArray("poster_path");
                        book.imagecode = ids.getString(0);
                    }

                    if (jsonObject.has("backdrop_path")){
                        book.bgimagecode=jsonObject.getString("backdrop_path");
                    }
                    book.media_type=jsonObject.getString("media_type");
                    book.title = jsonObject.getString("title");
                    book.overview = jsonObject.getString("overview");
                    book.voteAvs= jsonObject.getString("vote_average");
                    book.releaseDate=jsonObject.getString("release_date");


                    Log.d("MOVIE_MEDIATYPE","IsMovie"+book.toString());
                    //Log.d("MOVIE_MEDIATYPE",""+book.toString());
                }
                else if(jsonObject.getString("media_type").equals("tv")){
                    Log.d("TV_MEDIATYPE","TV");
                    book.id_Movie=jsonObject.getString("id");
                    if (jsonObject.has("poster_path"))  {
                        book.imagecode = jsonObject.getString("poster_path");
                    } else if(jsonObject.has("poster_path")) {
                        final JSONArray ids = jsonObject.getJSONArray("poster_path");
                        book.imagecode = ids.getString(0);
                    }

                    if (jsonObject.has("backdrop_path")){
                        book.bgimagecode=jsonObject.getString("backdrop_path");
                    }
                    //book.imagecode = jsonObject.getString("poster_path");
                    book.media_type=jsonObject.getString("media_type");
                    book.title = jsonObject.getString("name");
                    book.overview = jsonObject.getString("overview");
                    book.voteAvs= jsonObject.getString("vote_average");
                    book.releaseDate=jsonObject.getString("first_air_date");
                    Log.d("TV_MEDIATYPE", "IsTV"+book.toString());
                    //Log.d("TV_MEDIATYPE",""+book.toString());
                } else if (jsonObject.getString("media_type").equals("person")) {
                    Log.d("ACTOR_MEDIATYPE","ACTOR");
                    book.id_Movie=jsonObject.getString("id");
                    if (jsonObject.has("profile_path"))  {
                        book.imagecode = jsonObject.getString("profile_path");
                    } else if(jsonObject.has("profile_path")) {
                        final JSONArray ids = jsonObject.getJSONArray("profile_path");
                        book.imagecode = ids.getString(0);
                    }

                    if (jsonObject.has("backdrop_path")){
                        book.bgimagecode=jsonObject.getString("backdrop_path");
                    }
                    book.title = jsonObject.getString("name");
                    if (jsonObject.has("known_for")) {
                        // display comma separated list of publishers
                        final JSONArray production=jsonObject.getJSONArray("known_for");
                        //JSONObject pro_com_id=pro_com.getJSONObject("id");
                        final int numCompanies = production.length();
                        final String[] companies = new String[numCompanies];
                        JSONArray arraycompanies=jsonObject.getJSONArray("known_for");
                        for (int i = 0; i < numCompanies; ++i) {
                            JSONObject object = arraycompanies.getJSONObject(i);
                            companies[i]=object.getString("title");
                            //id_Companieslogo.add(i,object.getString("logo_path"));
                            //Picasso.with(getBaseContext()).load(Uri.parse(book.getLargeCoverUrl())).error(R.drawable.ic_nocover).into(ivBookCover);

                        }

                        book.overview =TextUtils.join(", ", companies);
                    }
                    book.media_type=jsonObject.getString("media_type");
                    book.voteAvs= jsonObject.getString("popularity");

                    book.releaseDate="Actor";
                    Log.d("ACTOR_MEDIATYPE","IsActor"+book.toString());
                }
            }
            else{
                Log.d("mainpage_MEDIATYPE","in");

                book.id_Movie=jsonObject.getString("id");
                if (jsonObject.has("poster_path"))  {
                book.imagecode = jsonObject.getString("poster_path") ;
                } else if(jsonObject.has("poster_path")) {
                final JSONArray ids = jsonObject.getJSONArray("poster_path");
                book.imagecode = ids.getString(0);
                }

                if (jsonObject.has("backdrop_path")){
                book.bgimagecode=jsonObject.getString("backdrop_path");
                }
                if(!jsonObject.has(book.media_type)){

                    book.media_type="movie";

                }
                book.title = jsonObject.getString("title");
                book.overview = jsonObject.getString("overview");
                book.voteAvs= jsonObject.getString("vote_average");
                book.releaseDate=jsonObject.getString("release_date");


                Log.d("Movie_Book","Is movie"+book.toString());

            }

/*
            Log.d("MOVIE_MEDIATYPE",""+book.toString());

            book.id_Movie=jsonObject.getString("id");
            //if (jsonObject.has("poster_path"))  {
            book.imagecode = "/uyJgTzAsp3Za2TaPiZt2yaKYRIR.jpg" ;//jsonObject.getString("poster_path");
            //} else if(jsonObject.has("poster_path")) {
            //final JSONArray ids = jsonObject.getJSONArray("poster_path");
            //book.imagecode = ids.getString(0);
            //}

            //if (jsonObject.has("backdrop_path")){
            book.bgimagecode=jsonObject.getString("backdrop_path");
            //}
            book.title = jsonObject.getString("title");
            book.overview = jsonObject.getString("overview");
            book.voteAvs= jsonObject.getString("vote_average");
            book.releaseDate=jsonObject.getString("release_date");


            Log.d("Movie_Book","Is movie"+book.toString());
*/
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return book;
    }
    // Decodes array of book json results into business model objects
    public static ArrayList<Book> fromJson(JSONArray jsonArray) {
        ArrayList<Book> books = new ArrayList<Book>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject bookJson = null;
            try {
                bookJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Book book = Book.fromJson(bookJson );
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public String toString() {
        return "media type: "+ this.media_type+"\n"+"IDa" + this.id_Movie + "title: " + this.title +"\n"+ "posterPath: " + this.imagecode +"\n"+ "Overview: " + this.overview + "\n"+"voteAvg: " + this.voteAvs +
                "\n"+"releaseDate: " + this.releaseDate + "\n"+"bkgImage: " + this.bgimagecode;
    }
}
