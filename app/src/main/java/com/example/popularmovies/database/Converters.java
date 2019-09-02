package com.example.popularmovies.database;

import androidx.room.TypeConverter;

import com.example.popularmovies.models.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Converters {
    @TypeConverter
    public static List<Genre> fromString(String stringGenre) {
        List<String> stringList = Arrays.asList(stringGenre.split(","));
        return stringList.stream().map(Genre::new).collect(Collectors.toList());
    }

    @TypeConverter
    public static String GenreListToString(List<Genre> genreList) {
        return genreList.stream().map(Genre::getName).collect(Collectors.joining(","));
    }

}