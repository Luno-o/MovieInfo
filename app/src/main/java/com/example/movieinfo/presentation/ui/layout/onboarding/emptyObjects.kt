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


val emptyMovieBaseInfo = object : MovieBaseInfo {
    override val kpID: Int
        get() = 4414587
    override val kpHdId: String?
        get() = "4a0e81d5d0584c52b61baaa6e1d1ff71"
    override val ImdbId: String?
        get() = "tt5177120"
    override val nameRU: String?
        get() = "Министерство неджентльменских дел"
    override val nameENG: String?
        get() = null
    override val nameOriginal: String?
        get() = "The Ministry of Ungentlemanly Warfare"
    override val posterUrl: String
        get() = "https://kinopoiskapiunofficial.tech/images/posters/kp/4414587.jpg"
    override val prevPosterUrl: String
        get() = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/4414587.jpg"
    override val coverUrl: String
        get() = "https://avatars.mds.yandex.net/get-ott/224348/2a0000018f3e89a200cf1fd5a46d92eb7fc5/orig"
    override val logoUrl: String?
        get() = null
    override val reviewsCount: Int
        get() = 6
    override val ratingGoodReview: Float?
        get() = 17.0f
    override val ratingGoodReviewVoteCount: Int?
        get() = 1
    override val ratingKinopoisk: Float?
        get() = 7.1f
    override val ratingKinopoiskVoteCount: Int
        get() = 6099
    override val ratingImdb: Float?
        get() = 7.3f
    override val ratingImdbVoteCount: Int
        get() = 8721
    override val ratingFilmCritics: Float?
        get() = 6.6f
    override val ratingFilmCriticsVoteCount: Int
        get() = 31
    override val ratingAwait: Float?
        get() = 99.0f
    override val ratingAwaitCount: Int
        get() = 19970
    override val ratingRfCritics: Float?
        get() = null
    override val ratingRfCriticsVoteCount: Int
        get() = 0
    override val webUrl: String
        get() = "https://www.kinopoisk.ru/film/4414587/"
    override val year: Int?
        get() = 2024
    override val filmLength: Int?
        get() = 120
    override val slogan: String?
        get() = null
    override val description: String
        get() = "1942 год, Великобритания. Они—лучшие излучших. Отпетые авантюристы и первоклассные спецы, привыкшие действовать в одиночку. Но когда на кону стоит судьба всего мира, им приходится объединиться в сверхсекретное боевое подразделение и отправиться на выполнение дерзкой миссии против нацистов. Теперь их дело — война, и вести они её будут совершенно не по-джентльменски."
    override val shortDescription: String
        get() = "Отряду авантюристов поручают невыполнимую миссию. Новый фильм Гая Ричи и продюсера «Пиратов Карибского моря»"
    override val editorAnnotation: String
        get() = "В мае на Кинопоиске"
    override val isTicketsAvailable: Boolean
        get() = false
    override val productionStatus: String?
        get() = null
    override val type: String?
        get() = "FILM"
    override val ratingMpaa: String
        get() = "r"
    override val ratingAgeLimits: String
        get() = "age18"
    override val hasImax: Boolean
        get() = false
    override val has3D: Boolean
        get() = false
    override val lastSync: String
        get() = "2024-05-06T18:07:16.522463"
    override val countries: List<Country>
        get() = listOf(object : Country {
            override val country: String
                get() = "США"

        },
            object : Country {
                override val country: String
                    get() = "Великобритания"

            },
            object : Country {
                override val country: String
                    get() = "Турция"

            })
    override val genres: List<Genre>
        get() = listOf(object : Genre {
            override val genre: String
                get() = "драма"

        },
            object : Genre {
                override val genre: String
                    get() = "боевик"

            },
            object : Genre {
                override val genre: String
                    get() = "военный"

            },
            object : Genre {
                override val genre: String
                    get() = "история"

            })
    override val startYear: Int?
        get() = null
    override val endYear: Int?
        get() = null
    override val serial: Boolean
        get() = false
    override val shortFilm: Boolean
        get() = false
    override val completed: Boolean
        get() = false

}
val dragonEpisode = object : Episode {
    override val seasonNumber: Int
        get() = 1
    override val episodeNumber: Int
        get() = 1
    override val nameRu: String?
        get() = "Наследники дракона"
    override val nameEng: String?
        get() = "The Heirs of the Dragon"
    override val synopsys: String?
        get() = "Визерис — справедливый правитель Семи королевств. " +
                "После того как он понес утрату, " +
                "Малый совет пытается решить вопрос с наследником."
    override val releasedDate: String?
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
val emptyStaffFullInfo = StaffFullInfoImpl(
    0, null, null, null, null, null,
    0, null, null, 0, null, null,
    null, null, emptyList(), emptyList(), emptyList()
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
    override val raitingKP: Float?
        get() = 10.0f
    override val raitingImdb: Float?
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
    override val nameRU: String?
        get() = "Генри Кавилл"
    override val nameENG: String?
        get() = "Henry Cavill"
    override val description: String?
        get() = "Gus March-Phillips"
    override val posterUrl: String?
        get() = "https://st.kp.yandex.net/images/actor_iphone/iphone360_34227.jpg"
    override val professionText: String?
        get() = "Актеры"
    override val professionKey: String?
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
val staffMovie = object : MovieForStaff {
    override val filmId: Int
        get() = 4414587
    override val nameRU: String?
        get() = "Министерство неджентльменских дел"
    override val nameENG: String?
        get() = "The Ministry of Ungentlemanly Warfare"
    override val rating: String?
        get() = "7.3"
    override val general: Boolean
        get() = false
    override val description: String?
        get() = ""
    override val professionKey: String?
        get() = "ACTOR"
}
val filmographyForPreview = listOf(
    staffMovie, staffMovie, staffMovie, staffMovie, staffMovie,
    staffMovie, staffMovie, staffMovie, staffMovie, staffMovie, staffMovie, staffMovie
)
val galleryForPreview = listOf(
    galleryItem, galleryItem, galleryItem, galleryItem, galleryItem,
    galleryItem, galleryItem, galleryItem, galleryItem
)