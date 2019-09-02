package com.example.popularmovies.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.ui.Repository;
import com.example.popularmovies.models.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainViewModel extends ViewModel implements Repository.ListRequestCallbacks {
    private SortType mCurrentSortType = SortType.POPULAR;
    private MutableLiveData<List<Movie>> mMovies = new MutableLiveData<>();
    private Observer<List<Movie>> mFavoriteObserver = this::onFavoriteChanged;
    private Repository mRepository;
    private final Map<SortType, Consumer<Boolean>> functionMap = createMap();

    public MainViewModel(Repository repository) {
        mRepository = repository;
        mRepository.getFavoriteList().observeForever(mFavoriteObserver);
        loadMovies();
    }

    @Override
    public void onCleared() {
        mRepository.getFavoriteList().removeObserver(mFavoriteObserver);
        super.onCleared();
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

    public enum SortType {
        POPULAR,
        TOP,
        FAVORITE
    }

    private void onFavoriteChanged(List<Movie> favoriteList) {
        if (mCurrentSortType == SortType.FAVORITE) {
            mMovies.postValue(favoriteList);
        }
    }

    private Map<SortType, Consumer<Boolean>> createMap() {
        Map<SortType, Consumer<Boolean>> map = new HashMap<>();
        map.put(SortType.POPULAR, notUsed -> mRepository.requestPopularMovies(this));
        map.put(SortType.TOP, notUsed -> mRepository.requestTopMovies(this));
        map.put(SortType.FAVORITE,
                notUsed -> mMovies.postValue(mRepository.getFavoriteMovies()));
        return map;
    }
}