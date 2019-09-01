package com.example.popularmovies.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.ui.adapters.ReviewAdapter;
import com.example.popularmovies.ui.adapters.TrailerAdapter;
import com.example.popularmovies.utils.ImageUtils;
import com.example.popularmovies.viewmodel.DetailViewModel;
import com.example.popularmovies.viewmodel.DetailViewModelFactory;

public class DetailActivity extends AppCompatActivity {
    private DetailViewModel mDetailViewModel;
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        configureReviewRecyclerView();
        configureTrailerRecyclerView();

        DetailViewModelFactory factory = new DetailViewModelFactory(Repository.getInstance(this));
        mDetailViewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        mDetailViewModel.getMovieDetails().observe(this, details -> {
            mReviewAdapter.setReviewData(details.getReviews().getReviewList());
            mTrailerAdapter.setTrailerData(details.getTrailers().geTrailerList());
        });

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
        releaseDate.setText(
                getResources().getString(R.string.release_date, movie.getReleaseDate()));
        score.setText(getResources().getString(R.string.user_score, movie.getAverageVote()));
        language.setText(
                getResources().getString(R.string.original_language, movie.getOriginalLanguage()));
        overview.setText(movie.getOverview());
        ImageUtils.setImage(poster, movie.getPosterPath());

        favoriteButton.setOnClickListener(listener -> mDetailViewModel.toggleFavoriteStatus(movie));

        mDetailViewModel.loadMovieDetails(movie.getId());
    }

    private void configureReviewRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.review_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReviewAdapter = new ReviewAdapter();
        recyclerView.setAdapter(mReviewAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void configureTrailerRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.trailer_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mTrailerAdapter = new TrailerAdapter(trailer -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailer.getKey()));
            if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mTrailerAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
