package com.example.popularmovies.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.models.MovieList;
import com.example.popularmovies.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestHelper {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private MovieApiService mService;

    public RequestHelper() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(MovieApiService.class);
    }

    public void requestTopMovies(@NonNull RequestCallbacks callbacks) {
        Call<MovieList> call = mService.getTopRatedMovies(BuildConfig.ApiKey);
        enqueueCall(call, callbacks);
    }

    public void requestPopularMovies(@NonNull RequestCallbacks callbacks) {
        Call<MovieList> call = mService.getPopularMovies(BuildConfig.ApiKey);
        enqueueCall(call, callbacks);
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
