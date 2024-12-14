package com.movieinfo.data.repository.storage

import com.movieinfo.data.repository.storage.models.MyCollections
import com.movieinfo.data.repository.storage.models.MyMovieDb
import kotlinx.coroutines.flow.Flow

interface MovieStorage {
    suspend fun getMyCollections(): List<MyCollections>
    suspend fun getCollectionById(collectionId: Int): List<MyMovieDb>
    suspend fun getMovieCollectionId(kpId: Int): List<Int>?
    suspend fun addCollection(name: String)
    suspend fun getMovieFromDB(kpId: Int): MyMovieDb?
    suspend fun addMovie(movieCollectionDB: MyMovieDb)
    suspend fun getCollectionByNameFlow(collectionId: Int): Flow<List<MyMovieDb>>
    suspend fun getAllMyMovies(): List<MyMovieDb>
    suspend fun removeMovie(movie: MyMovieDb)
    suspend fun updateMovie(movie: MyMovieDb)
}