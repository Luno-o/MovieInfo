package com.example.movieinfo

import android.app.Application
import com.example.movieinfo.data.db.Database
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App @Inject constructor(): Application(){
    override fun onCreate() {
        AndroidThreeTen.init(this)
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Database.init(this)
    }
}