package com.movieinfo.domain.repository

import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.entity.MovieGallery
import com.movieinfo.domain.entity.MyMovieCollections
import com.movieinfo.domain.entity.SerialWrapper
import com.movieinfo.domain.entity.SimilarMovie
import com.movieinfo.domain.entity.Staff
import com.movieinfo.domain.entity.StaffFullInfo
import kotlinx.coroutines.flow.Flow


interface MainPageRepository {

     suspend fun loadCollection(
        collectionType: CollectionType,
        page: Int
    ): List<MovieCollection>?
    suspend fun loadCollectionFlow(
        collectionType: CollectionType,
        page: Int
    ): Flow<List<MovieCollection>>
     suspend fun loadPremieres(): Flow<List<MovieCollection>>
     suspend fun loadStaffById(id: Int): StaffFullInfo
     suspend fun getSeasons(id: Int): List<SerialWrapper>
    suspend fun getSimilarMovie(id: Int): List<MovieCollection>
    suspend fun getMovieGallery(id: Int, galleryType: GalleryType): List<MovieGallery>
    suspend fun getStaffByFilmId(id: Int): List<Staff>
     suspend fun getMovieById(id: Int): MovieBaseInfo?


     suspend fun getMyCollections(): List<MyMovieCollections>
     suspend fun getCollectionByName(collectionId: Int): List<MovieDb>
     suspend fun getMovieCollectionId(kpId: Int): List<Int>?
     suspend fun addCollection(name: String)
    suspend fun getMovieFromDB(kpId: Int): MovieDb?
    suspend fun addMovie(movieCollectionDB: MovieDb)
    suspend fun getCollectionByNameFlow(collectionId: Int): Flow<List<MovieDb>>
    suspend fun getAllCollections(): List<MovieDb>
     suspend fun removeMovie(movie: MovieDb)
    suspend fun updateMovie(movie: MovieDb)
    suspend fun getSearchByFilters(
        countries: Array<Int>?,
        genres: Array<Int>?,
        order: String?,
        type: String?,
        ratingFrom: Int?,
        ratingTo: Int?,
        yearFrom: Int?,
        yearTo: Int?,
        keyword: String?,
        page: Int
    ): List<MovieCollection>

    suspend fun getSearchByKeyWord(key: String, page: Int): List<MovieBaseInfo>
    suspend fun getCollection(type: CollectionType, page: Int): List<MovieCollection>
    suspend fun getPremiers(page: Int): List<MovieCollection>

}