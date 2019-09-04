package com.example.popularmovies.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.models.Movie;
import com.example.popularmovies.repository.Repository;

public class DetailViewModel extends ViewModel implements Repository.RequestCallbacks {
    private Repository mRepository;
    private MutableLiveData<Movie> mMovieDetails = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsFavorite = new MutableLiveData<>();

    public DetailViewModel(Repository repository, Movie movie) {
        mRepository = repository;
        mIsFavorite.postValue(mRepository.isFavorite(movie));
    }

    public void loadMovieDetails(long id) {
        mRepository.requestMovieDetails(id, this);
    }

    public LiveData<Boolean> getFavoriteStatus() {
        return mIsFavorite;
    }

    public void switchFavoriteStatus(Movie movie) {
        boolean isFavorite = mIsFavorite.getValue() == null ? false : mIsFavorite.getValue();
        mIsFavorite.postValue(!isFavorite);
        mRepository.switchFavoriteStatus(movie);
    }

    public LiveData<Movie> getMovieDetails() {
        return mMovieDetails;
    }

    @Override
    public void onSuccess(Movie movies) {
        mMovieDetails.postValue(movies);
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        mMovieDetails.postValue(null);
        throwable.printStackTrace();
    }
}
