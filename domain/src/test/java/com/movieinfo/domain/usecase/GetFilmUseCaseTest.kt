package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.Country
import com.movieinfo.domain.entity.Genre
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.models.LoadStateUI
import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetFilmUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()

    @Test
    fun `should return the same data as in repository`() {
        runBlocking {
            Mockito.`when`(testRepository.getMovieById(1))
                .thenReturn(flowOf(LoadStateUI.Success(fakeMovieBaseInfo)))
            val useCase = GetFilmUseCase(mainMovieRepository = testRepository)
            val actual = useCase(1)
            assertEquals(fakeMovieBaseInfo, (actual.first() as LoadStateUI.Success).data)
        }
    }

    @Test
    fun `should return error`() {
        runBlocking {
            Mockito.`when`(testRepository.getMovieById(0))
                .thenReturn(flowOf(LoadStateUI.Error(Throwable("error"))))
            val useCase = GetFilmUseCase(mainMovieRepository = testRepository)
            val actual = useCase(0)
           assertEquals(
                "error",
                (actual.first() as LoadStateUI.Error).throwable.message
            )

        }
    }
    @Test
    fun `should return loading`() {
        runBlocking {
            Mockito.`when`(testRepository.getMovieById(Mockito.anyInt()))
                .thenReturn(flowOf(LoadStateUI.Loading))
            val useCase = GetFilmUseCase(mainMovieRepository = testRepository)
            val actual = useCase(0)
            assertEquals(
                LoadStateUI.Loading,
                (actual.first() as LoadStateUI.Loading)
            )

        }
    }
}
val fakeMovieBaseInfo = MovieBaseInfoImpl(
    kpID = 4414587,
    kpHdId = "4a0e81d5d0584c52b61baaa6e1d1ff71",
    ImdbId = "tt5177120",
    nameRU = "Министерство неджентльменских дел",
    nameENG = null,
    nameOriginal = "The Ministry of Ungentlemanly Warfare",
    posterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp/4414587.jpg",
    prevPosterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/4414587.jpg",
    coverUrl = "https://avatars.mds.yandex.net/get-ott/224348/2a0000018f3e89a200cf1fd5a46d92eb7fc5/orig",
    logoUrl = null,
    reviewsCount = 6,
    ratingGoodReview = 17.0f,
    ratingGoodReviewVoteCount = 1,
    ratingKinopoisk = 7.1f,
    ratingKinopoiskVoteCount = 6099,
    ratingImdb = 7.3f,
    ratingImdbVoteCount = 8721,
    ratingFilmCritics = 6.6f,
    ratingFilmCriticsVoteCount = 31,
    ratingAwait = 99.0f,
    ratingAwaitCount = 19970,
    ratingRfCritics = null,
    ratingRfCriticsVoteCount = 0,
    webUrl = "https://www.kinopoisk.ru/film/4414587/",
    year = 2024,
    filmLength = 120,
    slogan = null,
    description = "1942 год, Великобритания. Они—лучшие излучших. " +
            "Отпетые авантюристы и первоклассные спецы, привыкшие действовать в одиночку." +
            " Но когда на кону стоит судьба всего мира, им приходится объединиться в" +
            " сверхсекретное боевое подразделение и отправиться на " +
            "выполнение дерзкой миссии против нацистов. Теперь их дело —" +
            " война, и вести они её будут совершенно не по-джентльменски.",
    shortDescription = "Отряду авантюристов поручают невыполнимую миссию. Новый фильм" +
            " Гая Ричи и продюсера «Пиратов Карибского моря»",
    editorAnnotation = "В мае на Кинопоиске",
    isTicketsAvailable = false,
    productionStatus = null,
    type = "FILM",
    ratingMpaa = "r",
    ratingAgeLimits = "age18",
    hasImax = false,
    has3D = false,
    lastSync = "2024-05-06T18:07:16.522463",
    countries = listOf(
        CountryImpl(
            country = "США"
        ), CountryImpl(
            country = "Великобритания"
        ), CountryImpl(
            country = "Турция"
        )
    ),
    genres = listOf(
        GenreImpl(
            genre = "драма"

        ), GenreImpl(
            genre = "боевик"

        ), GenreImpl(
            genre = "военный"

        ), GenreImpl(
            genre = "история"
        )
    ),
    startYear = null,
    endYear = null,
    serial = false,
    shortFilm = false,
    completed = false
)
class GenreImpl(override val genre: String): Genre
class CountryImpl(override val country: String): Country
class MovieBaseInfoImpl(
    override val kpID: Int,
    override val kpHdId: String?,
    override val ImdbId: String?,
    override val nameRU: String?,
    override val nameENG: String?,
    override val nameOriginal: String?,
    override val posterUrl: String?,
    override val prevPosterUrl: String?,
    override val coverUrl: String?,
    override val logoUrl: String?,
    override val reviewsCount: Int?,
    override val ratingGoodReview: Float?,
    override val ratingGoodReviewVoteCount: Int?,
    override val ratingKinopoisk: Float?,
    override val ratingKinopoiskVoteCount: Int?,
    override val ratingImdb: Float?,
    override val ratingImdbVoteCount: Int?,
    override val ratingFilmCritics: Float?,
    override val ratingFilmCriticsVoteCount: Int?,
    override val ratingAwait: Float?,
    override val ratingAwaitCount: Int?,
    override val ratingRfCritics: Float?,
    override val ratingRfCriticsVoteCount: Int?,
    override val webUrl: String?,
    override val year: Int?,
    override val filmLength: Int?,
    override val slogan: String?,
    override val description: String?,
    override val shortDescription: String?,
    override val editorAnnotation: String?,
    override val isTicketsAvailable: Boolean?,
    override val productionStatus: String?,
    override val type: String?,
    override val ratingMpaa: String?,
    override val ratingAgeLimits: String?,
    override val hasImax: Boolean?,
    override val has3D: Boolean?,
    override val lastSync: String?,
    override val countries: List<Country>,
    override val genres: List<Genre>,
    override val startYear: Int?,
    override val endYear: Int?,
    override val serial: Boolean?,
    override val shortFilm: Boolean?,
    override val completed: Boolean?
): MovieBaseInfo