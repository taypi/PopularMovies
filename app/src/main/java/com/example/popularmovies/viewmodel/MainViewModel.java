package com.example.popularmovies.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.api.RequestHelper;
import com.example.popularmovies.database.MovieDatabase;
import com.example.popularmovies.models.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel implements RequestHelper.RequestCallbacks {
    private final MovieDatabase mDatabase;
    ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private SortType mCurrentSortType = SortType.POPULAR;
    private MutableLiveData<List<Movie>> mMovies = new MutableLiveData<>();
    private RequestHelper mRequestHelper = new RequestHelper();
    private final Map<SortType, Consumer<Boolean>> functionMap = createMap();

    public MainViewModel(MovieDatabase database) {
        mDatabase = database;
        loadMovies();
    }

    @Override
    public void onSuccess(List<Movie> movies) {
        mMovies.postValue(movies);
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        mMovies.postValue(new ArrayList<>());
        Log.d("Network Error", throwable.getMessage());
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    public void setCurrentSortType(SortType sortType) {
        mCurrentSortType = sortType;
        loadMovies();
    }

    public void loadMovies() {
        mExecutor.submit(
                () -> Objects.requireNonNull(functionMap.get(mCurrentSortType)).accept(true));
    }

    public void toggleFavorite(Movie movie) {
        mExecutor.submit(() -> {
            if (mDatabase.movieDao().getFavoriteById(movie.getId()) != null) {
                mDatabase.movieDao().deleteFavoriteMovie(movie);
            } else {
                mDatabase.movieDao().insertFavoriteMovie(movie);
            }
        });
    }

    private Map<SortType, Consumer<Boolean>> createMap() {
        Map<SortType, Consumer<Boolean>> map = new HashMap<>();
        map.put(SortType.POPULAR, notUsed -> mRequestHelper.requestPopularMovies(this));
        map.put(SortType.TOP, notUsed -> mRequestHelper.requestTopMovies(this));
        map.put(SortType.FAVORITE,
                notUsed -> mMovies.postValue(mDatabase.movieDao().getFavoriteMovies()));
        return map;
    }

    public enum SortType {
        POPULAR,
        TOP,
        FAVORITE
    }
}