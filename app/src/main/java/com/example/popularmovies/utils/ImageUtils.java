package com.example.popularmovies.utils;

import android.widget.ImageView;

import com.example.popularmovies.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageUtils {
    public static void setImage(ImageView image, String path) {
        Picasso.get()
                .load(NetworkUtils.IMG_PATH + path)
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
