package com.example.movieinfo.di


import com.movieinfo.domain.usecase.MainPageUseCase
import com.movieinfo.domain.repository.MainPageRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

@Provides
    fun provideMainPageUseCase(movieRepository: MainPageRepository): MainPageUseCase {
        return MainPageUseCase(mainPageRepository = movieRepository)
    }



}