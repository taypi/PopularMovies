package com.example.popularmovies.ui;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.api.MovieApiService;
import com.example.popularmovies.database.MovieDatabase;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.models.MovieList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private MovieApiService mService;
    private MovieDatabase mDatabase;

    public Repository(@NonNull Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(MovieApiService.class);

        mDatabase = MovieDatabase.getInstance(context);
    }

    public void requestTopMovies(@NonNull RequestCallbacks callbacks) {
        mExecutor.submit(() -> {
            Call<MovieList> call = mService.getTopRatedMovies(BuildConfig.ApiKey);
            enqueueCall(call, callbacks);
        });
    }

    public void requestPopularMovies(@NonNull RequestCallbacks callbacks) {
        mExecutor.submit(() -> {
            Call<MovieList> call = mService.getPopularMovies(BuildConfig.ApiKey);
            enqueueCall(call, callbacks);
        });
    }

    public List<Movie> getFavoriteMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = mExecutor.submit(() -> mDatabase.movieDao().getFavoriteMovies()).get();
        } catch (ExecutionException|InterruptedException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public void toggleFavoriteStatus(@NonNull Movie movie) {
        mExecutor.submit(() -> {
            if (mDatabase.movieDao().getFavoriteById(movie.getId()) != null) {
                mDatabase.movieDao().deleteFavoriteMovie(movie);
            } else {
                mDatabase.movieDao().insertFavoriteMovie(movie);
            }
        });
    }

    private void enqueueCall(Call<MovieList> call, @NonNull RequestCallbacks callbacks) {
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.body() != null) {
                    callbacks.onSuccess(response.body().getMovieList());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                callbacks.onError(t);
            }
        });
    }

    public interface RequestCallbacks {
        void onSuccess(List<Movie> movies);

        void onError(@NonNull Throwable throwable);
    }
}
