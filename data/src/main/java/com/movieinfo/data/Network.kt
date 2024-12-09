package com.movieinfo.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
object Network {
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(ApiKeyInterceptor())
        .addNetworkInterceptor(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    ).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
val getKinopoiskApi: KinopoiskApi = retrofit.create(KinopoiskApi::class.java)
}

class ApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder
            .addHeader("X-API-KEY","8b875a0a-33c0-4470-9e03-5c732b0359ff")
        return chain.proceed(requestBuilder.build())
    }

}