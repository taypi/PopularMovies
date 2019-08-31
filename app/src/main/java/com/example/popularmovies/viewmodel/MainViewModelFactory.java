package com.example.popularmovies.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.database.MovieDatabase;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MovieDatabase mDatabase;

    public MainViewModelFactory(MovieDatabase database) {
        mDatabase = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(mDatabase);
    }
}
