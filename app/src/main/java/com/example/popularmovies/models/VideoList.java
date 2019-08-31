package com.example.popularmovies.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VideoList {
    @SerializedName("results")
    private List<Video> videos;

    public VideoList() {
        videos = new ArrayList<>();
    }

    public List<Video> getVideosList() {
        return videos;
    }
}
