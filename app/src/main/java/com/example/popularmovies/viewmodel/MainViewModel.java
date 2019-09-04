package com.example.popularmovies.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.repository.Repository;
import com.example.popularmovies.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel implements Repository.ListRequestCallbacks {
    private SortType mCurrentSortType = SortType.POPULAR;
    private MutableLiveData<List<Movie>> mMovies = new MutableLiveData<>();
    private Observer<List<Movie>> mFavoriteObserver = this::onFavoriteChanged;
    private Repository mRepository;

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

    public SortType getCurrentSortType() {
        return mCurrentSortType;
    }

    public void loadMovies() {
        switch (mCurrentSortType) {
            case FAVORITE:
                mMovies.postValue(mRepository.getFavoriteMovies());
                break;
            case TOP:
                mRepository.requestTopMovies(this);
                break;
            default:
                mRepository.requestPopularMovies(this);
        }
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
}