package com.example.popularmovies.models;

public class Movie {
    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private String mReleaseDate;
    private int mId;
    private String mBackdropPath;
    private String mAverageVote;
    private String mOriginalLanguage;

    public Movie() {
    }

    public Movie(String title, String posterPath, String overview, String releaseDate, int id,
                 String backdropPath, String averageVote, String originalLanguage) {
        this.mTitle = title;
        this.mPosterPath = posterPath;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mId = id;
        this.mBackdropPath = backdropPath;
        this.mAverageVote = averageVote;
        this.mOriginalLanguage = originalLanguage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String mBackdropPath) {
        this.mBackdropPath = mBackdropPath;
    }

    public String getAverageVote() {
        return mAverageVote;
    }

    public void setAverageVote(String mAverageVote) {
        this.mAverageVote = mAverageVote;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String mOriginalLanguage) {
        this.mOriginalLanguage = mOriginalLanguage;
    }

}
