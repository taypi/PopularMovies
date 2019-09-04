package com.example.popularmovies.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.models.Movie;
import com.example.popularmovies.repository.Repository;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Repository mRepository;
    private final Movie mMovie;

    public DetailViewModelFactory(Repository repository, Movie movie) {
        mRepository = repository;
        mMovie = movie;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(mRepository, mMovie);
    }
}
