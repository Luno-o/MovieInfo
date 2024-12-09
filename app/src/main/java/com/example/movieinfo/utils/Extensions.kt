package com.example.movieinfo.utils

import com.movieinfo.data.repository.storage.models.MovieCollectionImpl
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.entity.SearchMovieFilter

fun MovieBaseInfo.convert(): MovieCollectionImpl {
    return MovieCollectionImpl(
        kpID = kpID,
        imdbId = null,
        nameRU = nameRU,
        nameENG = nameENG,
        nameOriginal = nameOriginal,
        countries = countries,
        genre = genres,
        raitingKP = ratingKinopoisk ?: 0.0f,
        raitingImdb = ratingImdb ?: 0.0f,
        year = year,
        type = type,
        posterUrl = posterUrl,
        prevPosterUrl = prevPosterUrl
    )
}


fun movieDBToMovieCollection(movieDB: MovieDb): MovieCollection {
    return MovieCollectionImpl(
        posterUrl = movieDB.posterUrl,
        prevPosterUrl = movieDB.prevPosterUrl,
        countries = movieDB.countries,
        genre = movieDB.genre,
        kpID = movieDB.kpID,
        imdbId = movieDB.imdbId,
        nameRU = movieDB.nameRU,
        nameENG = movieDB.nameENG,
        nameOriginal = movieDB.nameOriginal,
        raitingKP = movieDB.raitingKP,
        raitingImdb = movieDB.raitingImdb,
        year = movieDB.year,
        type = movieDB.type
    )
}

data class SearchMovieFilterImpl(
    override var countryInd: Array<Int> = arrayOf(0),
    override var genreInd: Array<Int> = arrayOf(0),
    override var sortType: String = "YEAR",
    override var movieType: String = "ALL",
    override var raitingFrom: Int = 1,
    override var raitingTo: Int = 10,
    override var yearBefore: Int = 1998,
    override var yearAfter: Int = 2024,
    override var queryState: String = ""

) : SearchMovieFilter

