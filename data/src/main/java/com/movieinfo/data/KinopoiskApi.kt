package com.movieinfo.data

import com.movieinfo.data.moviesDto.MoviePremiereDto
import com.movieinfo.data.moviesDto.MovieBaseInfoDto
import com.movieinfo.data.moviesDto.MovieCollectionDto
import com.movieinfo.data.moviesDto.MovieCollectionWrapperDto
import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.data.moviesDto.MovieGalleryDto
import com.movieinfo.data.moviesDto.SearchWrapperDto
import com.movieinfo.data.moviesDto.SerialWrapperDto
import com.movieinfo.data.moviesDto.ServerSearchWrapperDto
import com.movieinfo.data.moviesDto.SimilarMovieDto
import com.movieinfo.data.moviesDto.StaffDto
import com.movieinfo.data.moviesDto.StaffFullInfoDto
import com.movieinfo.domain.entity.CollectionType
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremiers(
        @Query("year")
        year: Int,
        @Query("month")
        month: String
    ): ServerSearchWrapperDto<MoviePremiereDto>?

@GET("/api/v2.1/films/search-by-keyword")
suspend fun getSearchByKeyWord(
    @Query("keyword")
    keyword: String,
    @Query("page")
    page: Int = 1): SearchWrapperDto

    @GET("/api/v2.2/films/collections")
    suspend fun getCollectionMovies(
        @Query("type")
        type: CollectionType,
        @Query("page")
        page: Int = 1
    ): MovieCollectionWrapperDto<MovieCollectionDto>

    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun getSerialSeasons(
        @Path("id")
        id: Int
    ): ServerSearchWrapperDto<SerialWrapperDto>

    @GET("/api/v2.2/films/{id}")
    suspend fun getMovieById(
        @Path("id")
        id: Int
    ): MovieBaseInfoDto?

    @GET("/api/v2.2/films/{id}/similars")
   suspend fun getSimilarMovie(
        @Path("id")
        id: Int
    ): ServerSearchWrapperDto<SimilarMovieDto>

    @GET("/api/v2.2/films/{id}/images")
    suspend fun getMovieGallery(
        @Path("id")
        id: Int,
        @Query("type")
        galleryType: GalleryType
    ): ServerSearchWrapperDto<MovieGalleryDto>

    @GET("/api/v1/staff")
    suspend fun getStaffByFilmId(
        @Query("filmId")
        filmId: Int
    ): List<StaffDto>?

    @GET("/api/v1/staff/{id}")
    suspend fun getStaffById(
        @Path("id")
        id: Int
    ): StaffFullInfoDto

    @GET("/api/v2.2/films")
    suspend fun getSearchByFilters(
        @Query("countries")
        countries: Array<Int>?,
        @Query("genres")
        genres: Array<Int>?,
        @Query("order")
        order: String?,
        @Query("type")
        type: String?,
        @Query("ratingFrom")
        ratingFrom: Int?,
        @Query("ratingTo")
        ratingTo: Int?,
        @Query("yearFrom")
        yearFrom: Int?,
        @Query("yearTo")
        yearTo: Int?,
        @Query("keyword")
        keyword: String?,
        @Query("page")
        page: Int
    ): MovieCollectionWrapperDto<MovieCollectionDto>
}