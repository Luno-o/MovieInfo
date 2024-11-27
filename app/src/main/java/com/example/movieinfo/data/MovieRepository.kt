package com.example.movieinfo.data

import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.movieinfo.data.db.Database
import com.example.movieinfo.data.db.MovieCollectionDB
import com.example.movieinfo.data.db.MyMovieCollections
import com.example.movieinfo.data.moviesDto.SerialWrapperDto
import com.example.movieinfo.entity.GalleryType
import com.example.movieinfo.entity.MovieBaseInfo
import com.example.movieinfo.entity.MovieCollection
import com.example.movieinfo.entity.MovieGallery
import com.example.movieinfo.entity.CollectionType
import com.example.movieinfo.entity.MovieBaseInfoImp
import com.example.movieinfo.entity.SimilarMovie
import com.example.movieinfo.entity.Staff
import com.example.movieinfo.entity.StaffFullInfo
import com.example.movieinfo.presentation.ui.layout.MovieCard
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.Instant
import org.threeten.bp.Month
import org.threeten.bp.Year
import org.threeten.bp.ZoneId
import javax.inject.Inject

class MovieRepository @Inject constructor() {
    private val movieDao = Database.instance.movieDBDao()
    private val movieCollectionsNameDao = Database.instance.movieCollectionsNameDao()

    suspend fun getPremiers(): List<MovieCard> {
        return Network.getKinopoiskApi.getPremiers(
            Year.now().toString().toInt(), Month.from(
                Instant.now().atZone(ZoneId.systemDefault())
            ).toString()
        )?.items.orEmpty().map {
            MovieCard(
                prevPosterUrl = it.prevPosterUrl,
                nameRU = it.nameRU, raitingKP = null,
                genreDtos = it.genreDtos, kpID = it.kpID, nameENG = it.nameENG,
                year = it.year, posterUrl = it.posterUrl, countries = it.countries,
                raitingImdb = null, nameOriginal = null, type = null, imdbId = null
            )
        }
    }

    suspend fun deleteHistory(collectionId: Int){
        movieDao.deleteHistory(collectionId)
    }

    suspend fun getSearchByKeyWord(key: String,page: Int): List<MovieBaseInfo> {
        return Network.getKinopoiskApi.getSearchByKeyWord(key,page).films.map {
            MovieBaseInfoImp(
                kpID = it.filmId,
                nameRU = it.nameRu,
                nameENG = it.nameEn,
                type = it.type,
                year = it.year,
                description = it.description,
                filmLength = null,
                countries = it.countries,
                genreDtos = it.genres,
                ratingImdbVoteCount = it.ratingVoteCount,
                posterUrl = it.posterUrl,
                prevPosterUrl = it.posterUrlPreview

            )
        }
    }

    suspend fun getCollection(type: CollectionType,page: Int): List<MovieCollection> {
        return Network.getKinopoiskApi.getCollectionMovies(type,page).items
    }

    suspend fun getMovieById(id: Int): MovieBaseInfo {
        return Network.getKinopoiskApi.getMovieById(id)
    }
    suspend fun getMovieFromDB(kpId: Int): MovieCollectionDB?{
        return movieDao.getMovieDB(kpId)
    }

    suspend fun getSeasons(id: Int): List<SerialWrapperDto> {
        return Network.getKinopoiskApi.getSerialSeasons(id).items
    }

    suspend fun getSimilarMovie(id: Int): List<SimilarMovie> {
        return Network.getKinopoiskApi.getSimilarMovie(id).items
    }

    suspend fun getMovieGallery(id: Int, galleryType: GalleryType): List<MovieGallery> {
        return Network.getKinopoiskApi.getMovieGallery(id, galleryType).items
    }

    suspend fun getStaffByFilmId(id: Int): List<Staff> {
        return Network.getKinopoiskApi.getStaffByFilmId(id)
    }

    suspend fun getStaffById(id: Int): StaffFullInfo {
        return Network.getKinopoiskApi.getStaffById(id)
    }

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
    ): List<MovieCollection> {
        return Network.getKinopoiskApi.getSearchByFilters(
            countries,
            genres, order, type, ratingFrom, ratingTo, yearFrom, yearTo, keyword, page
        ).items
    }

    suspend fun addMovie(movieCollectionDB: MovieCollectionDB) {
        movieDao.insertMovieDB(movieCollectionDB)
    }


    suspend fun updateMovie(movieCollectionDB: MovieCollectionDB) {
        movieDao.updateMovieDB(movieCollectionDB)
    }

    suspend fun removeMovie(movieCollectionDB: MovieCollectionDB) {
        movieDao.removeMovie(movieCollectionDB)
    }

    suspend fun getCollectionByName(collectionId: Int): List<MovieCollectionDB> {
        return movieCollectionsNameDao.getMoviesByCollectionId(collectionId.toString())
    }
    fun getCollectionByNameFlow(collectionId: Int): Flow<List<MovieCollectionDB>> {
        return movieCollectionsNameDao.getMoviesByCollectionIdFlow(collectionId.toString())
    }

    suspend fun getMyCollections(): List<MyMovieCollections> {
        return movieCollectionsNameDao.getAllCollectionNames()
    }

    suspend fun getAllCollections(): List<MovieCollectionDB> {
        return movieDao.getAllMoviesDB()
    }

    suspend fun addCollection(name: String) {

        movieCollectionsNameDao.insertCollection(MyMovieCollections(0, name))
    }


}
