package com.example.popularmovies.utils;

import android.widget.ImageView;

import com.example.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageUtils {
    private final static String IMG_PATH = "https://image.tmdb.org/t/p/w342/";
//    private final static String TRAILER_IMG_PATH = "https://img.youtube.com/vi/" + trailer.getKey() + "/hqdefault.jpg";

    public static void setImage(ImageView imageView, String path) {
        Picasso.get()
                .load(IMG_PATH + path)
                .placeholder(R.mipmap.ic_launcher)
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

    public static void setTrailerImage(ImageView imageView, String key) {
        Picasso.get()
                .load("https://img.youtube.com/vi/" + key + "/hqdefault.jpg")
                .placeholder(R.mipmap.ic_launcher)
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
