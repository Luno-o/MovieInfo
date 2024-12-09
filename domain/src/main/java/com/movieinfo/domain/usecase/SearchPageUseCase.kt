package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.SearchMovieFilter
import com.movieinfo.domain.repository.MainPageRepository
import javax.inject.Inject

class SearchPageUseCase @Inject constructor(private val mainPageRepository: MainPageRepository) {
    suspend fun getSearchByFilter(   searchFilter: SearchMovieFilter,
                                     page: Int): List<MovieCollection>{
        return mainPageRepository.
        getSearchByFilters(searchFilter.countryInd,searchFilter.genreInd, searchFilter.sortType,
            searchFilter.movieType, searchFilter.raitingFrom,
            searchFilter.raitingTo,
            searchFilter.yearAfter,
            searchFilter.yearBefore,
            searchFilter.queryState, page)
    }
    suspend fun getSearchByKeyWord(key: String,page: Int): List<MovieBaseInfo>{
        return if (key.isNotEmpty()){
            mainPageRepository.getSearchByKeyWord(key,page)
        }else emptyList()
    }

}