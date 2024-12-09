package com.movieinfo.data.moviesDto

import com.movieinfo.data.db.MovieCollectionDB
import com.movieinfo.domain.entity.Country
import com.movieinfo.domain.entity.Episode
import com.movieinfo.domain.entity.Genre
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.entity.MovieForStaff
import com.movieinfo.domain.entity.MovieGallery
import com.movieinfo.domain.entity.MoviePremiere
import com.movieinfo.domain.entity.SerialWrapper
import com.movieinfo.domain.entity.SimilarMovie
import com.movieinfo.domain.entity.Spouses
import com.movieinfo.domain.entity.Staff
import com.movieinfo.domain.entity.StaffFullInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryDto(
    @Json(name = "country")
   override val country: String
) : Country

 class CountryImpl(
    override val country: String
) : Country
class GenreImpl(
    override val genre: String
) : Genre



@JsonClass(generateAdapter = true)
data class EpisodesDto(
    @Json(name = "seasonNumber")
    override val seasonNumber: Int,
    @Json(name = "episodeNumber")
    override val episodeNumber: Int,
    @Json(name = "nameRu")
    override val nameRu: String?,
    @Json(name = "nameEn")
    override val nameEng: String?,
    @Json(name = "synopsis")
    override val synopsys: String?,
    @Json(name = "releaseDate")
    override val releasedDate: String?): Episode

@JsonClass(generateAdapter = true)
data class GenreDto(
    @Json(name = "genre")
    override val  genre: String
): Genre


@JsonClass(generateAdapter = true)
data class MovieBaseInfoDto(
    @Json(name = "kinopoiskId")
    override val kpID: Int,
    @Json(name = "kinopoiskHDId")
    override val kpHdId: String?,
    @Json(name = "imdbId")
    override val ImdbId : String?,
    @Json(name = "nameRu")
    override val nameRU: String?,
    @Json(name = "nameEn")
    override val nameENG: String?,
    @Json(name = "nameOriginal")
    override val nameOriginal: String?,
    @Json(name = "posterUrl")
    override val posterUrl: String,
    @Json(name = "posterUrlPreview")
    override val prevPosterUrl: String,
    @Json(name = "coverUrl")
    override val coverUrl: String?,
    @Json(name = "logoUrl")
    override val logoUrl: String?,
    @Json(name = "reviewsCount")
    override val reviewsCount: Int,
    @Json(name = "ratingGoodReview")
    override val ratingGoodReview: Float?,
    @Json(name = "ratingGoodReviewVoteCount")
    override val ratingGoodReviewVoteCount: Int?,
    @Json(name = "ratingKinopoisk")
    override val ratingKinopoisk: Float?,
    @Json(name = "ratingKinopoiskVoteCount")
    override val ratingKinopoiskVoteCount: Int?,
    @Json(name = "ratingImdb")
    override val ratingImdb: Float?,
    @Json(name = "ratingImdbVoteCount")
    override val ratingImdbVoteCount: Int?,
    @Json(name = "ratingFilmCritics")
    override val ratingFilmCritics: Float?,
    @Json(name = "ratingFilmCriticsVoteCount")
    override val ratingFilmCriticsVoteCount: Int?,
    @Json(name = "ratingAwait")
    override val ratingAwait: Float?,
    @Json(name = "ratingAwaitCount")
    override val ratingAwaitCount: Int?,
    @Json(name = "ratingRfCritics")
    override val ratingRfCritics: Float?,
    @Json(name = "ratingRfCriticsVoteCount")
    override val ratingRfCriticsVoteCount: Int?,
    @Json(name = "webUrl")
    override val webUrl: String?,
    @Json(name = "year")
    override val year: Int?,
    @Json(name = "filmLength")
    override val filmLength: Int?,
    @Json(name = "slogan")
    override val slogan: String?,
    @Json(name = "description")
    override val description: String?,
    @Json(name = "shortDescription")
    override val shortDescription: String?,
    @Json(name = "editorAnnotation")
    override val editorAnnotation: String?,
    @Json(name = "isTicketsAvailable")
    override val isTicketsAvailable: Boolean?,
    @Json(name = "productionStatus")
    override val productionStatus: String?,
    @Json(name = "type")
    override val type: String?,
    @Json(name = "ratingMpaa")
    override val ratingMpaa: String?,
    @Json(name = "ratingAgeLimits")
    override val ratingAgeLimits: String?,
    @Json(name = "hasImax")
    override val hasImax: Boolean?,
    @Json(name = "has3D")
    override val has3D: Boolean?,
    @Json(name = "lastSync")
    override val lastSync: String?,
    @Json(name = "countries")
    override val countries: List<CountryDto>,
    @Json(name = "genres")
    override val genres: List<GenreDto>,
    @Json(name = "startYear")
    override val startYear: Int?,
    @Json(name = "endYear")
    override val endYear: Int?,
    @Json(name = "serial")
    override val serial: Boolean?,
    @Json(name = "shortFilm")
    override val shortFilm: Boolean?,
    @Json(name = "completed")
    override val completed: Boolean?
): MovieBaseInfo


@JsonClass(generateAdapter = true)
data class MovieCollectionDto(
    @Json(name = "kinopoiskId")
    override val kpID: Int,
    @Json(name = "imdbId")
    override val imdbId : String?,
    @Json(name = "nameRu")
    override val nameRU: String?,
    @Json(name = "nameEn")
    override val nameENG: String?,
    @Json(name = "nameOriginal")
    override val nameOriginal: String?,
    @Json(name = "countries")
    override val countries: List<CountryDto>,
    @Json(name = "genres")
    override val genre: List<GenreDto>,
    @Json(name= "ratingKinopoisk")
    override val raitingKP: Float?,
    @Json(name= "ratingImdb")
    override val raitingImdb: Float?,
    @Json(name = "year")
    override val year: Int?,
    @Json(name = "type")
    override val type: String?,
    @Json(name = "posterUrl")
    override val posterUrl: String,
    @Json(name = "posterUrlPreview")
    override val prevPosterUrl: String
): MovieCollection

@JsonClass(generateAdapter = true)
data class MovieCollectionWrapperDto<T>(
    @Json(name = "total")
    val total: Int,
    @Json(name = "totalPages")
    val totalPages: Int,
    @Json(name = "items")
    val items: List<T>
)
@JsonClass(generateAdapter = true)
data class MovieForStaffDto(
    @Json(name = "filmId")
    override val filmId: Int,
    @Json(name = "nameRu")
    override val nameRU: String?,
    @Json(name = "nameEn")
    override val nameENG: String?,
    @Json(name = "rating")
    override val rating: String?,
    @Json(name = "general")
    override val general: Boolean,
    @Json(name = "description")
    override val description: String?,
    @Json(name = "professionKey")
    override val professionKey: String?
): MovieForStaff

@JsonClass(generateAdapter = true)
data class MovieGalleryDto(
    @Json(name = "imageUrl")
    override val imageUrl: String,
    @Json(name = "previewUrl")
    override val previewUrl: String
): MovieGallery


@JsonClass(generateAdapter = true)
data class MoviePremiereDto(
    @Json(name = "kinopoiskId")
    override val kpID: Int,
    @Json(name = "nameRu")
    override val nameRU: String,
    @Json(name = "nameEn")
    override val nameENG: String,
    @Json(name = "year")
    override val year: Int,
    @Json(name = "posterUrl")
    override val posterUrl: String,
    @Json(name = "posterUrlPreview")
    override val prevPosterUrl: String,
    @Json(name = "countries")
    override val countries: List<CountryDto>,
    @Json(name = "genres")
    override val genre: List<GenreDto>,
    @Json(name = "duration")
    override val duration: Int?,
    @Json(name = "premiereRu")
    override val premiereRu: String
): MoviePremiere

@JsonClass(generateAdapter = true)
data class SerialWrapperDto(
    @Json(name = "number")
    override val number: Int,
    @Json(name = "episodes")
    override val episodes: List<EpisodesDto>
): SerialWrapper

@JsonClass(generateAdapter = true)
data class ServerSearchWrapperDto<T>(
    @Json(name = "total")
    val total: Int,
    @Json(name = "items")
    val items: List<T>
)
@JsonClass(generateAdapter = true)
data class SearchWrapperDto(
    @Json(name = "keyword")
    val keyword: String,
    @Json(name = "pagesCount")
    val pagesCount: Int,
    @Json(name = "searchFilmsCountResult")
    val searchFilmsCountResult : Int,
    @Json(name = "films")
    val films: List<SearchMovie>
)
@JsonClass(generateAdapter = true)
data class SearchMovie(
    @Json(name = "filmId")
    val filmId : Int,
    @Json(name = "nameRu")
    val nameRu : String?,
    @Json(name = "nameEn")
    val nameEn: String?,
    @Json(name = "type")
    val type : String?,
    @Json(name = "year")
    val year : Int?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "filmLength")
    val filmLength : String?,
    @Json(name = "countries")
    val countries: List<CountryDto>,
    @Json(name = "genres")
    val genres: List<GenreDto>,
    @Json(name = "rating")
    val rating : String?,
    @Json(name = "ratingVoteCount")
    val ratingVoteCount: Int?,
    @Json(name = "posterUrl")
    val posterUrl: String?,
    @Json(name = "posterUrlPreview")
    val posterUrlPreview: String?
)

@JsonClass(generateAdapter = true)
data class SimilarMovieDto(
    @Json(name = "filmId")
    override val filmID: Int,
    @Json(name = "nameRu")
    override val nameRU: String,
    @Json(name = "nameEn")
    override val nameENG: String?,
    @Json(name = "nameOriginal")
    override val nameOriginal: String?,
    @Json(name = "posterUrl")
    override val posterUrl: String,
    @Json(name = "posterUrlPreview")
    override val prevPosterUrl: String,
    @Json(name = "relationType")
    override val relation: String
): SimilarMovie

@JsonClass(generateAdapter = true)
data class SpousesDto (
    @Json(name = "personId")
    override val personId: Int,
    @Json(name = "name")
    override val name: String?,
    @Json(name = "divorced")
    override val divorced: Boolean,
    @Json(name = "divorcedReason")
    override val divorcedReason: String,
    @Json(name = "sex")
    override val sex: String,
    @Json(name = "children")
    override val children: Int,
    @Json(name = "webUrl")
    override val webUrl: String,
    @Json(name = "relation")
    override val relation: String,
): Spouses

@JsonClass(generateAdapter = true)
data class StaffDto(
    @Json(name = "staffId")
    override val staffId: Int,
    @Json(name = "nameRu")
    override val nameRU: String?,
    @Json(name = "nameEn")
    override val nameENG: String?,
    @Json(name = "description")
    override val description: String?,
    @Json(name = "posterUrl")
    override val posterUrl: String?,
    @Json(name = "professionText")
    override val professionText: String?,
    @Json(name = "professionKey")
    override val professionKey: String?
): Staff

@JsonClass(generateAdapter = true)
data class StaffFullInfoDto (
    @Json(name = "personId")
    override val personId: Int,
    @Json(name = "webUrl")
    override val webUrl: String?,
    @Json(name = "nameRu")
    override val nameRU: String?,
    @Json(name = "nameEn")
    override val nameEN: String?,
    @Json(name = "sex")
    override val sex: String?,
    @Json(name = "posterUrl")
    override val posterUrl: String?,
    @Json(name = "growth")
    override val growth: Int,
    @Json(name = "birthday")
    override val birthday: String?,
    @Json(name = "death")
    override val death: String?,
    @Json(name = "age")
    override val age: Int,
    @Json(name = "birthplace")
    override val birthPlace: String?,
    @Json(name = "deathplace")
    override val deathPlace: String?,
    @Json(name = "hasAwards")
    override val hasAwards: Int?,
    @Json(name = "profession")
    override val profession: String?,
    @Json(name = "facts")
    override val facts: List<String?>,
    @Json(name = "spouses")
    override val spouses: List<SpousesDto>,
    @Json(name = "films")
    override val films: List<MovieForStaffDto>,
): StaffFullInfo

fun movieCollectionDbtoMovieDb(movieCollectionDB: MovieCollectionDB): MovieDb {
    return MovieDb(
        collectionId = movieCollectionDB.collectionId,
        kpID = movieCollectionDB.kpID,
        imdbId = movieCollectionDB.imdbId,
        nameRU = movieCollectionDB.nameRU,
        nameENG = movieCollectionDB.nameENG,
        nameOriginal = movieCollectionDB.nameOriginal,
        countries = movieCollectionDB.countries?.let { listOf(CountryImpl(it)) } ?: emptyList(),
        genre = movieCollectionDB.genreDtos?.let { listOf(GenreImpl(it)) } ?: emptyList(),
        raitingKP = movieCollectionDB.raitingKP,
        raitingImdb = movieCollectionDB.raitingImdb,
        year = movieCollectionDB.year,
        type = movieCollectionDB.type,
        posterUrl = movieCollectionDB.posterUrl,
        prevPosterUrl = movieCollectionDB.prevPosterUrl
    )
}
fun movieDbToMovieCollectionDb(movieDb: MovieDb): MovieCollectionDB {
    return MovieCollectionDB(
        collectionId = movieDb.collectionId,
        kpID = movieDb.kpID,
        imdbId = movieDb.imdbId,
        nameRU = movieDb.nameRU,
        nameENG = movieDb.nameENG,
        nameOriginal = movieDb.nameOriginal,
        countries = movieDb.countries?.first()?.country,
        genreDtos = movieDb.genre?.first()?.genre,
        raitingKP = movieDb.raitingKP,
        raitingImdb = movieDb.raitingImdb,
        year = movieDb.year,
        type = movieDb.type,
        posterUrl = movieDb.posterUrl,
        prevPosterUrl = movieDb.prevPosterUrl
    )
}