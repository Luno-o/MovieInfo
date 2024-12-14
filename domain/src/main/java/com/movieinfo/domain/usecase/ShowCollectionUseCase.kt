package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShowCollectionUseCase
@Inject constructor(
    private val mainMovieRepository: MainMovieRepository
) {
    suspend fun execute(type: CollectionType, page: Int = 1): List<MovieCollection> {
        return mainMovieRepository.loadCollection(type, page) ?: emptyList()
    }

}

class GetSimilarCollectionUseCase
@Inject constructor(
    private val mainMovieRepository: MainMovieRepository
) {
    suspend fun execute(id: Int): List<MovieCollection> {
        return mainMovieRepository.getSimilarMovie(id)
    }
}

class ShowMyCollectionFlowUseCase
@Inject constructor(
    private val mainMovieRepository: MainMovieRepository
) {
    suspend fun execute(name: String): Flow<List<MovieDb>> {
        val id = mainMovieRepository.getMyCollections().find { it.collectionName == name }?.id
        return if (id != null) {
            mainMovieRepository.getCollectionByNameFlow(id)
        } else flow { emptyList<MovieCollection>() }
    }
}