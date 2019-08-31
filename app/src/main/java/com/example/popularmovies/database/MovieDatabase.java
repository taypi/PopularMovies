package com.example.popularmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.popularmovies.models.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "favorite";
    private static MovieDatabase sInstance;

    synchronized public static MovieDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDatabase.class, DATABASE_NAME)
                    // I solemnly swear that I am up to no good
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();
}
