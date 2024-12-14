package com.example.movieinfo.di

import com.movieinfo.data.KinopoiskApi
import com.movieinfo.data.repository.MainMovieRepositoryImpl
import com.movieinfo.data.repository.storage.MovieStorage
import com.movieinfo.data.repository.storage.MovieStorageImpl
import com.movieinfo.domain.repository.MainMovieRepository
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
fun provideMovieRepository(api: KinopoiskApi,movieStorage: MovieStorage): MainMovieRepository{
return MainMovieRepositoryImpl(movieStorage,api)
}
    @Provides
    @Singleton
    fun provideMovieStorage():MovieStorage{
        return MovieStorageImpl()
    }

}