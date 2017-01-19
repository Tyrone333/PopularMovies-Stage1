package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.utilities.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieAdapterOnClickHandler {


    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    ArrayList<Movie> mMovies;
    String searchURL = "http://api.themoviedb.org/3/movie/top_rated?api_key=c8554df4e230ea7a19b1bcf1ad352dd7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 3);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(this);

        mRecyclerView.setAdapter(mMoviesAdapter);

        fireOffNetworkTask();


    }

    private void fireOffNetworkTask() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (activeNetwork != null && activeNetwork.isConnected()) {

            //Fire off the AsyncTask
            new FetchMovieAsyncTask().execute();

        } else {
            //No internet connection
            /* First, hide the currently visible data */
            mRecyclerView.setVisibility(View.INVISIBLE);
            /* Then, show the error */
            mErrorMessageDisplay.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(Movie oneMovie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("currentMovie", oneMovie);
        startActivity(intent);
    }

    private class FetchMovieAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... urls) {
            //first create the URL and then fetch the data
            mMovies = Utils.fetchMovies(searchURL);
            return mMovies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null && !movies.isEmpty()) {
                mMoviesAdapter.setMovieData(movies);
            } else {
                /* First, hide the currently visible data */
                mRecyclerView.setVisibility(View.INVISIBLE);
                /* Then, show the error */
                mErrorMessageDisplay.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.top_movies_menu_option) {
            searchURL = "http://api.themoviedb.org/3/movie/top_rated?api_key=c8554df4e230ea7a19b1bcf1ad352dd7";
            fireOffNetworkTask();
            return true;
        } else if (id == R.id.most_popular_movies_menu_option) {
            searchURL = "http://api.themoviedb.org/3/movie/popular?api_key=c8554df4e230ea7a19b1bcf1ad352dd7";
            fireOffNetworkTask();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
