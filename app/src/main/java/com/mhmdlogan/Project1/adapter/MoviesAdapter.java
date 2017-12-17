package com.mhmdlogan.Project1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhmdlogan.Project1.R;
import com.mhmdlogan.Project1.data.MovieList;
import com.squareup.picasso.Picasso;

 //Created by Mohamed ELhossiny.
 //This is an adapter which is responsible for the movie grid in movies fragment recycler

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    public static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/w185/";
    Context mContext;
    LayoutInflater mLayoutInflater;

    public MoviesAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.row_movie, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;


    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        //Setting Tile for Movie
        holder.mTextView.setText(MovieList.get(mContext)
                .getResultsArrayList()
                .get(position)
                .getOriginalTitle());


        Picasso
                .with(mContext)
                .load(BASE_IMG_URL + MovieList.get(mContext)
                        .getResultsArrayList().get(position)
                        .getPosterPath())
                .placeholder(R.drawable.placeholder_poster)
                .into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return MovieList.get(mContext).getResultsArrayList().size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.movie_poster);
            mTextView = (TextView) itemView.findViewById(R.id.movie_item_title);
        }
    }
}
