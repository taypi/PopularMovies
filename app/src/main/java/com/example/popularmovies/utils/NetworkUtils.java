package com.example.popularmovies.utils;

import android.net.Uri;

import com.example.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String MOVIE_API_URL = "https://api.themoviedb.org/3/discover/movie";
    private final static String SORT_PARAM = "sort_by";
    private final static String VOTE_COUNT_PARAM = "vote_count.gte";
    private final static String API_KEY_PARAM = "api_key";

    /* The minimum number of votes to include a movie */
    private static final String minVotes = "500";

    /**
     * Builds the URL used to talk to the movie db server.
     *
     * @param sortQuery The attribute used to sort the movies.
     * @return The URL to use to query The Movie DB server.
     */
    public static URL buildUrl(String sortQuery) {
        Uri builtUri = Uri.parse(MOVIE_API_URL).buildUpon()
                .appendQueryParameter(SORT_PARAM, sortQuery)
                .appendQueryParameter(VOTE_COUNT_PARAM, minVotes)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.ApiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
