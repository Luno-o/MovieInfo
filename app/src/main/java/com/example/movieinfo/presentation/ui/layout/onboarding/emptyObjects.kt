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