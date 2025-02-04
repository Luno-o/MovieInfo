package com.movieinfo.data.repository.storage
import com.movieinfo.data.db.Database
import com.movieinfo.data.db.MovieCollectionDB
import com.movieinfo.data.db.MovieCollectionsNameDao
import com.movieinfo.data.db.MovieDBDao
import com.movieinfo.data.db.MyMovieCollectionsDb
import com.movieinfo.data.extensions.toMovieCollectionDb
import com.movieinfo.data.repository.storage.models.MyCollections
import com.movieinfo.data.repository.storage.models.MyMovieDb
import kotlinx.coroutines.flow.Flow


class MovieStorageImpl(
    private val movieDao: MovieDBDao = Database.instance.movieDBDao(),
    private val movieCollectionsNameDao: MovieCollectionsNameDao = Database.instance.movieCollectionsNameDao()
): MovieStorage {


    override  fun getMyCollections(): List<MyCollections> {
        return movieCollectionsNameDao.getAllCollectionNames()
    }

    override fun removeMyCollections(myMovieCollectionsDb: MyMovieCollectionsDb) {
        val collection = movieCollectionsNameDao.getMoviesByCollectionId(myMovieCollectionsDb.id.toString())
            val newCollection = mutableListOf<MovieCollectionDB>()
        collection.forEach {
            val newCollections = mutableListOf<Int>()
                newCollections.addAll(it.collectionId)
                newCollections.remove(myMovieCollectionsDb.id)
            newCollection.add(it.copy(collectionId = newCollections))
        }
        newCollection.forEach {
            if (it.collectionId.isEmpty()){
                movieDao.removeMovie(it)
            }else{
        movieDao.insertMoviesDB(newCollection)
            }
        }
        movieCollectionsNameDao.removeCollection(myMovieCollectionsDb = myMovieCollectionsDb)
    }

    override  fun getCollectionById(collectionId: Int): List<MyMovieDb> {
        return movieCollectionsNameDao.getMoviesByCollectionId(collectionId.toString())
    }

    override  fun getMovieCollectionId(kpId: Int): List<Int>? {
        return movieDao.getMovieDB(kpId)?.collectionId
    }

    override  fun addCollection(name: String) {
        movieCollectionsNameDao.insertCollection(MyMovieCollectionsDb(0, name))
    }

    override  fun getMovieFromDB(kpId: Int): MyMovieDb? {
        return movieDao.getMovieDB(kpId)
    }

    override  fun addMovie(movieCollectionDB: MyMovieDb) {
        movieDao.insertMovieDB(movieCollectionDB.toMovieCollectionDb())
    }

    override  fun getCollectionByNameFlow(collectionId: Int): Flow<List<MyMovieDb>> {
        return movieCollectionsNameDao.getMoviesByCollectionIdFlow(collectionId.toString())
    }

    override  fun getAllMyMovies(): List<MyMovieDb> {
        return movieDao.getAllMoviesDB()
    }

    override  fun removeMovie(movie: MyMovieDb) {
        movieDao.removeMovie( movie.toMovieCollectionDb())
    }

    override  fun updateMovie(movie: MyMovieDb) {
        movieDao.updateMovieDB( movie.toMovieCollectionDb())
    }

}