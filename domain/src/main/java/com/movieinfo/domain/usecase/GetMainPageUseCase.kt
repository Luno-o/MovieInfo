package com.movieinfo.domain.usecase

import com.movieinfo.domain.repository.MainMovieRepository
import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.models.MovieCollectionRowMut
import javax.inject.Inject

class
GetMainPageUseCase @Inject constructor(val mainMovieRepository: MainMovieRepository) {

    suspend fun execute(list: List<MovieCollectionRowMut>){
        if (list.isNotEmpty()) {
            list.forEach {
                if (it.movieType == CollectionType.PREMIERES) {
                    it.movieCards.value = mainMovieRepository.loadPremieres()
                } else {
                    it.movieCards.value = mainMovieRepository
                        .loadCollection(it.movieType, page = 1) ?: emptyList()
                }
            }
        }
    }
}