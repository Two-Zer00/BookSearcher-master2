package com.example.twozer00.booksearch.booksearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.twozer00.booksearch.booksearch.BookDetailActivity;
import com.example.twozer00.booksearch.booksearch.GridMovies;
import com.example.twozer00.booksearch.booksearch.R;
import com.example.twozer00.booksearch.booksearch.models.Element;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private ArrayList<Element> dataset;
    private Context context;
    private Element el;

    public GridAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        el = dataset.get(i);
        //viewHolder.nombreTextView.setText(el.getName());
        Picasso.get().load(Uri.parse(el.getPoster_path())).error(R.drawable.ic_nocover).into(viewHolder.Poster);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView Poster;
        private CardView item;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Poster = (ImageView) itemView.findViewById(R.id.poster);
            item = (CardView) itemView.findViewById(R.id.items);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Poster = (ImageView) itemView.findViewById(R.id.poster);
            el = dataset.get(getAdapterPosition());
            switch (view.getId()) {
                case R.id.items:
                    Intent i = new Intent(view.getContext(), BookDetailActivity.class);
                    //Bitmap bitmap = drawableToBitmap(fotoImageView.getDrawable());
                    //ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    //bitmap.compress(Bitmap.CompressFormat.PNG, 100, bs);
                    //i.putExtra("POKEMON_ID", p.getNumber());
                    //i.putExtra("POKEMON_NAME", p.getName());
                    //i.putExtra("POKEMON_IMAGE", bs.toByteArray());
                    view.getContext().startActivity(i);
                    break;
            }

        }
    }
}
