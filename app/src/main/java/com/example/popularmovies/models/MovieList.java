package com.example.popularmovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieList {
    @SerializedName("results")
    private List<Movie> movies;

    public MovieList() {
        movies = new ArrayList<>();
    }

    public List<Movie> getMovieList() {
        return movies;
    }
}
