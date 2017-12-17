package com.mhmdlogan.Project1.ui;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.mhmdlogan.Project1.R;

// By Mohamed Elhossiny (mhmdlogan)

public class MainActivity extends AppCompatActivity implements MovieFragment.MoviesCallback {
    public static final String INTENT_EXTRA = "com.apherio.philims>INTNETTAG";
    //USING THIS FOR GRID ANIMATION PURPOSE
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public void getCurrentItem(int position) {
        Toast.makeText(this, "Called " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra(INTENT_EXTRA, position);

        startActivity(intent);
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        sendMessage();
    }


    private void sendMessage() {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
