package com.movieinfo.data.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeMovieCollectionsNameDao: MovieCollectionsNameDao {
    private val list = mutableListOf<MyMovieCollectionsDb>()
    override fun insertCollection(collectionNames: MyMovieCollectionsDb) {
        list.add(collectionNames)
    }

    override fun getAllCollectionNames(): List<MyMovieCollectionsDb> {
        return list
    }

    override fun removeCollection(myMovieCollectionsDb: MyMovieCollectionsDb) {
        list.remove(myMovieCollectionsDb)
    }

    override fun updateMovieDB(myMovieCollectionsDb: MyMovieCollectionsDb) {
        TODO("Not yet implemented")
    }

    override fun getMoviesByCollectionId(id: String): List<MovieCollectionDB> {
        return if (id == "0"){
        list.filter { it.id.toString() == id }.map { MovieCollectionDB(1,null,"name",
            "nameEn","maneOr"
        ,null,null,null,null,null,null,null,
            null, listOf(it.id)
        ) }
    }else {
         error("error")
    }
    }

    override fun getMoviesByCollectionIdFlow(id: String): Flow<List<MovieCollectionDB>> {
        return if (id == "0"){
            flowOf(list.filter { it.id.toString() == id }.map { MovieCollectionDB(1,null,"name","nameEn","maneOr"
            ,null,null,null,null,null,null,null,
            null, listOf(it.id)
        ) } )
    }else {
             error("error")
    }
}

}