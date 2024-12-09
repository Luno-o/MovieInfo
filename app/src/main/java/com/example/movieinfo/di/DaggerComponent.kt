package com.example.movieinfo.di

import com.movieinfo.data.ApiKeyInterceptor
import com.movieinfo.data.KinopoiskApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
    @Provides
    @Singleton
    fun provideOkHttpClient():OkHttpClient{
return OkHttpClient.Builder()
    .addNetworkInterceptor(ApiKeyInterceptor())
    .addNetworkInterceptor(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    ).build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun provideKinopoiskApi(retrofit: Retrofit): KinopoiskApi {
        return  retrofit.create(KinopoiskApi::class.java)
    }
}