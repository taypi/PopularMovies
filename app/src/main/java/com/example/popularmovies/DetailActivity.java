package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.database.MovieDatabase;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.utils.ImageUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private MovieDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        database = MovieDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE)
                && intent.getParcelableExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE) != null) {
            setUi(intent.getParcelableExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE));
        } else {
            String errorMessage = getResources().getString(R.string.detail_error);
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    private void setUi(Movie movie) {
        TextView movieTitle = findViewById(R.id.tv_detail_title);
        TextView releaseDate = findViewById(R.id.tv_detail_release);
        TextView language = findViewById(R.id.tv_detail_language);
        TextView overview = findViewById(R.id.tv_detail_overview);
        TextView score = findViewById(R.id.tv_detail_vote_average);
        ImageView poster = findViewById(R.id.iv_detail_poster);
        Button favoriteButton = findViewById(R.id.btn_favorite);

        movieTitle.setText(movie.getTitle());
        releaseDate.setText(getResources().getString(R.string.release_date, movie.getReleaseDate()));
        score.setText(getResources().getString(R.string.user_score, movie.getAverageVote()));
        language.setText(getResources().getString(R.string.original_language, movie.getOriginalLanguage()));
        overview.setText(movie.getOverview());
        ImageUtils.setImage(poster, movie.getPosterPath());

        favoriteButton.setOnClickListener(listener -> toggleFavorite(movie));
    }

    private void toggleFavorite(Movie movie) {
        if (database.movieDao().getFavoriteById(movie.getId()) != null) {
            database.movieDao().deleteFavoriteMovie(movie);
        } else {
            database.movieDao().insertFavoriteMovie(movie);
        }
    }
}
