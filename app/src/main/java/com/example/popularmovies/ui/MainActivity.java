package com.example.popularmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.popularmovies.R;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.repository.Repository;
import com.example.popularmovies.ui.adapters.MovieAdapter;
import com.example.popularmovies.viewmodel.MainViewModel;
import com.example.popularmovies.viewmodel.MainViewModelFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mErrorMessageDisplay;
    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(R.string.menu_popular);

        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mRecyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);

        configureRecyclerView();

        MainViewModelFactory factory = new MainViewModelFactory(Repository.getInstance(this));
        mMainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        mMainViewModel.getMovies().observe(this, this::onMoviesChanged);

        mSwipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.smoothScrollToPosition(0);
        switch (item.getItemId()) {
            case R.id.action_sort_popular:
                mMainViewModel.setCurrentSortType(MainViewModel.SortType.POPULAR);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(
                            R.string.menu_popular);
                }
                return true;
            case R.id.action_sort_top_rated:
                mMainViewModel.setCurrentSortType(MainViewModel.SortType.TOP);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(
                            R.string.menu_top_rated);
                }
                return true;
            case R.id.action_sort_favorite:
                mMainViewModel.setCurrentSortType(MainViewModel.SortType.FAVORITE);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(
                            R.string.menu_favorite);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onMoviesChanged(List<Movie> movies) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (!isConnected()
                && mMainViewModel.getCurrentSortType() != MainViewModel.SortType.FAVORITE) {
            showErrorMessage(R.string.network_error);
        } else if (movies.isEmpty()) {
            showErrorMessage(R.string.no_favorites);
        } else {
            showMoviesData(movies);
        }
    }

    private void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mMainViewModel.loadMovies();
    }

    private void showErrorMessage(int error_message) {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setText(error_message);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showMoviesData(List<Movie> movieData) {
        mAdapter.setMovieData(movieData);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void configureRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, getSpanCount()));
        mAdapter = new MovieAdapter(movie -> {
            Intent intentToStartDetailActivity = new Intent(this, DetailActivity.class);
            intentToStartDetailActivity.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, movie);
            startActivity(intentToStartDetailActivity);
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private int getSpanCount() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int spanCount = size.x / getResources().getInteger(R.integer.screen_size_ratio);
        return spanCount > 0 ? spanCount : 1;
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
