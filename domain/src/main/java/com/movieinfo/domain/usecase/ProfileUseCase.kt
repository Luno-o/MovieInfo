package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.entity.MyMovieCollections
import com.movieinfo.domain.repository.MainPageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor (private val mainPageRepository: MainPageRepository) {

    suspend fun addCollection(name: String) {
        mainPageRepository.addCollection(name)
    }
    suspend fun getCollectionById(collectionId: Int):List<MovieDb>{
        return    mainPageRepository.getCollectionByName(collectionId)
    }
    suspend fun getAllCollections(): List<MovieDb>{
        return mainPageRepository.getAllCollections()
    }
    suspend fun getCollectionByIdFlow(collectionId: Int): Flow<List<MovieDb>> {
        return    mainPageRepository.getCollectionByNameFlow(collectionId)
    }
    suspend fun getCollectionsName(): List<MyMovieCollections>{
        return mainPageRepository.getMyCollections()
    }
    suspend fun deleteHistory(collectionId: Int){
        val collection =  mainPageRepository.getCollectionByName(collectionId)
        collection.forEach { movie->
            if (movie.collectionId.size == 1){
                mainPageRepository.removeMovie(movie)
            }else{
                val newCollection : MutableList<Int> = mutableListOf()
                newCollection.addAll(movie.collectionId)
                newCollection.remove(collectionId)
                mainPageRepository.updateMovie(movie.copy(collectionId = newCollection))
            }
        }

    }
}