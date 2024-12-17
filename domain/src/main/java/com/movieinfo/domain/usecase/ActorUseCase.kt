package com.movieinfo.domain.usecase

import com.movieinfo.domain.repository.MainMovieRepository
import javax.inject.Inject

class ActorUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {

      suspend operator fun invoke(id: Int) =  mainMovieRepository.loadStaffById(id)

}