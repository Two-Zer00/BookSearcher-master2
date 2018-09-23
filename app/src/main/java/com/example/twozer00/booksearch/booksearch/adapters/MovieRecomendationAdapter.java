package com.example.twozer00.booksearch.booksearch.adapters;

import android.content.Context;
import android.graphics.Movie;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twozer00.booksearch.booksearch.R;
import com.example.twozer00.booksearch.booksearch.models.Book;
import com.example.twozer00.booksearch.booksearch.models.MovieRecomendation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieRecomendationAdapter extends ArrayAdapter<Book> {

    private static class ViewHolder{
        public ImageView ivCover;
        public TextView tvTitle;
        //public TextView tvGenre;
        //public TextView tvOverview;
        //public TextView tvVoteAvs;
        //public TextView release_date;
    }

    public MovieRecomendationAdapter(Context context, ArrayList<Book> Movie) {
        super(context, 0, Movie);
    }

    // Translates a particular `Book` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Book Movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        MovieRecomendationAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new MovieRecomendationAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.ivCover = (ImageView)convertView.findViewById(R.id.ivBookCover);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
            //viewHolder.tvOverview = (TextView)convertView.findViewById(R.id.tvOverview);
            //viewHolder.release_date = (TextView)convertView.findViewById(R.id.release_date);
            //viewHolder.tvVoteAvs = (TextView)convertView.findViewById(R.id.tvVoteAvs);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MovieRecomendationAdapter.ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        //viewHolder.tvTitle.setText(book.getTitle());
        //viewHolder.tvGenre.setText(book.getGenre());
        //viewHolder.tvVoteAvs.setText(book.getVote());
        //viewHolder.release_date.setText(book.getRelease_date());
        Picasso.with(getContext()).load(Uri.parse(Movie.getCoverUrl())).error(R.id.tvTitle).into(viewHolder.ivCover);
        // Return the completed view to render on screen
        return convertView;
    }
}
