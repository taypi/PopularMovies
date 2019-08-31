package com.example.popularmovies.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovies.api.RequestHelper;
import com.example.popularmovies.database.MovieDatabase;
import com.example.popularmovies.models.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainViewModel extends AndroidViewModel implements RequestHelper.RequestCallbacks {
    private final MovieDatabase mDatabase = MovieDatabase.getInstance(getApplication());
    private SortType mCurrentSortType = SortType.POPULAR;
    private MutableLiveData<List<Movie>> mMovies = new MutableLiveData<>();
    private RequestHelper mRequestHelper = new RequestHelper();
    private final Map<SortType, Consumer<Boolean>> functionMap = createMap();

    public MainViewModel(@NonNull Application application) {
        super(application);
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
        Objects.requireNonNull(functionMap.get(mCurrentSortType)).accept(true);
    }

    private Map<SortType, Consumer<Boolean>> createMap() {
        Map<SortType, Consumer<Boolean>> myMap = new HashMap<>();
        myMap.put(SortType.POPULAR, notUsed -> mRequestHelper.requestPopularMovies(this));
        myMap.put(SortType.TOP, notUsed -> mRequestHelper.requestTopMovies(this));
        myMap.put(SortType.FAVORITE,
                notUsed -> mMovies.postValue(mDatabase.movieDao().getFavoriteMovies()));
        return myMap;
    }

    public enum SortType {
        POPULAR,
        TOP,
        FAVORITE
    }
}