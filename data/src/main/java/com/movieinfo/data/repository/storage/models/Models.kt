package com.movieinfo.data.repository.storage.models

interface MyCollections{
    val id: Int
    val collectionName: String
}

interface MyMovieDb {
    val collectionId: List<Int>
    val kpID: Int
    val imdbId: String?
    val nameRU: String?
    val nameENG: String?
    val nameOriginal: String?
    val countries: String?
    val genre: String?
    val ratingKP: Float?
    val ratingImdb: Float?
    val year: Int?
    val type: String?
    val posterUrl: String?
    val prevPosterUrl: String?
}