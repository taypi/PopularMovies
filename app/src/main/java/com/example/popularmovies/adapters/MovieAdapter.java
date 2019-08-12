package com.example.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.models.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> mMovieData;

    public MovieAdapter() {
    }

    public void setMovieData(ArrayList<Movie> movieData) {
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
        Picasso.get()
                .load("https://image.tmdb.org/t/p/" + "/w342/" + movie.getPosterPath())
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.mPoster, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mMovieData == null ? 0 : mMovieData.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public final TextView mMovieItemTextView;
        public final ImageView mPoster;

        public MovieViewHolder(View view) {
            super(view);
            mMovieItemTextView = view.findViewById(R.id.tv_list_item);
            mPoster = view.findViewById(R.id.iv_list_item);
        }
    }

}
