package com.example.movieinfo.data.db

import android.content.Context
import androidx.room.Room

object Database {
    lateinit var instance: MovieDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(context,
            MovieDatabase::class.java, MovieDatabase.DB_NAME)
            .build()
    }
}