package com.movieinfo.data.repository.storage

import com.movieinfo.data.db.MyMovieCollectionsDb
import com.movieinfo.data.repository.storage.models.MyCollections
import com.movieinfo.data.repository.storage.models.MyMovieDb
import kotlinx.coroutines.flow.Flow

interface MovieStorage {
     fun getMyCollections(): List<MyCollections>
     fun removeMyCollections(myMovieCollectionsDb: MyMovieCollectionsDb)
     fun getCollectionById(collectionId: Int): List<MyMovieDb>
     fun getMovieCollectionId(kpId: Int): List<Int>?
     fun addCollection(name: String)
     fun getMovieFromDB(kpId: Int): MyMovieDb?
     fun addMovie(movieCollectionDB: MyMovieDb)
     fun getCollectionByNameFlow(collectionId: Int): Flow<List<MyMovieDb>>
     fun getAllMyMovies(): List<MyMovieDb>
     fun removeMovie(movie: MyMovieDb)
     fun updateMovie(movie: MyMovieDb)
}