package com.example.popularmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.ui.adapters.ReviewAdapter;
import com.example.popularmovies.ui.adapters.TrailerAdapter;
import com.example.popularmovies.utils.ApiUtils;
import com.example.popularmovies.utils.ImageUtils;
import com.example.popularmovies.viewmodel.DetailViewModel;
import com.example.popularmovies.viewmodel.DetailViewModelFactory;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        mDetailViewModel.getMovieDetails().observe(this, details -> {
            mReviewAdapter.setReviewData(details.getReviews().getReviewList());
            mTrailerAdapter.setTrailerData(details.getTrailers().geTrailerList());
//            TextView runtime = findViewById(R.id.tv_detail_runtime);
//            runtime.setText(details.getRuntime());
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
                    .setChooserTitle("Share movie")
                    .setText(getString(R.string.share_message, ApiUtils.SHARE_URL,
                            mMovie.getId()))
                    .startChooser();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void setToolbarText() {
        // Code snippet from https://stackoverflow/com/questions/31662416/show
        // -collapsingtoolbarlayout-title-only-when-collapsed
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

    private void setUi(Movie movie) {
        mMovie = movie;
        ImageView poster = findViewById(R.id.iv_movie_collapse);
        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        ImageUtils.setPoster(poster, movie.getBackdropPath());
        toolbar.setTitle(movie.getTitle());
        setSupportActionBar(toolbar);
        setToolbarText();
        TextView movieTitle = findViewById(R.id.tv_detail_title);
        TextView releaseDate = findViewById(R.id.tv_detail_release);
//        TextView language = findViewById(R.id.tv_detail_language);
//        TextView overview = findViewById(R.id.tv_detail_overview);
//        TextView score = findViewById(R.id.tv_detail_vote_average);
        fab.setImageResource(mDetailViewModel.getFavoriteIcon(mMovie));
        fab.setOnClickListener(view -> {
            mDetailViewModel.toggleFavoriteStatus(mMovie);
            fab.setImageResource(mDetailViewModel.getFavoriteIcon(mMovie));
        });
        movieTitle.setText(movie.getTitle());
        releaseDate.setText(
                getResources().getString(R.string.release_date, movie.getReleaseDate()));

//        score.setText(getResources().getString(R.string.user_score, movie.getAverageVote()));
//        language.setText(
//                getResources().getString(R.string.original_language, movie.getOriginalLanguage
//                ()));
//        overview.setText(movie.getOverview());
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
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    ApiUtils.getTrailerUri(trailer.getKey()));
            if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mTrailerAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
