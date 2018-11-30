package com.example.twozer00.booksearch.booksearch.net;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

public class BookClient {
    //Locale loc=Locale.getDefault().toString();
    public static final String API_BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY="?api_key=a36aa66b935c743a91a78e97f0e4bc9c";
    private static final String UrlLenguage="&language=";
    //Locale locales[] = Locale.getAvailableLocales()
    private String lenguage = Locale.getDefault().getLanguage(); // es
    //private String lenguages=lenguage;
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
            String url = getApiUrl("search/multi" +API_KEY+"&query=");
            client.get(url + URLEncoder.encode(query, "utf-8")+UrlLenguage+lenguage, handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void getReviews(final String id, JsonHttpResponseHandler handler) {
            String url = getApiUrl(id+"/reviews" +API_KEY);
            client.get(url +UrlLenguage+lenguage, handler);
    }

    public String getLenguage(String leng) {
        lenguage=leng;
        return lenguage;
    }

    public void getExtraBookDetails(String id_Movie, JsonHttpResponseHandler handler) {
        String url = getApiUrl("movie/");
        client.get(url + id_Movie + API_KEY+UrlLenguage+lenguage, handler);
        //getRecommendations(id_Movie,handler);
    }
    public void getCastMovie(String id_Movie, JsonHttpResponseHandler handler) {
        String url = getApiUrl("movie/");
        client.get(url + id_Movie +"/credits"+ API_KEY+UrlLenguage+lenguage, handler);
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
    public void getUpcomingMovies(JsonHttpResponseHandler handler) {
        String url =getApiUrl("movie/upcoming/");
        client.get(url +API_KEY+UrlLenguage+lenguage, handler);
    }
    public void getNowPlayingMovies(JsonHttpResponseHandler handler) {
        String url =getApiUrl("movie/now_playing/");
        client.get(url +API_KEY+UrlLenguage+lenguage, handler);
    }
    public void getPopularMovies(JsonHttpResponseHandler handler) {
        String url = getApiUrl("movie/popular/");
        client.get(url +API_KEY+UrlLenguage+lenguage, handler);
    }
    public void getAccountDetails(JsonHttpResponseHandler handler, String session_ID) {
        String url = getApiUrl("account");
        client.get(url +API_KEY+ "&session_id="+session_ID, handler);
    }
    public void getSimilarMovies(String id_Movie, JsonHttpResponseHandler handler) {
        String url = getApiUrl("movie/");
        client.get(url +id_Movie+API_KEY, handler);
    }
    public void getReviews(String id, String Media, JsonHttpResponseHandler handler){
        String url = getApiUrl("");
        client.get(url + Media +"/" + id+ "/reviews"+ API_KEY + UrlLenguage +lenguage, handler );
    }


}
