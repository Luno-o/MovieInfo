package com.movieinfo.data.repository

import com.movieinfo.data.KinopoiskApi
import com.movieinfo.data.db.FakeMovieCollectionsNameDao
import com.movieinfo.data.db.FakeMovieDBDao
import com.movieinfo.data.db.MovieCollectionDB
import com.movieinfo.data.db.MyMovieCollectionsDb
import com.movieinfo.data.extensions.toMyMovieDb
import com.movieinfo.data.moviesDto.CountryDto
import com.movieinfo.data.moviesDto.EpisodesDto
import com.movieinfo.data.moviesDto.GenreDto
import com.movieinfo.data.moviesDto.MovieBaseInfoDto
import com.movieinfo.data.moviesDto.MovieCollectionDto
import com.movieinfo.data.moviesDto.MovieCollectionWrapperDto
import com.movieinfo.data.moviesDto.MovieGalleryDto
import com.movieinfo.data.moviesDto.MoviePremiereDto
import com.movieinfo.data.moviesDto.SearchMovie
import com.movieinfo.data.moviesDto.SearchWrapperDto
import com.movieinfo.data.moviesDto.SerialWrapperDto
import com.movieinfo.data.moviesDto.ServerSearchWrapperDto
import com.movieinfo.data.moviesDto.SimilarMovieDto
import com.movieinfo.data.moviesDto.StaffDto
import com.movieinfo.data.moviesDto.StaffFullInfoDto
import com.movieinfo.data.repository.storage.MovieStorage
import com.movieinfo.data.repository.storage.MovieStorageImpl
import com.movieinfo.data.repository.storage.models.MovieBaseInfoImp
import com.movieinfo.data.repository.storage.models.MovieCollectionImpl
import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.entity.Genre
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.models.LoadStateUI
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.threeten.bp.Instant
import org.threeten.bp.Month
import org.threeten.bp.Year
import org.threeten.bp.ZoneId


class MainMovieRepositoryImplTest {
    private val api: KinopoiskApi = mock()
    private val storage: MovieStorage = MovieStorageImpl(movieDao = FakeMovieDBDao(), movieCollectionsNameDao = FakeMovieCollectionsNameDao())
    private val items = listOf(emptyMovieCard, emptyMovieCard)
    private val wrapperPremiereDto = ServerSearchWrapperDto(10, items)


    private val mainMovieRepositoryImpl = MainMovieRepositoryImpl(storage, api)

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    fun tearDown() {
    }

    @Test
    fun `should return premieres`() = runTest {
        Mockito.`when`(
            api.getPremiers(
                Year.now().toString().toInt(), Month.from(
                    Instant.now().atZone(ZoneId.systemDefault())
                ).toString()
            )
        ).thenReturn(wrapperPremiereDto)
        val response = mainMovieRepositoryImpl.loadCollection(CollectionType.PREMIERES, 1)
        Assertions.assertEquals(wrapperPremiereDto.items.map {
            MovieCollectionImpl(
                prevPosterUrl = it.prevPosterUrl,
                nameRU = it.nameRU,
                ratingKP = null,
                genre = it.genre,
                kpID = it.kpID,
                nameENG = it.nameENG,
                year = it.year,
                posterUrl = it.posterUrl,
                countries = it.countries,
                ratingImdb = null,
                nameOriginal = null,
                type = "FILM",
                imdbId = null
            )
        }.firstOrNull(), response.firstOrNull())
    }

    @Test
    fun `should return collection`() = runTest {
        Mockito.`when`(api.getCollectionMovies(CollectionType.TOP_POPULAR_ALL, 1))
            .thenReturn(fakeMovieCollectionWrapperDto)
        val response = mainMovieRepositoryImpl.loadCollection(CollectionType.TOP_POPULAR_ALL, 1)
        Assertions.assertEquals(fakeMovieCollectionWrapperDto.items, response)
    }

    @Test
    fun `should return premieres flow`() = runTest {
        Mockito.`when`(
            api.getPremiers(
                Year.now().toString().toInt(), Month.from(
                    Instant.now().atZone(ZoneId.systemDefault())
                ).toString()
            )
        )
            .thenReturn(wrapperPremiereDto)

        val response = mainMovieRepositoryImpl.loadCollectionFlow(CollectionType.PREMIERES, 1)

        Assertions.assertEquals(wrapperPremiereDto.items.map {
            MovieCollectionImpl(
                prevPosterUrl = it.prevPosterUrl,
                nameRU = it.nameRU,
                ratingKP = null,
                genre = it.genre,
                kpID = it.kpID,
                nameENG = it.nameENG,
                year = it.year,
                posterUrl = it.posterUrl,
                countries = it.countries,
                ratingImdb = null,
                nameOriginal = null,
                type = "FILM",
                imdbId = null
            )
        }.firstOrNull(), (response.lastOrNull() as LoadStateUI.Success).data.firstOrNull())
    }

    @Test
    fun `should return collection flow`() = runTest {
        Mockito.`when`(api.getCollectionMovies(CollectionType.TOP_POPULAR_ALL, 1))
            .thenReturn(fakeMovieCollectionWrapperDto)
        val response = mainMovieRepositoryImpl.loadCollectionFlow(CollectionType.TOP_POPULAR_ALL, 1)
        Assertions.assertEquals(
            fakeMovieCollectionWrapperDto.items,
            (response.lastOrNull() as LoadStateUI.Success).data
        )
    }

    @Test
    fun `should return error flow premiere`() = runTest {
        Mockito.`when`(
            api.getPremiers(
                Year.now().toString().toInt(), Month.from(
                    Instant.now().atZone(ZoneId.systemDefault())
                ).toString()
            )
        ).thenThrow(RuntimeException("error"))
        val response = mainMovieRepositoryImpl.loadCollectionFlow(CollectionType.PREMIERES, 1)
        Assertions.assertEquals(
            "error",
            (response.lastOrNull() as LoadStateUI.Error).throwable.message
        )
    }

    @Test
    fun `should return error collection flow`() = runTest {
        Mockito.`when`(api.getCollectionMovies(CollectionType.TOP_POPULAR_ALL, 1))
            .thenThrow(RuntimeException("error"))
        val response = mainMovieRepositoryImpl.loadCollectionFlow(CollectionType.TOP_POPULAR_ALL, 1)
        Assertions.assertEquals(
            "error",
            (response.lastOrNull() as LoadStateUI.Error).throwable.message
        )
    }

    @Test
    fun `should return staff by id`() = runTest {
        Mockito.`when`(api.getStaffById(1)).thenReturn(staffFullInfo)
        val response = mainMovieRepositoryImpl.loadStaffById(1)
        Assertions.assertEquals(staffFullInfo, (response.lastOrNull() as LoadStateUI.Success).data)
    }

    @Test
    fun `should return error staff`() = runTest {
        Mockito.`when`(api.getStaffById(1)).thenThrow(RuntimeException("error"))
        val response = mainMovieRepositoryImpl.loadStaffById(1)
        Assertions.assertEquals(
            "error",
            (response.lastOrNull() as LoadStateUI.Error).throwable.message
        )
    }

    @Test
    fun `should return seasons by id`() = runTest {
        Mockito.`when`(api.getSerialSeasons(1)).thenReturn(serverSearchWrapperDtoSerialWrapper)
        val response = mainMovieRepositoryImpl.loadSeasons(1)
        Assertions.assertEquals(
            serverSearchWrapperDtoSerialWrapper.items,
            (response.lastOrNull() as LoadStateUI.Success).data
        )
    }

    @Test
    fun `should return error seasons`() = runTest {
        Mockito.`when`(api.getSerialSeasons(1)).thenThrow(RuntimeException("error"))
        val response = mainMovieRepositoryImpl.loadSeasons(1)
        Assertions.assertEquals(
            "error",
            (response.lastOrNull() as LoadStateUI.Error).throwable.message
        )
    }

    @Test
    fun `should return similar movie by id flow`() = runTest {
        Mockito.`when`(api.getSimilarMovie(1)).thenReturn(serverSearchWrapperDtoSimilar)
        val response = mainMovieRepositoryImpl.loadSimilarMovieFlow(1)
        Assertions.assertEquals(serverSearchWrapperDtoSimilar.items.map {
            MovieCollectionImpl(
                kpID = it.filmID,
                imdbId = null,
                nameRU = it.nameRU,
                nameENG = it.nameENG,
                nameOriginal = it.nameOriginal,
                countries = null,
                genre = null,
                ratingKP = null,
                ratingImdb = null,
                year = null,
                type = null,
                posterUrl = it.posterUrl,
                prevPosterUrl = it.prevPosterUrl
            )
        }, (response.lastOrNull() as LoadStateUI.Success).data)
    }

    @Test
    fun `should return error similar movie flow`() = runTest {
        Mockito.`when`(api.getSimilarMovie(1)).thenThrow(RuntimeException("error"))
        val response = mainMovieRepositoryImpl.loadSimilarMovieFlow(1)
        Assertions.assertEquals(
            "error",
            (response.lastOrNull() as LoadStateUI.Error).throwable.message
        )
    }

    @Test
    fun `should return similar movie by id`() = runTest {
        Mockito.`when`(api.getSimilarMovie(1)).thenReturn(serverSearchWrapperDtoSimilar)
        val response = mainMovieRepositoryImpl.loadSimilarMovieFlow(1)
        Assertions.assertEquals(serverSearchWrapperDtoSimilar.items.map {
            MovieCollectionImpl(
                kpID = it.filmID,
                imdbId = null,
                nameRU = it.nameRU,
                nameENG = it.nameENG,
                nameOriginal = it.nameOriginal,
                countries = null,
                genre = null,
                ratingKP = null,
                ratingImdb = null,
                year = null,
                type = null,
                posterUrl = it.posterUrl,
                prevPosterUrl = it.prevPosterUrl
            )
        }, (response.lastOrNull() as LoadStateUI.Success).data)
    }

    @Test
    fun `should return error similar movie`() = runTest {
        Mockito.`when`(api.getSimilarMovie(1)).thenThrow(RuntimeException("error"))
        val response = mainMovieRepositoryImpl.loadSimilarMovieFlow(1)
        Assertions.assertEquals(
            "error",
            (response.lastOrNull() as LoadStateUI.Error).throwable.message
        )
    }

    @Test
    fun `should return movie gallery flow`() = runTest {
        Mockito.`when`(api.getMovieGallery(1, GalleryType.POSTER)).thenReturn(
            serverSearchWrapperDtoGallery
        )
        val response = mainMovieRepositoryImpl.getMovieGalleryFlow(1, GalleryType.POSTER)
        Assertions.assertEquals(
            serverSearchWrapperDtoGallery.items,
            (response.lastOrNull() as LoadStateUI.Success).data
        )
    }

    @Test
    fun `should return error gallery flow`() = runTest {
        Mockito.`when`(api.getMovieGallery(1, GalleryType.POSTER))
            .thenThrow(RuntimeException("error"))
        val response = mainMovieRepositoryImpl.getMovieGalleryFlow(1, GalleryType.POSTER)
        Assertions.assertEquals(
            "error",
            (response.lastOrNull() as LoadStateUI.Error).throwable.message
        )
    }

    @Test
    fun `should return movie gallery`() = runTest {
        Mockito.`when`(api.getMovieGallery(1, GalleryType.POSTER)).thenReturn(
            serverSearchWrapperDtoGallery
        )
        val response = mainMovieRepositoryImpl.getMovieGallery(1, GalleryType.POSTER)
        Assertions.assertEquals(serverSearchWrapperDtoGallery.items, response)
    }

    @Test
    fun `should return staff by film id`() = runTest {
        Mockito.`when`(api.getStaffByFilmId(1)).thenReturn(
            listOf(staffDto)
        )
        val response = mainMovieRepositoryImpl.getStaffByFilmId(1)
        Assertions.assertEquals(
            listOf(staffDto),
            (response.lastOrNull() as LoadStateUI.Success).data
        )
    }

    @Test
    fun `should return error staff by film flow`() = runTest {
        Mockito.`when`(api.getStaffByFilmId(1)).thenThrow(RuntimeException("error"))
        val response = mainMovieRepositoryImpl.getStaffByFilmId(1)
        Assertions.assertEquals(
            "error",
            (response.lastOrNull() as LoadStateUI.Error).throwable.message
        )
    }

    @Test
    fun `should return movie by id`() = runTest {
        Mockito.`when`(api.getMovieById(1)).thenReturn(
            fakeMovieBaseInfo
        )
        val response = mainMovieRepositoryImpl.getMovieById(1)
        Assertions.assertEquals(
            fakeMovieBaseInfo,
            (response.lastOrNull() as LoadStateUI.Success).data
        )
    }

    @Test
    fun `should return error movie by id`() = runTest {
        Mockito.`when`(api.getMovieById(1)).thenThrow(RuntimeException("error"))
        val response = mainMovieRepositoryImpl.getMovieById(1)
        Assertions.assertEquals(
            "error",
            (response.lastOrNull() as LoadStateUI.Error).throwable.message
        )
    }

    @Test
    fun `should return search by filters`() = runTest {
        Mockito.`when`(
            api.getSearchByFilters(
                arrayOf(), arrayOf(), null, null, null, null, null, null,
                null, 1
            )
        ).thenReturn(
            fakeMovieCollectionWrapperDto
        )
        val response = mainMovieRepositoryImpl.getSearchByFilters(
            arrayOf(), arrayOf(), null, null, null, null, null, null,
            null, 1
        )
        Assertions.assertEquals(fakeMovieCollectionWrapperDto.items, response)
    }

    @Test
    fun `should return search by keyword`() = runTest {
        Mockito.`when`(api.getSearchByKeyWord("key", 1)).thenReturn(
            fakeSearchWrapperDto
        )
        val response = mainMovieRepositoryImpl.getSearchByKeyWord("key", 1)
        Assertions.assertEquals(fakeSearchWrapperDto.films.map {
            MovieBaseInfoImp(
                kpID = it.filmId,
                nameRU = it.nameRu,
                nameENG = it.nameEn,
                type = it.type,
                year = it.year,
                description = it.description,
                filmLength = null,
                countries = it.countries,
                genres = it.genres,
                ratingImdbVoteCount = it.ratingVoteCount,
                posterUrl = it.posterUrl,
                prevPosterUrl = it.posterUrlPreview

            )
        }.firstOrNull()?.kpID, response.firstOrNull()?.kpID)
    }

    @Test
    fun `should return my collections`() = runTest {
mainMovieRepositoryImpl.addCollection("Collection")
        val response = mainMovieRepositoryImpl.getMyCollections()
        Assertions.assertEquals("Collection", response.first().collectionName)
    }

    @Test
    fun `test insert movie`() = runTest {
            mainMovieRepositoryImpl.addMovie(
            movieDb
        )
        val response = mainMovieRepositoryImpl.getAllMyMovies()
        Assertions.assertEquals(movieDb, response.first())
    }
    @Test
    fun `test delete movie`() = runTest {
        mainMovieRepositoryImpl.addMovie(
            movieDb
        )
        val response = mainMovieRepositoryImpl.getAllMyMovies()
        Assertions.assertEquals(movieDb, response.first())
        mainMovieRepositoryImpl.removeMovie(movieDb)
        Assertions.assertEquals(emptyList<MovieDb>(),mainMovieRepositoryImpl.getAllMyMovies())
    }
    @Test
    fun `test insert collection`() = runTest {
        mainMovieRepositoryImpl.addCollection(
            "Collection"
        )
        val response = mainMovieRepositoryImpl.getMyCollections()
        Assertions.assertEquals("Collection", response.first().collectionName)
    }
    @Test
    fun `test delete collection`() = runTest {
        mainMovieRepositoryImpl.addCollection(
            "Collection"
        )
        val response = mainMovieRepositoryImpl.getMyCollections()
        Assertions.assertEquals("Collection", response.first().collectionName)

        mainMovieRepositoryImpl.removeCollection(MyMovieCollectionsDb(0,"Collection"))
        Assertions.assertEquals(emptyList<MyMovieCollectionsDb>(),mainMovieRepositoryImpl.getMyCollections())
    }
    @Test
    fun `should return my collection by id`() = runTest {
        mainMovieRepositoryImpl.addCollection("Collection")
        val response =
        mainMovieRepositoryImpl.getCollectionById(0)
        Assertions.assertEquals(movieDb, response.first())
    }
    @Test
    fun `should return movie collections ids`() = runTest {
        mainMovieRepositoryImpl.addMovie(
            movieDb
        )
        val response = mainMovieRepositoryImpl.getMovieCollectionId(1)
        Assertions.assertEquals(0, response?.first())
    }
    @Test
    fun `should return my collection by name flow`() = runTest {
        mainMovieRepositoryImpl.addCollection(
            "Collection"
        )
        val response = mainMovieRepositoryImpl.getCollectionByNameFlow(0)
        Assertions.assertEquals(
            movieDb,
            (response.lastOrNull() as LoadStateUI.Success).data.lastOrNull()
        )
    }
    @Test
    fun `should return error collection by name`() = runTest {
        mainMovieRepositoryImpl.addCollection(
            "Collection"
        )
        val response = mainMovieRepositoryImpl.getCollectionByNameFlow(1)
        Assertions.assertEquals(
            "error",
            (response.lastOrNull() as LoadStateUI.Error).throwable.message
        )
    }
    @Test
    fun `should return my collection all`() = runTest {
        mainMovieRepositoryImpl.addMovie(movieDb)
        val response =
            mainMovieRepositoryImpl.getAllMyMovies()
        Assertions.assertEquals(movieDb, response.first())
    }


//    @Test
//    fun `should return backend collection error`() = runTest {
//Mockito.`when`(api.getPremiers(  Year.now().toString().toInt(), Month.from(
//    Instant.now().atZone(ZoneId.systemDefault())
//).toString())).thenReturn()
//    }

}
val myMovieDb=
    MovieCollectionDB(
        1,
        null, "name", "nameEn",
        "maneOr", null, null, null, null,
        null, null, null, null,
        listOf(0),
    )
val movieDb=
            MovieDb(
                listOf(0),
                1,
                null, "name", "nameEn",
                "maneOr", null, null, null, null,
                null, null, null, null
            )
val searchMovie = SearchMovie(
    1, "name", "nameEng", null, null, null, null, listOf(),
    listOf(), null, null, null, null
)
val fakeSearchWrapperDto = SearchWrapperDto("key", 10, 100, listOf(searchMovie))
val fakeMovieBaseInfo = MovieBaseInfoDto(
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
        CountryDto(
            country = "США"
        ), CountryDto(
            country = "Великобритания"
        ), CountryDto(
            country = "Турция"
        )
    ),
    genres = listOf(
        GenreDto(
            genre = "драма"

        ), GenreDto(
            genre = "боевик"

        ), GenreDto(
            genre = "военный"

        ), GenreDto(
            genre = "история"
        )
    ),
    startYear = null,
    endYear = null,
    serial = false,
    shortFilm = false,
    completed = false
)
val staffDto = StaffDto(
    1, "nameru", "nameEng", "description",
    "poster", "profession", "key"
)
val movieGallery = MovieGalleryDto("url", "prevurl")
val serverSearchWrapperDtoGallery = ServerSearchWrapperDto(50, listOf(movieGallery))
val episode = EpisodesDto(1, 2, null, null, null, null)
val serialWrapperDto = SerialWrapperDto(2, listOf(episode))
val serverSearchWrapperDtoSerialWrapper = ServerSearchWrapperDto(50, listOf(serialWrapperDto))
val staffFullInfo = StaffFullInfoDto(
    personId = 1,
    webUrl = null,
    age = 10,
    birthPlace = null,
    nameRU = null,
    nameEN = null,
    sex = null,
    growth = 10,
    birthday = null,
    death = null,
    deathPlace = null,
    hasAwards = null,
    profession = null,
    facts = listOf(),
    spouses = listOf(),
    films = listOf(),
    posterUrl = null
)
val emptyMovieCard = MoviePremiereDto(
    kpID
    = 1234,
    nameRU = "MovieTitle",
    nameENG = "English Title",
    countries = listOf(),
    genre = listOf(GenreDto("action")),
    year = 2000,
    posterUrl = "null",
    prevPosterUrl = "null",
    duration = null,
    premiereRu = "super.premiereRu"
)
private val fakeMovieCollectionDto = MovieCollectionDto(
    kpID = 1234,
    imdbId = null,
    nameRU = "MovieTitle",
    nameENG = null,
    nameOriginal = null,
    countries = listOf(),
    genre = listOf(GenreDto("action")),
    ratingKP = 10.0f,
    ratingImdb = null,
    year = 2000,
    type = null,
    posterUrl = "null",
    prevPosterUrl = "null"
)
val fakeList = listOf(
    fakeMovieCollectionDto
)
val fakeMovieCollectionWrapperDto = MovieCollectionWrapperDto<MovieCollectionDto>(
    totalPages = 10,
    items = fakeList,
    total = 50,
)
private val fakeMovieCollection = MovieCollectionImpl(
    kpID = 1234,
    imdbId = null,
    nameRU = "MovieTitle",
    nameENG = null,
    nameOriginal = null,
    countries = listOf(),
    genre = listOf(object : Genre {

        override val genre: String
            get() = "action"
    }
    ),
    ratingKP = 10.0f,
    ratingImdb = null,
    year = 2000,
    type = null,
    posterUrl = null,
    prevPosterUrl = null
)
val similarMovieDto = SimilarMovieDto(
    1,
    "Runame", "Enname", "orName", "poster", "prevPoster", "relation"
)
val serverSearchWrapperDtoSimilar = ServerSearchWrapperDto(50, listOf(similarMovieDto))
val listFakeMovieCollection = listOf(fakeMovieCollection)
