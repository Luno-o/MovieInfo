package com.example.movieinfo.utils

import android.content.Context
import com.movieinfo.data.KinopoiskApi
import com.movieinfo.data.Network
import com.movieinfo.domain.usecase.GetMainPageUseCase
import com.movieinfo.data.repository.MainMovieRepositoryImpl
import com.movieinfo.domain.repository.MainMovieRepository
import com.movieinfo.domain.usecase.ActorUseCase
import com.movieinfo.domain.usecase.SearchPageUseCase
import com.movieinfo.domain.usecase.ShowCollectionUseCase

interface AppContainer {
    val context: Context
}

class AppContainerImpl(
                       override val context: Context
) : AppContainer
