package com.example.twozer00.booksearch.booksearch.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twozer00.booksearch.booksearch.R;
import com.example.twozer00.booksearch.booksearch.models.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TvAdapter extends ArrayAdapter<Book> {

    private static class ViewHolder {
        public ImageView ivCover;
        public TextView tvTitle;
        public TextView tvGenre;
        public TextView tvOverview;
        public TextView tvVoteAvs;
        public TextView releaseDate;
        private ImageView backgroundImage;
    }

    public TvAdapter(Context context, ArrayList<Book> aBooks) {
        super(context, 0, aBooks);
    }

    // Translates a particular `Book` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Book book = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_book, parent, false);
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.ivBookCover);
            viewHolder.backgroundImage = (ImageView) convertView.findViewById(R.id.imagebg);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.releaseDate = (TextView) convertView.findViewById(R.id.release_date);
            viewHolder.tvVoteAvs = (TextView) convertView.findViewById(R.id.tvVoteAvs);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.tvTitle.setText(book.getTitle());
        //viewHolder.tvGenre.setText(book.getGenre());
        viewHolder.tvOverview.setText(book.getOverview());
        viewHolder.tvVoteAvs.setText(book.getVote());
        //Log.d("Date","release date created");
        viewHolder.releaseDate.setText(book.getRelease_date());
        //Log.d("Date","release date created"+book.getRelease_date());
        Picasso.get().load(Uri.parse(book.getCoverUrl())).error(R.drawable.ic_nocover).into(viewHolder.ivCover);
        return convertView;
    }}
