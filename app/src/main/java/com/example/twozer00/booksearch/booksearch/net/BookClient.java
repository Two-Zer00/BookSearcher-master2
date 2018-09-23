package com.example.twozer00.booksearch.booksearch.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BookClient {
    private static final String API_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY="?api_key=a36aa66b935c743a91a78e97f0e4bc9c";
    private AsyncHttpClient client;

    public BookClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public void getBooks(final String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("search/movie" +API_KEY+"&query=");
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void getExtraBookDetails(String id_Movie, JsonHttpResponseHandler handler) {
        String url = getApiUrl("movie/");
        client.get(url + id_Movie + API_KEY, handler);
        //getRecommendations(id_Movie,handler);
    }
    public void getCastMovie(String id_Movie, JsonHttpResponseHandler handler) {
        String url = getApiUrl("movie/");
        client.get(url + id_Movie +"/credits"+ API_KEY, handler);
        //getRecommendations(id_Movie,handler);
    }

    public void getCompaniesLogo(String id_Company, JsonHttpResponseHandler handler) {
        String url = getApiUrl("company/");
        client.get(url +id_Company+"/images"+API_KEY, handler);
    }
    public static String PopularMovies() {
        String url ="popular/"+API_KEY;
    return url;
    }
        public void getPopularMovies(JsonHttpResponseHandler handler) {
        String url = getApiUrl("movie/popular/");
        client.get(url +API_KEY, handler);
    }
    public void getSimilarMovies(String id_Movie, JsonHttpResponseHandler handler) {
        String url = getApiUrl("movie/");
        client.get(url +id_Movie+API_KEY, handler);
    }


}
