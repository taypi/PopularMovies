package com.example.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorite_movie")
public class Movie implements Parcelable {
    private final static String NOT_AVAILABLE = "Not available";
    @SerializedName("id")
    @PrimaryKey
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("vote_average")
    private String mAverageVote;
    @SerializedName("original_language")
    private String mOriginalLanguage;

    public Movie(int id, String title, String posterPath, String overview, String releaseDate,
                 String backdropPath, String averageVote, String originalLanguage) {
        this.mId = id;
        this.mTitle = title;
        this.mPosterPath = posterPath;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mBackdropPath = backdropPath;
        this.mAverageVote = averageVote;
        this.mOriginalLanguage = originalLanguage;
    }

    @Ignore
    private Movie(Parcel in) {
        mTitle = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mId = in.readInt();
        mBackdropPath = in.readString();
        mAverageVote = in.readString();
        mOriginalLanguage = in.readString();
    }

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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Movie && ((Movie) obj).getId() == mId;
    }

    // Parcelable stuff
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
