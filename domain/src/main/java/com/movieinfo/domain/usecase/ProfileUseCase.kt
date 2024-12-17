package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.entity.MyMovieCollections
import com.movieinfo.domain.repository.MainMovieRepository
import javax.inject.Inject

class GetMyCollectionsUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {

    suspend  operator fun invoke(): List<MyMovieCollections> {
        return mainMovieRepository.getMyCollections()
    }
}

class AddCollectionUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {
    suspend operator fun invoke(name: String) {
        mainMovieRepository.addCollection(name)
    }
}

class GetCollectionByNameUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {
    suspend operator fun invoke(name: String): List<MovieDb> {
        val id = mainMovieRepository.getMyCollections().find { it.collectionName == name }?.id
        return if (id != null) {
            mainMovieRepository.getCollectionById(id)
        } else {
            emptyList()
        }
    }
}

class DeleteHistoryUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {
    suspend operator fun invoke(collectionId: Int) {
        val collection = mainMovieRepository.getCollectionById(collectionId)
        collection.forEach { movie ->
            if (movie.collectionId.size == 1) {
                mainMovieRepository.removeMovie(movie)
            } else {
                val newCollection: MutableList<Int> = mutableListOf()
                newCollection.addAll(movie.collectionId)
                newCollection.remove(collectionId)
                mainMovieRepository.updateMovie(movie.copy(collectionId = newCollection))
            }
        }

    }
}