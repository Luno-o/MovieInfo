package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieType
import com.movieinfo.domain.entity.SearchMovieFilter
import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SearchPageUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = SearchPageUseCase(mainMovieRepository = testRepository)

    @Test
    fun `should return the same data as in repository`() {
        runBlocking {
            Mockito.`when`(testRepository.getSearchByFilters(
                searchFilter.countryInd, searchFilter.genreInd, searchFilter.sortType,
                searchFilter.movieType,searchFilter.raitingFrom,
                searchFilter.raitingTo,
                searchFilter.yearAfter,
                searchFilter.yearBefore,
                searchFilter.queryState, 1))
                .thenReturn(listOf(fakeMovieCollection))
            val actual = useCase.execute(searchFilter,1)
            assertEquals(listOf(fakeMovieCollection), actual)
        }
    }

    @Test
    fun `should return the same data by key`() {
        runBlocking {
            Mockito.`when`(testRepository.getSearchByKeyWord("key", 1))
                .thenReturn(listOf(fakeMovieBaseInfo))
            val actual = useCase.execute("key",1)
            assertEquals(listOf(fakeMovieBaseInfo), actual)
        }
    }
    @Test
    fun `should return empty list`() {
        runBlocking {
            val actual = useCase.execute("",1)
            assertEquals(emptyList<MovieBaseInfo>(), actual)
        }
    }
}
class SearchMovieFilterImp(
    override val countryInd: Array<Int>,
    override val genreInd: Array<Int>,
    override val sortType: String,
    override val movieType: String,
    override val raitingFrom: Int,
    override val raitingTo: Int,
    override val yearBefore: Int,
    override val yearAfter: Int,
    override val queryState: String
) : SearchMovieFilter
val searchFilter = SearchMovieFilterImp(
    arrayOf(1), arrayOf(1),"ORDER",
    MovieType.FILM.name,1,10,2000,1900,"query")