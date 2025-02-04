package com.movieinfo.data.repository.storage.models

import com.movieinfo.domain.entity.Country
import com.movieinfo.domain.entity.Genre
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieForStaff
import com.movieinfo.domain.entity.Spouses
import com.movieinfo.domain.entity.StaffFullInfo

data class MovieCollectionImpl(
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
    override val genres: List<Genre>,
    override val startYear: Int?=null,
    override val endYear: Int?=null,
    override val serial: Boolean?=null,
    override val shortFilm: Boolean?=null,
    override val completed: Boolean?=null
) : MovieBaseInfo

data class StaffFullInfoImpl(
    override val personId: Int,
    override val webUrl: String?,
    override val nameRU: String?,
    override val nameEN: String?,
    override val sex: String?,
    override val posterUrl: String?,
    override val growth: Int,
    override val birthday: String?,
    override val death: String?,
    override val age: Int,
    override val birthPlace: String?,
    override val deathPlace: String?,
    override val hasAwards: Int?,
    override val profession: String?,
    override val facts: List<String?>,
    override val spouses: List<Spouses>,
    override val films: List<MovieForStaff>
) : StaffFullInfo