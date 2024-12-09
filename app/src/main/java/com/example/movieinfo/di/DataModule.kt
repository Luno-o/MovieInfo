package com.example.movieinfo.di

import com.movieinfo.data.KinopoiskApi
import com.movieinfo.data.repository.MainPageRepositoryImpl
import com.movieinfo.domain.repository.MainPageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

@Provides
@Singleton
fun provideMovieRepository(api: KinopoiskApi): MainPageRepository{
return MainPageRepositoryImpl(api)
}

}