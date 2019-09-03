package com.example.popularmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.models.Genre;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.repository.Repository;
import com.example.popularmovies.ui.adapters.ReviewAdapter;
import com.example.popularmovies.ui.adapters.TrailerAdapter;
import com.example.popularmovies.utils.ApiUtils;
import com.example.popularmovies.utils.ImageUtils;
import com.example.popularmovies.viewmodel.DetailViewModel;
import com.example.popularmovies.viewmodel.DetailViewModelFactory;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.stream.Collectors;

public class DetailActivity extends AppCompatActivity {
    private Movie mMovie;
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
        mDetailViewModel.getMovieDetails().observe(this, this::onDetailsChanged);

        handleIntentInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setChooserTitle(R.string.intent_share)
                    .setText(getString(R.string.share_message, ApiUtils.SHARE_URL,
                            mMovie.getId()))
                    .startChooser();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void handleIntentInfo() {
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE)
                && intent.getParcelableExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE) != null) {
            setUi(intent.getParcelableExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE));
        } else {
            startActivity(new Intent(this, MainActivity.class));

            String errorMessage = getResources().getString(R.string.detail_error);
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    private void onDetailsChanged(Movie movie) {
        if (movie == null) {
            return;
        }
        mTrailerAdapter.setTrailerData(movie.getTrailers().geTrailerList());
        mReviewAdapter.setReviewData(movie.getReviews().getReviewList());

        ConstraintLayout trailers = findViewById(R.id.trailers);
        trailers.setVisibility(View.VISIBLE);
        ConstraintLayout reviews = findViewById(R.id.reviews);
        reviews.setVisibility(View.VISIBLE);

        TextView genres = findViewById(R.id.tv_genres);
        genres.setText(
                movie.getGenres().stream().map(Genre::getName).collect(Collectors.joining(", ")));
    }

    private void setUi(Movie movie) {
        mMovie = movie;
        mDetailViewModel.loadMovieDetails(movie.getId());

        ImageView backdrop = findViewById(R.id.iv_movie_collapse);
        TextView movieTitle = findViewById(R.id.tv_detail_title);
        TextView score = findViewById(R.id.tv_score);
        TextView totalVotes = findViewById(R.id.tv_total_votes);
        TextView language = findViewById(R.id.tv_language);
        TextView date = findViewById(R.id.tv_date);
        TextView overview = findViewById(R.id.tv_overview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        ImageUtils.setPoster(backdrop, movie.getBackdropPath());
        movieTitle.setText(movie.getTitle());
        score.setText(movie.getAverageVote());
        totalVotes.setText(getString(R.string.total_votes, movie.getVoteCount()));

        language.setText(movie.getOriginalLanguage());
        language.setAllCaps(true);

        date.setText(movie.getReleaseDate());
        overview.setText(movie.getOverview());

        toolbar.setTitle(movie.getTitle());
        setSupportActionBar(toolbar);
        setToolbarText();

        fab.setImageResource(mDetailViewModel.getFavoriteIcon(mMovie));
        fab.setOnClickListener(view -> {
            mDetailViewModel.toggleFavoriteStatus(mMovie);
            ((FloatingActionButton) view).setImageResource(
                    mDetailViewModel.getFavoriteIcon(mMovie));
        });
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
        mTrailerAdapter = getTrailerAdapter();
        recyclerView.setAdapter(mTrailerAdapter);
        // To make the reviews layout scroll along with the rest
        recyclerView.setNestedScrollingEnabled(false);
    }

    private TrailerAdapter getTrailerAdapter() {
        return new TrailerAdapter(trailer -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    ApiUtils.getTrailerUri(trailer.getKey()));

            if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                startActivity(intent);
            }
        });
    }

    private void setToolbarText() {
        // Code snippet from
        // https://stackoverflow/com/questions/31662416/show-collapsingtoolbarlayout-title-only
        // -when-collapsed
        // The title will only show up when the toolbar is collapsed
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(
                R.id.collapsing_toolbar_layout);
        AppBarLayout appBarLayout = findViewById(R.id.bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(mMovie.getTitle());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
