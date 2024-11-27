package com.example.movieinfo.entity

import com.example.movieinfo.data.moviesDto.CountryDto
import com.example.movieinfo.data.moviesDto.GenreDto

data class YourCollection(val collectionName: String, val collection: List<MovieCollection>)
interface Country {
    val country: String
}
interface Episode {
    val seasonNumber: Int
    val episodeNumber: Int
    val nameRu: String?
    val nameEng: String?
    val synopsys: String?
    val releasedDate: String?
}
interface Genre {
    val  genre: String
}
interface  MovieBaseInfo{
    val kpID: Int
    val kpHdId: String?
    val ImdbId : String?
    val nameRU: String?
    val nameENG: String?
    val nameOriginal: String?
    val posterUrl: String?
    val prevPosterUrl: String?
    val coverUrl: String?
    val logoUrl: String?
    val reviewsCount: Int?
    val ratingGoodReview: Float?
    val ratingGoodReviewVoteCount: Int?
    val ratingKinopoisk: Float?
    val ratingKinopoiskVoteCount: Int?
    val ratingImdb: Float?
    val ratingImdbVoteCount: Int?
    val ratingFilmCritics: Float?
    val ratingFilmCriticsVoteCount: Int?
    val ratingAwait: Float?
    val ratingAwaitCount: Int?
    val ratingRfCritics: Float?
    val ratingRfCriticsVoteCount: Int?
    val webUrl: String?
    val year: Int?
    val filmLength: Int?
    val slogan: String?
    val description: String?
    val shortDescription: String?
    val editorAnnotation: String?
    val isTicketsAvailable: Boolean?
    val productionStatus: String?
    val type: String?
    val ratingMpaa: String?
    val ratingAgeLimits: String?
    val hasImax: Boolean?
    val has3D: Boolean?
    val lastSync: String?
    val countries: List<Country>
    val genreDtos: List<Genre>
    val startYear: Int?
    val endYear: Int?
    val serial: Boolean?
    val shortFilm: Boolean?
    val completed: Boolean?
}
fun MovieBaseInfo.convert(): MovieCollectionImp{
    return MovieCollectionImp(
        kpID = kpID,
        imdbId = null,
        nameRU = nameRU,
        nameENG = nameENG,
        nameOriginal = nameOriginal,
        countries = countries.map { CountryDto(it.country) },
        genreDtos = genreDtos.map { GenreDto(it.genre) },
        raitingKP = ratingKinopoisk?:0.0f,
        raitingImdb = ratingImdb?:0.0f,
        year = year,
        type = type,
        posterUrl = posterUrl,
        prevPosterUrl = prevPosterUrl
    )
}
class MovieBaseInfoImp(
    override val kpID: Int,
    override val kpHdId: String? =null,
    override val ImdbId: String?=null,
    override val nameRU: String?=null,
    override val nameENG: String?=null,
    override val nameOriginal: String?=null,
    override val posterUrl: String?=null,
    override val prevPosterUrl: String?=null,
    override val coverUrl: String?=null,
    override val logoUrl: String?=null,
    override val reviewsCount: Int?=null,
    override val ratingGoodReview: Float?=null,
    override val ratingGoodReviewVoteCount: Int?=null,
    override val ratingKinopoisk: Float?=null,
    override val ratingKinopoiskVoteCount: Int?=null,
    override val ratingImdb: Float?=null,
    override val ratingImdbVoteCount: Int?=null,
    override val ratingFilmCritics: Float?=null,
    override val ratingFilmCriticsVoteCount: Int?=null,
    override val ratingAwait: Float?=null,
    override val ratingAwaitCount: Int?=null,
    override val ratingRfCritics: Float?=null,
    override val ratingRfCriticsVoteCount: Int?=null,
    override val webUrl: String?=null,
    override val year: Int?=null,
    override val filmLength: Int?=null,
    override val slogan: String?=null,
    override val description: String?=null,
    override val shortDescription: String?=null,
    override val editorAnnotation: String?=null,
    override val isTicketsAvailable: Boolean?=null,
    override val productionStatus: String?=null,
    override val type: String?=null,
    override val ratingMpaa: String?=null,
    override val ratingAgeLimits: String?=null,
    override val hasImax: Boolean?=null,
    override val has3D: Boolean?=null,
    override val lastSync: String?=null,
    override val countries: List<Country>,
    override val genreDtos: List<Genre>,
    override val startYear: Int?=null,
    override val endYear: Int?=null,
    override val serial: Boolean?=null,
    override val shortFilm: Boolean?=null,
    override val completed: Boolean?=null
) : MovieBaseInfo
interface MovieForStaff {
    val filmId: Int
    val nameRU: String?
    val nameENG: String?
    val rating: String?
    val general: Boolean
    val description: String?
    val professionKey: String?
}

interface MovieGallery {
    val imageUrl: String
    val previewUrl: String
}
interface MoviePremiere {
    val kpID: Int
    val nameRU: String
    val nameENG: String
    val year: Int
    val posterUrl: String
    val prevPosterUrl: String
    val countries: List<CountryDto>
    val genreDtos: List<GenreDto>
    val duration: Int?
    val premiereRu: String
}
interface SimilarMovie {
    val filmID: Int
    val nameRU: String
    val nameENG: String?
    val nameOriginal: String?
    val posterUrl: String
    val prevPosterUrl: String
    val relation: String
}

interface Spouses {
    val personId: Int
    val name: String?
    val divorced: Boolean
    val divorcedReason: String
    val sex: String
    val children: Int
    val webUrl: String
    val relation: String
}
interface Staff {
    val staffId: Int
    val nameRU: String?
    val nameENG: String?
    val description: String?
    val posterUrl: String?
    val professionText: String?
    val professionKey: String?
}
interface StaffFullInfo {
    val personId: Int
    val webUrl: String?
    val nameRU: String?
    val nameEN: String?
    val sex: String?
    val posterUrl: String?
    val growth: Int
    val birthday: String?
    val death: String?
    val age: Int
    val birthPlace: String?
    val deathPlace: String?
    val hasAwards: Int?
    val profession: String?
    val facts: List<String?>
    val spouses: List<Spouses>
    val films: List<MovieForStaff>
}