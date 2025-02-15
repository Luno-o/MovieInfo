package com.movieinfo.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.movieinfo.data.db.MovieCollectionDB
import com.movieinfo.data.db.MovieDbContracts
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertMoviesDB(movies: List<MovieCollectionDB>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertMovieDB(movie: MovieCollectionDB)

    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME}")
     fun getAllMoviesDB(): List<MovieCollectionDB>

    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME} WHERE ${MovieDbContracts.Columns.KP_ID} =:kpId")
     fun getMovieDB(kpId: Int): MovieCollectionDB?

@Transaction
    @Query("DELETE FROM ${MovieDbContracts.TABLE_NAME} WHERE ${MovieDbContracts.Columns.COLLECTION_ID} =:collectionId")
     fun deleteHistory(collectionId: Int)

    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME}")
    fun observeMovies(): Flow<List<MovieCollectionDB>>

    @Delete
     fun removeMovie(movieDB: MovieCollectionDB)

    @Update
     fun updateMovieDB(movieDB: MovieCollectionDB)
}
