package com.example.popularmovies.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class MovieDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "favorite";
    private static MovieDatabase sInstance;

    synchronized public static MovieDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDatabase.class, DATABASE_NAME)
                    .build();
        }
        return sInstance;
    }
}
