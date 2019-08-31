package com.example.popularmovies.api;

import androidx.lifecycle.LiveData;

import com.example.popularmovies.models.Movie;
import com.example.popularmovies.models.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("movie/popular")
    Call<MovieList> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieList> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}?append_to_response=videos,credits,reviews")
    LiveData<Movie> getMovieDetails(@Path("id") long id, @Query("api_key") String apiKey);
}
