package com.example.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.popularmovies.R;
import com.example.popularmovies.models.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> mMovieData;

    public MovieAdapter() {
    }

    public MovieAdapter(ArrayList<Movie> movieData) {
        mMovieData = movieData;
    }

    public void setMovieData(ArrayList<Movie> movieData) {
        this.mMovieData = movieData;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.mMovieItemTextView.setText(mMovieData.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return mMovieData == null ? 0 : mMovieData.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public final TextView mMovieItemTextView;

        public MovieViewHolder(View view) {
            super(view);
            mMovieItemTextView = view.findViewById(R.id.list_item_tv);
        }
    }

}
