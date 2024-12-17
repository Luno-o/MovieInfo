package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.Country
import com.movieinfo.domain.entity.Genre
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.models.MovieCollectionRowMut
import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

//val emptyMovieCard = object : MovieCollection {
//    override val kpID: Int
//        get() = 1234
//    override val imdbId: String?
//        get() = null
//    override val nameRU: String?
//        get() = "MovieTitle"
//    override val nameENG: String?
//        get() = null
//    override val nameOriginal: String?
//        get() = null
//    override val countries: List<Country>?
//        get() = listOf()
//    override val genre: List<Genre>?
//        get() = listOf(object : Genre {
//
//            override val genre: String
//                get() = "action"
//        }
//        )
//    override val raitingKP: Float?
//        get() = 10.0f
//    override val raitingImdb: Float?
//        get() = null
//    override val year: Int?
//        get() = 2000
//    override val type: String?
//        get() = null
//    override val posterUrl: String?
//        get() = null
//    override val prevPosterUrl: String?
//        get() = null
//}
//class GetMainPageUseCaseTest {
//    @AfterEach
//    fun afterDown(){
//
//    }
//    @BeforeEach
//    fun beforeEach(){
//
//    }
//val testRepository = mock<MainMovieRepository>()
//    @Test
//    fun `should return the same data as in repository`(){
//        Mockito.`when`(testRepository.loadCollection())
//val useCase = GetMainPageUseCase(mainMovieRepository = testRepository)
//        val actual = useCase.execute(
//            listOf(MovieCollectionRowMut(MutableStateFlow(listOf(
//            emptyMovieCard)),"EmptyName",CollectionType.TOP_250_MOVIES))
//        )
//        val expected =
//    }
//}