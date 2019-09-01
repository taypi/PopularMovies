package com.example.popularmovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TrailerList {
    @SerializedName("results")
    private List<Trailer> trailers;

    public TrailerList() {
        trailers = new ArrayList<>();
    }

    public List<Trailer> geTrailerList() {
        return trailers;
    }
}
