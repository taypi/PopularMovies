package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.models.Movie;
import com.example.popularmovies.utils.ImageUtils;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        movieTitle.setText(movie.getTitle());
        releaseDate.setText(getResources().getString(R.string.release_date, movie.getReleaseDate()));
        score.setText(getResources().getString(R.string.user_score, movie.getAverageVote()));
        language.setText(getResources().getString(R.string.original_language, movie.getOriginalLanguage()));
        overview.setText(movie.getOverview());
        ImageUtils.setImage(poster, movie.getPosterPath());
    }
}
