package com.example.movieinfo.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesDB(movies: List<MovieCollectionDB>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDB(movie: MovieCollectionDB)

    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME}")
    suspend fun getAllMoviesDB(): List<MovieCollectionDB>

    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME} WHERE ${MovieDbContracts.Columns.KP_ID} =:kpId")
    suspend fun getMovieDB(kpId: Int): MovieCollectionDB?

@Transaction
    @Query("DELETE FROM ${MovieDbContracts.TABLE_NAME} WHERE ${MovieDbContracts.Columns.COLLECTION_ID} =:collectionId")
    suspend fun deleteHistory(collectionId: Int)

    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME}")
    fun observeMovies(): Flow<List<MovieCollectionDB>>

    @Delete
    suspend fun removeMovie(movieDB: MovieCollectionDB)

    @Update
    suspend fun updateMovieDB(movieDB: MovieCollectionDB)
}
