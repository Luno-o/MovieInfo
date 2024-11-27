package com.example.movieinfo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieCollectionDB::class,
                     MyMovieCollections::class], version = MovieDatabase.DB_VERSION)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDBDao(): MovieDBDao
    abstract fun movieCollectionsNameDao(): MovieCollectionsNameDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "movie_DB"
    }
}