package com.mhmdlogan.Project1.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

 // Basic movie list fetcher from the movies DB api

public class MovieList {
    static MovieList sMovieDataList;
    Context mContext;
    private List<Movie> mResultsArrayList = new ArrayList<Movie>();

    MovieList(Context context) {
        mContext = context;
    }

    public static MovieList get(Context context) {
        if (sMovieDataList == null) {
            sMovieDataList = new MovieList(context);
        }
        return sMovieDataList;
    }

    public List<Movie> getResultsArrayList() {
        return mResultsArrayList;
    }

    public void setResultsArrayList(List<Movie> resultsList) {
        for (Movie Results : resultsList)
            mResultsArrayList.add(Results);
    }

    public void setSingleResult(Movie result) {
        mResultsArrayList.add(result);
    }

    public Movie getSingleResultByPosition(int pos) {
        return mResultsArrayList.get(pos);
    }

    public void clearList() {
        mResultsArrayList.clear();
    }
}
