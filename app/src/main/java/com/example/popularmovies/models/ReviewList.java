package com.example.popularmovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReviewList {
    @SerializedName("results")
    private List<Review> reviews;

    public ReviewList() {
        reviews = new ArrayList<>();
    }

    public List<Review> getReviewList() {
        return reviews;
    }
}
