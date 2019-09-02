package com.example.popularmovies.utils;

import android.widget.ImageView;

import com.example.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageUtils {
    public static void setPoster(ImageView imageView, String path) {
        setImage(imageView, ApiUtils.getPosterUrl(path));
    }

    public static void setTrailer(ImageView imageView, String key) {
        setImage(imageView, ApiUtils.getTrailerUrl(key));
    }

    private static void setImage(ImageView imageView, String path) {
        Picasso.get()
                .load(path)
                .placeholder(R.drawable.ic_local_movies_black_24dp)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
