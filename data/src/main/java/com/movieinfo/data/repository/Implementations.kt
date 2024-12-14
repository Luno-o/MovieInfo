package com.movieinfo.data.repository

import com.movieinfo.data.repository.storage.models.MyMovieDb
import com.movieinfo.domain.entity.MyMovieCollections

class MyMovieCollectionsImpl(override val id: Int,
                         override val collectionName: String): MyMovieCollections
class MyMovieDbImpl(
    override val collectionId: List<Int>,
    override val kpID: Int,
    override val imdbId: String?,
    override val nameRU: String?,
    override val nameENG: String?,
    override val nameOriginal: String?,
    override val countries: String?,
    override val genre: String?,
    override val ratingKP: Float?,
    override val ratingImdb: Float?,
    override val year: Int?,
    override val type: String?,
    override val posterUrl: String?,
    override val prevPosterUrl: String?
): MyMovieDb