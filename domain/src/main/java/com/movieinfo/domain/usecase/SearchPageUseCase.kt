package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.SearchMovieFilter
import com.movieinfo.domain.repository.MainMovieRepository
import javax.inject.Inject

class SearchPageUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {

    suspend fun execute(searchFilter: SearchMovieFilter,
                        page: Int): List<MovieCollection>{
        return mainMovieRepository.
        getSearchByFilters(searchFilter.countryInd,searchFilter.genreInd, searchFilter.sortType,
            searchFilter.movieType, searchFilter.raitingFrom,
            searchFilter.raitingTo,
            searchFilter.yearAfter,
            searchFilter.yearBefore,
            searchFilter.queryState, page)
    }
    suspend fun execute(key: String, page: Int): List<MovieBaseInfo>{
        return if (key.isNotEmpty()){
            mainMovieRepository.getSearchByKeyWord(key,page)
        }else emptyList()
    }

}