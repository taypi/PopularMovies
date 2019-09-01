package com.example.popularmovies.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.models.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> mReviewData;

    public ReviewAdapter() {
    }

    public void setReviewData(List<Review> reviewData) {
        mReviewData = reviewData;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder viewHolder, int position) {
        Review review = mReviewData.get(position);
        viewHolder.mAuthorTextView.setText(review.getAuthor());
        viewHolder.mContentTextView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviewData == null ? 0 : mReviewData.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        public final TextView mAuthorTextView;
        public final TextView mContentTextView;

        public ReviewViewHolder(View view) {
            super(view);
            mAuthorTextView = view.findViewById(R.id.tv_author);
            mContentTextView = view.findViewById(R.id.tv_content);
        }
    }
}