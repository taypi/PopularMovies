package com.example.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private final static String NOT_AVAILABLE = "Not available";
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

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mId = in.readInt();
        mBackdropPath = in.readString();
        mAverageVote = in.readString();
        mOriginalLanguage = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return getFormatted(mTitle);
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPosterPath() {
        return getFormatted(mPosterPath);
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getOverview() {
        return getFormatted(mOverview);
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getReleaseDate() {
        return getFormatted(mReleaseDate);
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
        return getFormatted(mAverageVote);
    }

    public void setAverageVote(String mAverageVote) {
        this.mAverageVote = mAverageVote;
    }

    public String getOriginalLanguage() {
        return getFormatted(mOriginalLanguage);
    }

    public void setOriginalLanguage(String mOriginalLanguage) {
        this.mOriginalLanguage = mOriginalLanguage;
    }

    private String getFormatted(String string) {
        return string == null ? NOT_AVAILABLE : string;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeString(mReleaseDate);
        dest.writeInt(mId);
        dest.writeString(mBackdropPath);
        dest.writeString(mAverageVote);
        dest.writeString(mOriginalLanguage);
    }
}
