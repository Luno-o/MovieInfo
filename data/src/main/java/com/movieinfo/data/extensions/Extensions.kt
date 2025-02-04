package com.movieinfo.data.extensions

import com.movieinfo.data.db.MovieCollectionDB
import com.movieinfo.data.moviesDto.CountryImpl
import com.movieinfo.data.moviesDto.GenreImpl
import com.movieinfo.data.repository.MyMovieCollectionsImpl
import com.movieinfo.data.repository.MyMovieDbImpl
import com.movieinfo.data.repository.storage.models.MyCollections
import com.movieinfo.data.repository.storage.models.MyMovieDb
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.entity.MyMovieCollections

fun MyMovieDb.toMovieCollectionDb(): MovieCollectionDB{
    return MovieCollectionDB(this.kpID,
        this.imdbId,this.nameRU,this.nameENG,this.nameOriginal,
        this.countries,this.genre,this.ratingKP,this.ratingImdb,
        this.year,this.type,this.posterUrl,this.prevPosterUrl,
        this.collectionId)
}
fun MyCollections.toMyMovieCollections(): MyMovieCollections{
    return MyMovieCollectionsImpl(this.id,this.collectionName)
}
fun MyMovieDb.toMovieDb(): MovieDb{
    return MovieDb(this.collectionId,this.kpID,this.imdbId,this.nameRU,this.nameENG,this.nameOriginal,
        this.countries?.let { listOf(CountryImpl(it)) },
        this.genre?.let { listOf(GenreImpl(it)) },
        this.ratingKP,this.ratingImdb,this.year,this.type,
        this.posterUrl,this.prevPosterUrl
        )
}
fun MovieDb.toMyMovieDb(): MyMovieDb{
    return MyMovieDbImpl(this.collectionId,this.kpID,this.imdbId,
        this.nameRU,this.nameENG,this.nameOriginal,this.countries?.firstOrNull()?.country,this.genre?.firstOrNull()?.genre,
        this.ratingKP,this.ratingImdb,this.year,this.type,this.posterUrl,this.prevPosterUrl)
}