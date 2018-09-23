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
    protected  String id_Movie;
    private String genre;
    protected  String title;
    private String overview;
    private String voteAvs;
    private String releaseDate;
    protected  String imagecode;
    private String profilepath;
    //private String[] profile_path;

    public String getOpenLibraryId() {
        return imagecode;
    }

    public String getId_Movie() {
        return id_Movie;
    }
    public String getRelease_date() {
        return releaseDate;
    }

    public String getVote(){
        return voteAvs +"/10";
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
    public String getActorUrl(String[] profile_path) {
        return "https://image.tmdb.org/t/p/w185/" + profile_path;
    }

    // Get large sized book cover from covers API
    public String getLargeCoverUrl() {
        return "https://image.tmdb.org/t/p/w342/" + imagecode;
    }
    // Returns a Book given the expected JSON
    public static Book fromJson(JSONObject jsonObject) {
        Book book = new Book();
        try {
            // Deserialize json into object fields
            // Check if a cover edition is available
            book.id_Movie=jsonObject.getString("id");
            if (jsonObject.has("poster_path"))  {
                book.imagecode = jsonObject.getString("poster_path");
            } else if(jsonObject.has("poster_path")) {
                final JSONArray ids = jsonObject.getJSONArray("poster_path");
                book.imagecode = ids.getString(0);
            }
            else if(jsonObject.has("profile_path")){
                book.profilepath=jsonObject.getString("profile_path");
            }
            book.title = jsonObject.getString("title");
            book.overview = jsonObject.getString("overview");
            book.voteAvs= jsonObject.getString("vote_average");
            book.releaseDate=jsonObject.getString("release_date");
            //book.tvPageCount=jsonObject.getString("release_date");
            //book.genre = getGenre(jsonObject);
            //book.genre = getGenre(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return book;
    }

    // Return comma separated author list when there is more than one author
    private static String getGenre(final JSONObject jsonObject) {
        try {
            final JSONArray genres = jsonObject.getJSONArray("genres");
            int numGenres = genres.length();

            final String[] GenresStrings = new String[numGenres];
            final String[] GenresLocal = new String[numGenres];
            for (int i=0 ;i<numGenres;++i){
                GenresLocal[i]=genres.getJSONObject(i).has("genres") ? jsonObject.getString("genres"):"";
            }

            /*for (int i = 0; i < numGenres; ++i) {
                authorStrings[i] = genres.getString(i);
            }*/
            return TextUtils.join(", ", GenresStrings);

        } catch (JSONException e) {
            return "";
        }
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
}
