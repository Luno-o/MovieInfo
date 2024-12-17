package com.example.movieinfo.di


import com.movieinfo.domain.usecase.GetCollectionUseCase
import com.movieinfo.domain.repository.MainMovieRepository
import com.movieinfo.domain.usecase.ActorUseCase
import com.movieinfo.domain.usecase.AddCollectionUseCase
import com.movieinfo.domain.usecase.AddToMyCollectionUseCase
import com.movieinfo.domain.usecase.DeleteHistoryUseCase
import com.movieinfo.domain.usecase.GetCollectionByNameUseCase
import com.movieinfo.domain.usecase.GetFilmUseCase
import com.movieinfo.domain.usecase.GetMovieCollectionsIdUseCase
import com.movieinfo.domain.usecase.GetMovieFromDbUseCase
import com.movieinfo.domain.usecase.GetMovieGalleryUseCase
import com.movieinfo.domain.usecase.GetMyCollectionsUseCase
import com.movieinfo.domain.usecase.GetSeasonsUseCase
import com.movieinfo.domain.usecase.GetSimilarCollectionFlowUseCase
import com.movieinfo.domain.usecase.GetStaffByFilmUseCase
import com.movieinfo.domain.usecase.SearchPageUseCase
import com.movieinfo.domain.usecase.ShowCollectionUseCase
import com.movieinfo.domain.usecase.ShowMyCollectionFlowUseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

@Provides
    fun provideMainPageUseCase(movieRepository: MainMovieRepository): GetCollectionUseCase {
        return GetCollectionUseCase(mainMovieRepository = movieRepository)
    }

    @Provides
    fun provideActorUseCase(movieRepository: MainMovieRepository): ActorUseCase {
        return ActorUseCase(mainMovieRepository = movieRepository)
    }

    @Provides
    fun provideGetFilmUseCase(movieRepository: MainMovieRepository): GetFilmUseCase {
        return GetFilmUseCase(mainMovieRepository = movieRepository)
    }

    @Provides
    fun provideGetSeasonsUseCase(movieRepository: MainMovieRepository): GetSeasonsUseCase {
        return GetSeasonsUseCase(mainMovieRepository = movieRepository)
    }

    @Provides
    fun provideGetMovieCollectionsIdUseCase(movieRepository: MainMovieRepository): GetMovieCollectionsIdUseCase {
        return GetMovieCollectionsIdUseCase(mainMovieRepository = movieRepository)
    }
    @Provides
    fun provideGetStaffByFilmUseCase(movieRepository: MainMovieRepository): GetStaffByFilmUseCase {
        return GetStaffByFilmUseCase(mainMovieRepository = movieRepository)
    }
    @Provides
    fun provideGetMovieGalleryUseCase(movieRepository: MainMovieRepository): GetMovieGalleryUseCase {
        return GetMovieGalleryUseCase(mainMovieRepository = movieRepository)
    }
    @Provides
    fun provideGetMovieFromDbUseCase(movieRepository: MainMovieRepository): GetMovieFromDbUseCase {
        return GetMovieFromDbUseCase(mainMovieRepository = movieRepository)
    }
    @Provides
    fun provideAddToMyCollectionUseCase(movieRepository: MainMovieRepository): AddToMyCollectionUseCase {
        return AddToMyCollectionUseCase(mainMovieRepository = movieRepository)
    }
    @Provides
    fun provideGetMyCollectionsUseCase(movieRepository: MainMovieRepository): GetMyCollectionsUseCase {
        return GetMyCollectionsUseCase(mainMovieRepository = movieRepository)
    }
    @Provides
    fun provideAddCollectionUseCase(movieRepository: MainMovieRepository): AddCollectionUseCase {
        return AddCollectionUseCase(mainMovieRepository = movieRepository)
    }
    @Provides
    fun provideGetCollectionByNameUseCase(movieRepository: MainMovieRepository): GetCollectionByNameUseCase {
        return GetCollectionByNameUseCase(mainMovieRepository = movieRepository)
    }
    @Provides
    fun provideDeleteHistoryUseCase(movieRepository: MainMovieRepository): DeleteHistoryUseCase {
        return DeleteHistoryUseCase(mainMovieRepository = movieRepository)
    }
    @Provides
    fun provideSearchPageUseCase(movieRepository: MainMovieRepository): SearchPageUseCase {
        return SearchPageUseCase(mainMovieRepository = movieRepository)
    }
    @Provides
    fun provideShowCollectionUseCase(movieRepository: MainMovieRepository): ShowCollectionUseCase {
        return ShowCollectionUseCase(mainMovieRepository = movieRepository)
    }
    @Provides
    fun provideGetSimilarCollectionUseCase(movieRepository: MainMovieRepository): GetSimilarCollectionFlowUseCase {
        return GetSimilarCollectionFlowUseCase(mainMovieRepository = movieRepository)
    }
    @Provides
    fun provideShowMyCollectionFlowUseCase(movieRepository: MainMovieRepository): ShowMyCollectionFlowUseCase {
        return ShowMyCollectionFlowUseCase(mainMovieRepository = movieRepository)
    }




}