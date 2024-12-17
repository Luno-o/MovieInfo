package com.movieinfo.domain.usecase

import com.movieinfo.domain.repository.MainMovieRepository
import com.movieinfo.domain.models.MovieCollectionRowMut
import javax.inject.Inject

class
GetCollectionUseCase @Inject constructor(val mainMovieRepository: MainMovieRepository) {

     suspend operator fun invoke(row: MovieCollectionRowMut)=
         mainMovieRepository.loadCollectionFlow(row.movieType, page = 1)

}