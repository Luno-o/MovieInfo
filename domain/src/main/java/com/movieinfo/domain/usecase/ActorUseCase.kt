package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.StaffFullInfo
import com.movieinfo.domain.repository.MainPageRepository
import javax.inject.Inject

class ActorUseCase @Inject constructor(private val mainPageRepository: MainPageRepository) {


    suspend fun loadStaffFullInfoById(id: Int): StaffFullInfo{
        return mainPageRepository.loadStaffById(id)
    }

}