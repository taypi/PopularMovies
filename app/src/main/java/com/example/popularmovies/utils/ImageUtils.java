package com.example.popularmovies.utils;

import android.widget.ImageView;

import com.example.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageUtils {
    private final static String IMG_PATH = "https://image.tmdb.org/t/p/w342/";

    public static void setImage(ImageView image, String path) {
        Picasso.get()
                .load(IMG_PATH + path)
                .placeholder(R.mipmap.ic_launcher)
                .into(image, new Callback() {
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
