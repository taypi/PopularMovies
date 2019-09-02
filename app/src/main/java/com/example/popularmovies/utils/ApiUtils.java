package com.example.popularmovies.utils;

import android.net.Uri;

import com.example.popularmovies.BuildConfig;

public class ApiUtils {
    private static final String POSTER_URL = "https://image.tmdb.org/t/p/w780/%s";
    private static final String TRAILER_URL = "https://img.youtube.com/vi/%s/hqdefault.jpg";
    private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=%s";
    public static final String SHARE_URL = "https://www.themoviedb.org/movie/";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String DETAILS_QUERY = "reviews,videos,credits";
    public static final String API_KEY = BuildConfig.ApiKey;

    public static String getTrailerUrl(String key) {
        return String.format(TRAILER_URL, key);
    }

    public static String getPosterUrl(String key) {
        return String.format(POSTER_URL, key);
    }

    public static Uri getTrailerUri(String key) {
        return Uri.parse(String.format(YOUTUBE_URL, key));
    }
}
