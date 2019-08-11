package com.example.popularmovies.utils;

import com.example.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String ID_KEY = "id";
    private static final String TITLE_KEY = "title";
    private static final String OVERVIEW_KEY = "overview";
    private static final String AVERAGE_VOTE_KEY = "vote_average";
    private static final String BACKDROP_KEY = "backdrop_path";
    private static final String LANGUAGE_KEY = "original_language";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String RELEASE_DATE_KEY = "release_date";
    private static final String RESULT_KEY = "results";

    public static ArrayList<Movie> parseMovieList(String json) {
        ArrayList<Movie> movieList = new ArrayList<>();

        try {
            JSONObject movieJson = new JSONObject(json);
            ArrayList<String> jsonList = getListFromJsonArray(movieJson, RESULT_KEY);
            jsonList.forEach((item) -> movieList.add(parseMovie(item)));
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }

        return movieList;
    }

    public static Movie parseMovie(String json) {
        Movie movie = new Movie();

        try {
            JSONObject movieJson = new JSONObject(json);

            movie.setId(movieJson.optInt(ID_KEY));
            movie.setTitle(movieJson.optString(TITLE_KEY));
            movie.setOverview(movieJson.optString(OVERVIEW_KEY));
            movie.setAverageVote(movieJson.optString(AVERAGE_VOTE_KEY));
            movie.setBackdropPath(movieJson.optString(BACKDROP_KEY));
            movie.setOriginalLanguage(movieJson.optString(LANGUAGE_KEY));
            movie.setPosterPath(movieJson.optString(POSTER_PATH_KEY));
            movie.setReleaseDate(movieJson.optString(RELEASE_DATE_KEY));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movie;
    }

    private static ArrayList<String> getListFromJsonArray(JSONObject json, String key) throws NullPointerException {
        ArrayList<String> list = new ArrayList<>();
        JSONArray array = json.optJSONArray(key);
        for (int i = 0; i < array.length(); i++) {
            list.add(array.optString(i));
        }
        return list;
    }
}
