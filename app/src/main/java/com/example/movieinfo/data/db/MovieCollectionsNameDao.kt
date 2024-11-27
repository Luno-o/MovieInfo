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
interface MovieCollectionsNameDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCollection(collectionNames: MyMovieCollections)

    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME_COLLECTIONS}")
    suspend fun getAllCollectionNames(): List<MyMovieCollections>

    @Delete
    suspend fun removeMovie(myMovieCollections: MyMovieCollections)

    @Update
    suspend fun updateMovieDB(myMovieCollections: MyMovieCollections)

    @Transaction
    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME} WHERE ${MovieDbContracts.Columns.COLLECTION_ID} LIKE '%' || :id || '%'")
    suspend fun getMoviesByCollectionId(id: String): List<MovieCollectionDB>

    @Transaction
    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME} WHERE ${MovieDbContracts.Columns.COLLECTION_ID} LIKE '%' || :id || '%'")
    fun getMoviesByCollectionIdFlow(id: String): Flow<List<MovieCollectionDB>>
}