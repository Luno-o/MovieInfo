package com.movieinfo.data.db

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
     fun insertCollection(collectionNames: MyMovieCollectionsDb)

    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME_COLLECTIONS}")
     fun getAllCollectionNames(): List<MyMovieCollectionsDb>

    @Delete
     fun removeCollection(myMovieCollectionsDb: MyMovieCollectionsDb)

    @Update
     fun updateMovieDB(myMovieCollectionsDb: MyMovieCollectionsDb)

    @Transaction
    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME} WHERE ${MovieDbContracts.Columns.COLLECTION_ID} LIKE '%' || :id || '%'")
     fun getMoviesByCollectionId(id: String): List<MovieCollectionDB>

    @Transaction
    @Query("SELECT * FROM ${MovieDbContracts.TABLE_NAME} WHERE ${MovieDbContracts.Columns.COLLECTION_ID} LIKE '%' || :id || '%'")
    fun getMoviesByCollectionIdFlow(id: String): Flow<List<MovieCollectionDB>>
}