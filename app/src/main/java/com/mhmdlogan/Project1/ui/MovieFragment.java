package com.mhmdlogan.Project1.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mhmdlogan.Project1.BuildConfig;
import com.mhmdlogan.Project1.R;
import com.mhmdlogan.Project1.adapter.MoviesAdapter;
import com.mhmdlogan.Project1.data.MovieList;
import com.mhmdlogan.Project1.data.MovieResponse;
import com.mhmdlogan.Project1.data.MoviesApiService;
import com.mhmdlogan.Project1.helpers.MarginDecoration;
import com.mhmdlogan.Project1.listener.MoviesRecyclerTouchListener;
import com.mhmdlogan.Project1.listener.RecyclerViewClickListener;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


// Browse Movie fragment with recycler

public class MovieFragment extends Fragment {
    public static final String BASE_URL = "http://api.themoviedb.org";
    private static final String TAG = "RecyclerViewFragment";
    protected RecyclerView mRecyclerView;
    MoviesAdapter mAdapter;
    MoviesCallback mMoviesCallback;
    Context mContext;
    boolean flag = false;
    private String previousPref = "popularity.desc";
    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
            flag = true;

        }
    };


    public MovieFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof MoviesCallback) {
            mMoviesCallback = (MoviesCallback) activity;
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInterface = connectivityManager.getActiveNetworkInfo();
        return networkInterface != null;


    }

    @Override
    public void onStart() {
        super.onStart();
        if (isConnected()) {
            SharedPreferences sortPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String sort_type = sortPref.getString(getResources().getString(R.string.pref_key), "popular");
            RestApi(sort_type);

        } else {
            Toast.makeText(getActivity(), "Oops! No Internet connection found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        mRecyclerView.setHasFixedSize(true);


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);


    }

    private void RestApi(final String sortby) {


        //Calling the retrofit RestAdapter to initalize Movie in Pojo
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASE_URL).build();
        MoviesApiService moviesApiService = restAdapter.create(MoviesApiService.class);
       restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);

        moviesApiService.getResponse(sortby, BuildConfig.MOVIE_DB_API_KEY, new Callback<MovieResponse>() {
            @Override
            public void success(MovieResponse movieResponse, Response response) {

                //initializing the adapter
                MoviesAdapter adapter = new MoviesAdapter(getActivity());

               //Clear  old adapter result
                MovieList.get(getActivity()).getResultsArrayList().clear();
                //Condtion to check if the Previously selected Type is equal to Current Selected
                if (previousPref.equals(sortby)) {
                    MovieList.get(getActivity()).getResultsArrayList().clear();
                    adapter.notifyDataSetChanged();
                    previousPref = sortby;
                }

                //Initializing the Array List in the Controller Class of results
                MovieList.get(getActivity()).setResultsArrayList(movieResponse.getResults());

                //Setting up the Layout manager
                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                } else {
                    mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                }


                mRecyclerView.addOnItemTouchListener(new MoviesRecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int postion) {
                        mMoviesCallback.getCurrentItem(postion);
                    }
                }));
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    mRecyclerView.setAdapter(adapter);
                } else {
                    if (flag = true) {
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.scheduleLayoutAnimation();
                        flag = false;
                    }
                }

            }


            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                Log.e("ErrorTag", error.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), Settings.class);
            startActivity(intent);
            return true;

        }


        return false;
    }

    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    interface MoviesCallback {
        void getCurrentItem(int position);
    }
}
