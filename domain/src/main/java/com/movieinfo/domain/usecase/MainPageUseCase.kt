package com.movieinfo.domain.usecase

import com.movieinfo.domain.repository.MainPageRepository
import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieDb
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class
MainPageUseCase @Inject constructor(val mainPageRepository: MainPageRepository) {

   suspend fun getCollection(type: CollectionType, page: Int = 1): List<MovieCollection>{
       return mainPageRepository.loadCollection(collectionType = type,page = page)?: emptyList()
    }
    suspend fun getPremieres(): Flow<List<MovieCollection>>{
        return mainPageRepository.loadPremieres()
    }
}