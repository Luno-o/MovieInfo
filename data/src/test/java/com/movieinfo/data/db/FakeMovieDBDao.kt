package com.movieinfo.data.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

class FakeMovieDBDao : MovieDBDao {
    private val list = mutableListOf<MovieCollectionDB>()
    override fun insertMoviesDB(movies: List<MovieCollectionDB>) {
        list.addAll(movies)
    }

    override fun insertMovieDB(movie: MovieCollectionDB) {
      list.add(movie)
    }

    override fun getAllMoviesDB(): List<MovieCollectionDB> {
        return list
    }

    override fun getMovieDB(kpId: Int): MovieCollectionDB? {
        return list.find { it.kpID == kpId }
    }

    override fun deleteHistory(collectionId: Int) {
        val deleteList = mutableListOf<MovieCollectionDB>()
        list.forEach { movie ->
            movie.collectionId.find { id -> id == collectionId }?.let {
                deleteList.add(
                    movie
                )
            }
        }
        list.removeAll(deleteList)
    }

    override fun observeMovies(): Flow<List<MovieCollectionDB>> {
       return flowOf(list)
    }

    override fun removeMovie(movieDB: MovieCollectionDB) {
        list.remove(movieDB)
    }

    override fun updateMovieDB(movieDB: MovieCollectionDB) {

    }
}