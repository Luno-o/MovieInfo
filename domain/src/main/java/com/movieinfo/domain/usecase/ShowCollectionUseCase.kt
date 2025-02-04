package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.models.LoadStateUI
import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShowCollectionUseCase
@Inject constructor(
    private val mainMovieRepository: MainMovieRepository
) {
    suspend operator fun invoke(type: CollectionType, page: Int = 1)=
         mainMovieRepository.loadCollection(type, page)
}

class GetSimilarCollectionFlowUseCase
@Inject constructor(
    private val mainMovieRepository: MainMovieRepository
) {
    suspend operator fun invoke(id: Int) =
         mainMovieRepository.loadSimilarMovieFlow(id)

}
class GetSimilarCollectionUseCase
@Inject constructor(
    private val mainMovieRepository: MainMovieRepository
) {
    suspend operator fun invoke(id: Int) =
        mainMovieRepository.loadSimilarMovie(id)

}

class ShowMyCollectionFlowUseCase
@Inject constructor(
    private val mainMovieRepository: MainMovieRepository
) {
    suspend operator fun invoke(name: String): Flow<LoadStateUI<List<MovieDb>>> {
        val id = mainMovieRepository.getMyCollections().find { it.collectionName == name }?.id
        return if (id != null) {
            mainMovieRepository.getCollectionByNameFlow(id)
        } else flow {
            LoadStateUI.Success<List<MovieDb>>(emptyList())
        }
    }
}