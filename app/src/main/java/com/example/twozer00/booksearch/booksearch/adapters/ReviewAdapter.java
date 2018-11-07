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
import com.example.twozer00.booksearch.booksearch.models.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends ArrayAdapter<Review> {
    private static class ViewHolder {
        public TextView author;
        public TextView content;
        public TextView id;
        public Context context;
    }


    public ReviewAdapter(Context context, ArrayList<Review> aBooks) {
        super(context, 0, aBooks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Review review = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ReviewAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ReviewAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_review, parent, false);
            viewHolder.author = (TextView) convertView.findViewById(R.id.reviewAuthor);
            viewHolder.content = (TextView) convertView.findViewById(R.id.reviewContent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ReviewAdapter.ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.author.setText(review.getAuthor());
        viewHolder.content.setText(review.getContent());
        return convertView;
    }

}
