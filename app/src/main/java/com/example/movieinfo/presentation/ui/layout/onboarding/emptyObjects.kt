package com.example.movieinfo.presentation.ui.layout.onboarding

import com.example.movieinfo.presentation.ui.layout.MovieCollectionRow
import com.movieinfo.data.repository.storage.models.StaffFullInfoImpl
import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.Country
import com.movieinfo.domain.entity.Episode
import com.movieinfo.domain.entity.Genre
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieForStaff
import com.movieinfo.domain.entity.MovieGallery
import com.movieinfo.domain.entity.Staff
import com.movieinfo.domain.entity.StaffFullInfo
import com.movieinfo.domain.models.LoadStateUI
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow


data class MainPage(val collectionsList: List<MovieCollectionRow>)

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

val dragonEpisode = object : Episode {
    override val seasonNumber: Int
        get() = 1
    override val episodeNumber: Int
        get() = 1
    override val nameRu: String
        get() = "Наследники дракона"
    override val nameEng: String
        get() = "The Heirs of the Dragon"
    override val synopsys: String
        get() = "Визерис — справедливый правитель Семи королевств. " +
                "После того как он понес утрату, " +
                "Малый совет пытается решить вопрос с наследником."
    override val releasedDate: String
        get() = "2022-08-22"
}
val emptyEpisode = object : Episode {
    override val seasonNumber: Int
        get() = 1
    override val episodeNumber: Int
        get() = 1
    override val nameRu: String?
        get() = null
    override val nameEng: String?
        get() = null
    override val synopsys: String?
        get() = null
    override val releasedDate: String?
        get() = null
}
val dragonEpisodeList = listOf(
    dragonEpisode, dragonEpisode, dragonEpisode, dragonEpisode,
    dragonEpisode, dragonEpisode, dragonEpisode, dragonEpisode
)
val staffMovie = object : MovieForStaff {
    override val filmId: Int
        get() = 4414587
    override val nameRU: String
        get() = "Министерство неджентльменских дел"
    override val nameENG: String
        get() = "The Ministry of Ungentlemanly Warfare"
    override val rating: String
        get() = "7.3"
    override val general: Boolean
        get() = false
    override val description: String
        get() = ""
    override val professionKey: String
        get() = "ACTOR"
}
val emptyStaffFullInfo = StaffFullInfoImpl(
    0, null, null, null, null, null,
    0, null, null, 0, null, null,
    null, null, emptyList(), emptyList(), listOf(staffMovie, staffMovie)
) as StaffFullInfo

val emptyMovieCard = object : MovieCollection {
    override val kpID: Int
        get() = 1234
    override val imdbId: String?
        get() = null
    override val nameRU: String?
        get() = "MovieTitle"
    override val nameENG: String?
        get() = null
    override val nameOriginal: String?
        get() = null
    override val countries: List<Country>?
        get() = listOf()
    override val genre: List<Genre>?
        get() = listOf(object : Genre {

            override val genre: String
                get() = "action"
        }
        )
    override val ratingKP: Float?
        get() = 10.0f
    override val ratingImdb: Float?
        get() = null
    override val year: Int?
        get() = 2000
    override val type: String?
        get() = null
    override val posterUrl: String?
        get() = null
    override val prevPosterUrl: String?
        get() = null
}
val collectionRow = MovieCollectionRow(movieCards =
object : StateFlow<LoadStateUI<List<MovieCollection>>> {
    override val replayCache: List<LoadStateUI<List<MovieCollection>>>
        get() = TODO("Not yet implemented")

    override val value: LoadStateUI<List<MovieCollection>> = LoadStateUI.Success(listOf(
        emptyMovieCard,
        emptyMovieCard,
        emptyMovieCard,
        emptyMovieCard,
        emptyMovieCard,
        emptyMovieCard,
        emptyMovieCard,
        emptyMovieCard,
        emptyMovieCard,
        emptyMovieCard
    ))

    override suspend fun collect(collector: FlowCollector<LoadStateUI<List<MovieCollection>>>): Nothing {
        TODO("Not yet implemented")
    }

}, "Premiers", CollectionType.PREMIERES)
val mainPage = MainPage(listOf(collectionRow, collectionRow, collectionRow, collectionRow))
val actorCardForPreview = object : Staff {
    override val staffId: Int
        get() = 34227
    override val nameRU: String
        get() = "Генри Кавилл"
    override val nameENG: String
        get() = "Henry Cavill"
    override val description: String
        get() = "Gus March-Phillips"
    override val posterUrl: String
        get() = "https://st.kp.yandex.net/images/actor_iphone/iphone360_34227.jpg"
    override val professionText: String
        get() = "Актеры"
    override val professionKey: String
        get() = "ACTOR"

}
val actorsForPreview = listOf(
    actorCardForPreview,
    actorCardForPreview,
    actorCardForPreview,
    actorCardForPreview,
    actorCardForPreview,
    actorCardForPreview,
    actorCardForPreview,
    actorCardForPreview,
    actorCardForPreview,
    actorCardForPreview,
    actorCardForPreview,
)
val galleryItem = object : MovieGallery {
    override val imageUrl: String
        get() = "https://avatars.mds.yandex.net/get-kinopoisk-image/9784475/11c1689c-9ba1-42c5-b5e7-77fe62a65daa/orig"
    override val previewUrl: String
        get() = "https://avatars.mds.yandex.net/get-kinopoisk-image/9784475/11c1689c-9ba1-42c5-b5e7-77fe62a65daa/300x"
}

val filmographyForPreview = listOf(
    staffMovie, staffMovie, staffMovie, staffMovie, staffMovie,
    staffMovie, staffMovie, staffMovie, staffMovie, staffMovie, staffMovie, staffMovie
)
val galleryForPreview = listOf(
    galleryItem, galleryItem, galleryItem, galleryItem, galleryItem,
    galleryItem, galleryItem, galleryItem, galleryItem
)