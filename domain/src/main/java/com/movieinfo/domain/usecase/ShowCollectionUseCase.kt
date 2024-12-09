package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.entity.MyMovieCollections
import com.movieinfo.domain.repository.MainPageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowCollectionUseCase @Inject constructor(private val mainPageRepository: MainPageRepository) {
    suspend fun getCollection(type: CollectionType, page: Int = 1): List<MovieCollection>{
        return mainPageRepository.getCollection(type,page)
    }
    suspend fun getPremieres(page: Int): List<MovieCollection>{
        return mainPageRepository.getPremiers(page)
    }
    suspend fun getSimilarMovie(id: Int): List<MovieCollection>{
        return mainPageRepository.getSimilarMovie(id)
    }
    suspend fun getCollectionByIdFlow(collectionId: Int): Flow<List<MovieDb>> {
        return    mainPageRepository.getCollectionByNameFlow(collectionId)
    }
    suspend fun getCollectionById(collectionId: Int):List<MovieCollection>{
        return    mainPageRepository.getCollectionByName(collectionId)
    }
    suspend fun getCollectionsName(): List<MyMovieCollections>{
        return mainPageRepository.getMyCollections()
    }
}