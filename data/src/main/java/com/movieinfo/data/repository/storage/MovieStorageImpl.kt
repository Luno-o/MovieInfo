package com.movieinfo.data.repository.storage
import com.movieinfo.data.db.Database
import com.movieinfo.data.db.MyMovieCollectionsDb
import com.movieinfo.data.extensions.toMovieCollectionDb
import com.movieinfo.data.repository.storage.models.MyCollections
import com.movieinfo.data.repository.storage.models.MyMovieDb
import kotlinx.coroutines.flow.Flow


class MovieStorageImpl: MovieStorage {
    private val movieDao = Database.instance.movieDBDao()
    private val movieCollectionsNameDao = Database.instance.movieCollectionsNameDao()


    override suspend fun getMyCollections(): List<MyCollections> {
        return movieCollectionsNameDao.getAllCollectionNames()
    }

    override suspend fun getCollectionById(collectionId: Int): List<MyMovieDb> {
        return movieCollectionsNameDao.getMoviesByCollectionId(collectionId.toString())
    }

    override suspend fun getMovieCollectionId(kpId: Int): List<Int>? {
        return movieDao.getMovieDB(kpId)?.collectionId
    }

    override suspend fun addCollection(name: String) {
        movieCollectionsNameDao.insertCollection(MyMovieCollectionsDb(0, name))
    }

    override suspend fun getMovieFromDB(kpId: Int): MyMovieDb? {
        return movieDao.getMovieDB(kpId)
    }

    override suspend fun addMovie(movieCollectionDB: MyMovieDb) {
        movieDao.insertMovieDB(movieCollectionDB.toMovieCollectionDb())
    }

    override suspend fun getCollectionByNameFlow(collectionId: Int): Flow<List<MyMovieDb>> {
        val flow = movieCollectionsNameDao.getMoviesByCollectionIdFlow(collectionId.toString())
        return flow
    }

    override suspend fun getAllMyMovies(): List<MyMovieDb> {
        return movieDao.getAllMoviesDB()
    }

    override suspend fun removeMovie(movie: MyMovieDb) {
        movieDao.removeMovie( movie.toMovieCollectionDb())
    }

    override suspend fun updateMovie(movie: MyMovieDb) {
        movieDao.updateMovieDB( movie.toMovieCollectionDb())
    }

}