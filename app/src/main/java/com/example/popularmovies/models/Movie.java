package com.example.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("vote_count")
    private String voteCount;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("runtime")
    private String runtime;
    @Ignore
    @SerializedName("genres")
    private List<Genre> genres;
    @Ignore
    @SerializedName("videos")
    private TrailerList trailers;
    @Ignore
    @SerializedName("reviews")
    private ReviewList reviews;

    public Movie(int id, String title, String posterPath, String overview, String releaseDate,
            String backdropPath, String averageVote, String voteCount, String originalLanguage,
            String runtime) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.averageVote = averageVote;
        this.voteCount = voteCount;
        this.originalLanguage = originalLanguage;
        this.runtime = runtime;
    }

    @Ignore
    public Movie(int id, String title, String posterPath, String overview, String releaseDate,
            String backdropPath, String averageVote, String voteCount, String originalLanguage,
            String runtime, TrailerList trailerList, ReviewList reviewList) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.averageVote = averageVote;
        this.voteCount = voteCount;
        this.originalLanguage = originalLanguage;
        this.runtime = runtime;
        this.trailers = trailerList;
        this.reviews = reviewList;
    }

    @Ignore
    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        backdropPath = in.readString();
        averageVote = in.readString();
        voteCount = in.readString();
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

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public TrailerList getTrailers() {
        return trailers;
    }

    public void setTrailers(TrailerList trailers) {
        this.trailers = trailers;
    }

    public ReviewList getReviews() {
        return reviews;
    }

    public void setReviews(ReviewList reviews) {
        this.reviews = reviews;
    }

    private String getFormatted(String string) {
        return string == null ? NOT_AVAILABLE : string;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(backdropPath);
        dest.writeString(averageVote);
        dest.writeString(voteCount);
        dest.writeString(originalLanguage);
    }
}
