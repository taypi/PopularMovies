package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.popularmovies.adapters.MovieAdapter;
import com.example.popularmovies.api.MovieApiService;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.models.MovieList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TOP_RATED_SORT_KEY = "vote_average.desc";
    private static final String POPULARITY_SORT_KEY = "popularity.desc";
    private static final String SORT_TYPE_KEY = "sort_type";
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

        mSwipeRefreshLayout.setOnRefreshListener(this::loadMoviesData);

        configureRecyclerView();

        loadMoviesData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                setSortType(POPULARITY_SORT_KEY);
                loadMoviesData();
                return true;
            case R.id.action_top_rated:
                setSortType(TOP_RATED_SORT_KEY);
                loadMoviesData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
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

    private String getSortType() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(SORT_TYPE_KEY, POPULARITY_SORT_KEY);
    }

    private void setSortType(String sortType) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SORT_TYPE_KEY, sortType);
        editor.apply();
    }

    private void loadMoviesData() {
        mSwipeRefreshLayout.setRefreshing(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApiService service = retrofit.create(MovieApiService.class);
        Call<MovieList> call = getSortType().equals(TOP_RATED_SORT_KEY) ?
                service.getTopRatedMovies(BuildConfig.ApiKey) :
                service.getPopularMovies(BuildConfig.ApiKey);
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.body() != null) {
                    showMoviesData(response.body().getMovieList());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.e("ERROR", "NET_ERROR:" + t.toString());
                mSwipeRefreshLayout.setRefreshing(false);
                showErrorMessage();
            }
        });
    }
}
