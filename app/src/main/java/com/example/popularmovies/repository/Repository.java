package com.example.popularmovies.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovies.api.MovieApiService;
import com.example.popularmovies.database.MovieDatabase;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.models.MovieList;
import com.example.popularmovies.utils.ApiUtils;

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
    private static Repository sInstance;
    private MutableLiveData<List<Movie>> favoriteList = new MutableLiveData<>();
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private MovieApiService mService;
    private MovieDatabase mDatabase;

    private Repository(@NonNull Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(MovieApiService.class);
        mDatabase = MovieDatabase.getInstance(context);
    }

    synchronized public static Repository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Repository(context);
        }
        return sInstance;
    }

    public MutableLiveData<List<Movie>> getFavoriteList() {
        return favoriteList;
    }

    public void requestTopMovies(@NonNull ListRequestCallbacks callbacks) {
        mExecutor.submit(() -> {
            Call<MovieList> call = mService.getTopRatedMovies(ApiUtils.API_KEY);
            enqueueListCall(call, callbacks);
        });
    }

    public void requestPopularMovies(@NonNull ListRequestCallbacks callbacks) {
        mExecutor.submit(() -> {
            Call<MovieList> call = mService.getPopularMovies(ApiUtils.API_KEY);
            enqueueListCall(call, callbacks);
        });
    }

    public void requestFavoriteMovies() {
        mExecutor.submit(() -> favoriteList.postValue(mDatabase.movieDao().getFavoriteMovies()));
    }

    public void requestMovieDetails(long id, @NonNull RequestCallbacks callbacks) {
        mExecutor.submit(() -> {
            Call<Movie> call = mService.getMovieDetails(id, ApiUtils.API_KEY,
                    ApiUtils.DETAILS_QUERY);
            enqueueCall(call, callbacks);
        });
    }

    public void switchFavoriteStatus(@NonNull Movie movie) {
        mExecutor.submit(() -> {
            if (mDatabase.movieDao().findById(movie.getId()) != null) {
                mDatabase.movieDao().deleteFavoriteMovie(movie);
            } else {
                mDatabase.movieDao().insertFavoriteMovie(movie);
            }
            favoriteList.postValue(mDatabase.movieDao().getFavoriteMovies());
        });
    }

    public boolean isFavorite(@NonNull Movie movie) {
        Movie favoriteMovie = null;
        try {
            favoriteMovie = mExecutor.submit(
                    () -> mDatabase.movieDao().findById(movie.getId())).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return favoriteMovie != null;
    }

    private void enqueueListCall(Call<MovieList> call, @NonNull ListRequestCallbacks callbacks) {
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

    private void enqueueCall(Call<Movie> call, @NonNull RequestCallbacks callbacks) {
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.body() != null) {
                    callbacks.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                callbacks.onError(t);
            }
        });
    }

    public interface ListRequestCallbacks {
        void onSuccess(List<Movie> movies);

        void onError(@NonNull Throwable throwable);
    }

    public interface RequestCallbacks {
        void onSuccess(Movie movies);

        void onError(@NonNull Throwable throwable);
    }
}