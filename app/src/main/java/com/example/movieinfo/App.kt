package com.example.movieinfo

import android.app.Application
import com.movieinfo.data.db.Database
import com.example.movieinfo.utils.AppContainer
import com.example.movieinfo.utils.AppContainerImpl
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App @Inject constructor(): Application(){
lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        Timber.plant(Timber.DebugTree())
        Database.init(this)
        container = AppContainerImpl(context = this)
    }
}