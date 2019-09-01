package com.example.popularmovies.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.models.Trailer;
import com.example.popularmovies.utils.ImageUtils;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailer> mTrailerData;
    private TrailerAdapterOnClickHandler mClickHandler;

    public TrailerAdapter(TrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public interface TrailerAdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    public void setTrailerData(List<Trailer> trailerData) {
        mTrailerData = trailerData;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder viewHolder, int position) {
        Trailer trailer = mTrailerData.get(position);
        viewHolder.mTrailerTextView.setText(trailer.getName());
        ImageUtils.setTrailerImage(viewHolder.mTrailerImageView, trailer.getKey());
    }

    @Override
    public int getItemCount() {
        return mTrailerData == null ? 0 : mTrailerData.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTrailerTextView;
        public final ImageView mTrailerImageView;

        public TrailerViewHolder(View view) {
            super(view);
            mTrailerTextView = view.findViewById(R.id.tv_trailer);
            mTrailerImageView = view.findViewById(R.id.iv_trailer);

            view.setOnClickListener(v -> mClickHandler.onClick(mTrailerData.get(getAdapterPosition())));
        }
    }
}