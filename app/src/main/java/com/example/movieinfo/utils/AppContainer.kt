package com.example.movieinfo.utils

import android.content.Context
import com.movieinfo.data.KinopoiskApi
import com.movieinfo.data.Network
import com.movieinfo.domain.usecase.MainPageUseCase
import com.movieinfo.data.repository.MainPageRepositoryImpl
import com.movieinfo.domain.repository.MainPageRepository
import com.movieinfo.domain.usecase.ActorUseCase
import com.movieinfo.domain.usecase.FilmPageUseCase
import com.movieinfo.domain.usecase.ProfileUseCase
import com.movieinfo.domain.usecase.SearchPageUseCase
import com.movieinfo.domain.usecase.ShowCollectionUseCase

interface AppContainer {
    val mainPageUseCase: MainPageUseCase
    val showCollectionUseCase: ShowCollectionUseCase
    val actorUseCase: ActorUseCase
    val profileUseCase: ProfileUseCase
    val filmPageUseCase: FilmPageUseCase
    val searchPageUseCase: SearchPageUseCase
    val mainPageRepository: MainPageRepository
    val api: KinopoiskApi
    val context: Context
}

class AppContainerImpl(override val api: KinopoiskApi = Network.getKinopoiskApi,
                       override val context: Context
) : AppContainer {

    override val mainPageUseCase: MainPageUseCase by lazy {
        MainPageUseCase(mainPageRepository)
    }
    override val showCollectionUseCase: ShowCollectionUseCase by lazy {
        ShowCollectionUseCase(mainPageRepository)
    }
    override val actorUseCase: ActorUseCase by lazy {
        ActorUseCase(mainPageRepository = mainPageRepository)
    }
    override val profileUseCase: ProfileUseCase by lazy {
        ProfileUseCase(mainPageRepository)
    }
    override val filmPageUseCase: FilmPageUseCase by lazy {
        FilmPageUseCase(mainPageRepository = mainPageRepository)
    }
    override val searchPageUseCase: SearchPageUseCase by lazy {
        SearchPageUseCase(mainPageRepository)
    }
    override val mainPageRepository: MainPageRepository by lazy { MainPageRepositoryImpl(api) }
}
