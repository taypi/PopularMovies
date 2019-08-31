package com.example.popularmovies.models;

import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;

    public Video(String id, String name, String site) {
        this.id = id;
        this.name = name;
        this.site = site;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
