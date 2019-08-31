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
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("vote_average")
    private String averageVote;
    @SerializedName("original_language")
    private String originalLanguage;

    public Movie(int id, String title, String posterPath, String overview, String releaseDate,
                 String backdropPath, String averageVote, String originalLanguage) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.averageVote = averageVote;
        this.originalLanguage = originalLanguage;
    }

    @Ignore
    private Movie(Parcel in) {
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        id = in.readInt();
        backdropPath = in.readString();
        averageVote = in.readString();
        originalLanguage = in.readString();
    }

    public String getTitle() {
        return getFormatted(title);
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public String getPosterPath() {
        return getFormatted(posterPath);
    }

    public void setPosterPath(String mPosterPath) {
        this.posterPath = mPosterPath;
    }

    public String getOverview() {
        return getFormatted(overview);
    }

    public void setOverview(String mOverview) {
        this.overview = mOverview;
    }

    public String getReleaseDate() {
        return getFormatted(releaseDate);
    }

    public void setReleaseDate(String mReleaseDate) {
        this.releaseDate = mReleaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        this.id = mId;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String mBackdropPath) {
        this.backdropPath = mBackdropPath;
    }

    public String getAverageVote() {
        return getFormatted(averageVote);
    }

    public void setAverageVote(String mAverageVote) {
        this.averageVote = mAverageVote;
    }

    public String getOriginalLanguage() {
        return getFormatted(originalLanguage);
    }

    public void setOriginalLanguage(String mOriginalLanguage) {
        this.originalLanguage = mOriginalLanguage;
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
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeInt(id);
        dest.writeString(backdropPath);
        dest.writeString(averageVote);
        dest.writeString(originalLanguage);
    }
}
