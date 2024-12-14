package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.StaffFullInfo
import com.movieinfo.domain.repository.MainMovieRepository
import javax.inject.Inject

class ActorUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {

    suspend fun execute(id: Int): StaffFullInfo{
        return mainMovieRepository.loadStaffById(id)
    }

}