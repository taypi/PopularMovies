package com.example.popularmovies.viewmodel;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.R;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.ui.Repository;

public class DetailViewModel extends ViewModel implements Repository.RequestCallbacks {
    private Repository mRepository;
    private MutableLiveData<Movie> mMovieDetails = new MutableLiveData<>();

    public DetailViewModel(Repository repository) {
        mRepository = repository;
    }

    public void loadMovieDetails(long id) {
        mRepository.requestMovieDetails(id, this);
    }


    public void toggleFavoriteStatus(Movie movie) {
        mRepository.toggleFavoriteStatus(movie);
    }

    public LiveData<Movie> getMovieDetails() {
        return mMovieDetails;
    }

    public int getFavoriteIcon(Movie movie) {
        return mRepository.isFavorite(movie) ? R.drawable.ic_favorite_fill : R.drawable.ic_favorite;
    }

    @Override
    public void onSuccess(Movie movies) {
        mMovieDetails.postValue(movies);
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        mMovieDetails.postValue(null);
        Log.d("Network Error", throwable.getMessage());
    }
}
