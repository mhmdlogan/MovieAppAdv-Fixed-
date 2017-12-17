package com.mhmdlogan.Project1.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mhmdlogan.Project1.R;


public class DetailMovieActivity extends AppCompatActivity {


    public static final String EXTRA_MOVIE = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        //Fragment Declaration
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.detailMovie, createFragment()).commit();


    }


    DetailMovieFragment createFragment() {
        Intent intent = getIntent();
        int data_position = intent.getIntExtra(MainActivity.INTENT_EXTRA, 0);
        DetailMovieFragment fragment = new DetailMovieFragment();
        Bundle positionBundle = new Bundle();
        positionBundle.putInt(EXTRA_MOVIE, data_position);
        fragment.setArguments(positionBundle);
        return fragment;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailMovieActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


}
