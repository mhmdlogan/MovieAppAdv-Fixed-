package com.mhmdlogan.Project1.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mhmdlogan.Project1.R;
import com.mhmdlogan.Project1.data.MovieList;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.SimpleDateFormat;

 //Detailed movie view for the stage one

public class DetailMovieFragment extends Fragment {


    public static final String EXTRA_MOVIE = "movie";
    private final String BASE_URl = "http://image.tmdb.org/t/p/w500/";
    Context context;
    private int mItemPosition;
    private Format mFormatter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItemPosition = getArguments().getInt(DetailMovieActivity.EXTRA_MOVIE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.detail_app_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        TextView plot = (TextView) view.findViewById(R.id.moviedescription);
        ImageView poster = (ImageView) view.findViewById(R.id.poster_image_view);

        TextView releaseDate = (TextView) view.findViewById(R.id.releaseDate);
        TextView rating = (TextView) view.findViewById(R.id.rating);


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        final CollapsingToolbarLayout templayout = collapsingToolbarLayout;
        collapsingToolbarLayout.setTitle(MovieList.get(getActivity()).getSingleResultByPosition(mItemPosition).getOriginalTitle());
        mFormatter = new SimpleDateFormat("yyyy-MM-dd");


        final ImageView imageView = (ImageView) view.findViewById(R.id.detail_header_imageview);
        String header_image = MovieList.get(getActivity()).getSingleResultByPosition(mItemPosition).getBackdropPath();
        Picasso.with(getActivity())
                .load(BASE_URl + header_image)
                .into(imageView, PicassoPalette.with(BASE_URl + header_image, imageView)
                                .use(PicassoPalette.Profile.VIBRANT)
                                .intoBackground(collapsingToolbarLayout, PicassoPalette.Swatch.RGB)
                                .use(PicassoPalette.Profile.VIBRANT_DARK)
                        //TODO: GET GOOD COLOR SCHEME IN P2 PROJECT
                );

        collapsingToolbarLayout = templayout;
        rating.setText(String.valueOf(MovieList.get(getActivity()).getSingleResultByPosition(mItemPosition).getVoteAverage()));
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185" + MovieList.get(getActivity()).getSingleResultByPosition(mItemPosition).getPosterPath()).into(poster);
        releaseDate.setText(MovieList.get(getActivity()).getSingleResultByPosition(mItemPosition).getReleaseDate());
        plot.setText(MovieList.get(getActivity()).getSingleResultByPosition(mItemPosition).getOverview());

        return view;
    }


}
