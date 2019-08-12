package com.example.popularmovies;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.popularmovies.adapters.MovieAdapter;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.utils.JsonUtils;
import com.example.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private TextView mErrorMessageDisplay;
    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mRecyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        configureRecyclerView();

        loadMoviesData();
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showMoviesData(ArrayList<Movie> movieData) {
        mAdapter.setMovieData(movieData);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void configureRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new MovieAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadMoviesData() {
        new FetchMoviesTask().execute();
    }

    @Override
    public void onRefresh() {
        FetchMoviesTask retrieveInfo = new FetchMoviesTask();
        retrieveInfo.execute();
    }

    class FetchMoviesTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            URL movieListRequestUrl = NetworkUtils.buildUrl("popularity.desc");

            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(movieListRequestUrl);
                return JsonUtils.parseMovieList(jsonResponse);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> response) {
            mSwipeRefreshLayout.setRefreshing(false);
            if (response == null) {
                showErrorMessage();
            } else {
                showMoviesData(response);
            }
        }
    }
}
