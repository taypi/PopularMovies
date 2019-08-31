package com.example.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.utils.ImageUtils;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> mMovieData;
    private final MovieAdapterOnClickHandler mClickHandler;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public void setMovieData(List<Movie> movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, int position) {
        Movie movie = mMovieData.get(position);
        viewHolder.mMovieItemTextView.setText(movie.getTitle());
        ImageUtils.setImage(viewHolder.mPoster, movie.getPosterPath());
    }

    @Override
    public int getItemCount() {
        return mMovieData == null ? 0 : mMovieData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public final TextView mMovieItemTextView;
        public final ImageView mPoster;

        public MovieViewHolder(View view) {
            super(view);
            mMovieItemTextView = view.findViewById(R.id.tv_list_item);
            mPoster = view.findViewById(R.id.iv_list_item);

            view.setOnClickListener(v -> mClickHandler.onClick(mMovieData.get(getAdapterPosition())));
        }
    }

}
