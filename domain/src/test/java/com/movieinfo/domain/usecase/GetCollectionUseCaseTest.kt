package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.Country
import com.movieinfo.domain.entity.Genre
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.models.LoadStateUI
import com.movieinfo.domain.models.MovieCollectionRowMut
import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetCollectionUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = GetCollectionUseCase(mainMovieRepository = testRepository)
            private val state = MutableStateFlow<LoadStateUI<List<MovieCollection>>>(LoadStateUI.Loading)

    @Test
    fun `should return the same data as in repository`() {
        runBlocking {
            Mockito.`when`(testRepository.loadCollectionFlow(CollectionType.PREMIERES,1))
                .thenReturn(flowOf(LoadStateUI.Success(listOf( fakeMovieCollection))))
            val actual = useCase(MovieCollectionRowMut(state,"collection",CollectionType.PREMIERES))
            assertEquals(fakeMovieCollection, (actual.last() as LoadStateUI.Success).data.last())
        }
    }

    @Test
    fun `should return error`() {
        runBlocking {
            Mockito.`when`(testRepository.loadCollectionFlow(CollectionType.PREMIERES,1))
                .thenReturn(flowOf(LoadStateUI.Error(Throwable("error"))))

            val actual = useCase(MovieCollectionRowMut(state,"collection",CollectionType.PREMIERES))
            assertEquals(
                "error",
                (actual.first() as LoadStateUI.Error).throwable.message
            )

        }
    }
    @Test
    fun `should return loading`() {
        runBlocking {
            Mockito.`when`(testRepository.loadCollectionFlow(CollectionType.PREMIERES,1))
                .thenReturn(flowOf(LoadStateUI.Loading))
            val actual = useCase(MovieCollectionRowMut(state,"collection",CollectionType.PREMIERES))
            assertEquals(
                LoadStateUI.Loading,
                (actual.first() as LoadStateUI.Loading)
            )

        }
    }
}
class MovieCollectionImpl(
    override val kpID: Int,
    override val imdbId: String?,
    override val nameRU: String?,
    override val nameENG: String?,
    override val nameOriginal: String?,
    override val countries: List<Country>?,
    override val genre: List<Genre>?,
    override val ratingKP: Float?,
    override val ratingImdb: Float?,
    override val year: Int?,
    override val type: String?,
    override val posterUrl: String?,
    override val prevPosterUrl: String?
) : MovieCollection
 val fakeMovieCollection = MovieCollectionImpl(
    kpID = 1234,
    imdbId = null,
    nameRU = "MovieTitle",
    nameENG = null,
    nameOriginal = null,
    countries = listOf(),
    genre = listOf(GenreImpl("action")),
    ratingKP = 10.0f,
    ratingImdb = null,
    year = 2000,
    type = null,
    posterUrl = "null",
    prevPosterUrl = "null"
)