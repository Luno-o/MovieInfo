package com.movieinfo.data.repository


import com.movieinfo.data.KinopoiskApi
import com.movieinfo.data.extensions.toMovieDb
import com.movieinfo.data.extensions.toMyMovieCollections
import com.movieinfo.data.extensions.toMyMovieDb
import com.movieinfo.data.repository.storage.models.MovieBaseInfoImp
import com.movieinfo.data.repository.storage.models.MovieCollectionImpl
import com.movieinfo.domain.repository.MainMovieRepository
import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.entity.MovieGallery
import com.movieinfo.domain.entity.MyMovieCollections
import com.movieinfo.domain.entity.SerialWrapper
import com.movieinfo.domain.entity.Staff
import com.movieinfo.domain.entity.StaffFullInfo
import com.movieinfo.data.repository.storage.MovieStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import org.threeten.bp.Instant
import org.threeten.bp.Month
import org.threeten.bp.Year
import org.threeten.bp.ZoneId

class MainMovieRepositoryImpl(private val movieStorage: MovieStorage,
                              private val api: KinopoiskApi
): MainMovieRepository {

    override suspend fun loadCollection(
        collectionType: CollectionType,
        page: Int
    ): List<MovieCollection> {
       return when(collectionType){
            CollectionType.PREMIERES->loadPremieres()
            else->{
            api.getCollectionMovies(collectionType,page).items
            }
        }
    }

    override suspend fun loadCollectionFlow(
        collectionType: CollectionType,
        page: Int
    ): Flow<List<MovieCollection>> {
        val collection = mutableListOf<List<MovieCollection>>()
        api.getCollectionMovies(collectionType,page).items.let { collection.add(it) }
        return collection.asFlow()
    }

    override suspend fun loadPremieres(): List<MovieCollection>{
            return api.getPremiers(
            Year.now().toString().toInt(), Month.from(
                Instant.now().atZone(ZoneId.systemDefault())
            ).toString()
        )?.items.orEmpty().map {
            MovieCollectionImpl(
                prevPosterUrl = it.prevPosterUrl,
                nameRU = it.nameRU, raitingKP = null,
                genre = it.genre, kpID = it.kpID, nameENG = it.nameENG,
                year = it.year, posterUrl = it.posterUrl, countries = it.countries,
                raitingImdb = null, nameOriginal = null, type = "FILM", imdbId = null
            )
        }

    }

    override suspend fun loadStaffById(id: Int): StaffFullInfo {
        return api.getStaffById(id)
    }

    override suspend fun getSeasons(id: Int): List<SerialWrapper> {
        return api.getSerialSeasons(id).items
    }

    override suspend fun getSimilarMovie(id: Int): List<MovieCollection> {
      return  api.getSimilarMovie(id).items.map {
          MovieCollectionImpl(
              kpID = it.filmID,
              imdbId = null,
              nameRU = it.nameRU,
              nameENG = it.nameENG,
              nameOriginal = it.nameOriginal,
              countries = null,
              genre = null,
              raitingKP = null,
              raitingImdb = null,
              year = null,
              type = null,
              posterUrl = it.posterUrl,
              prevPosterUrl = it.prevPosterUrl
          )
      }
    }

    override suspend fun getMovieGallery(id: Int, galleryType: GalleryType): List<MovieGallery> {
        return api.getMovieGallery(id, galleryType).items
    }

    override suspend fun getStaffByFilmId(id: Int): List<Staff> {
        return api.getStaffByFilmId(id) ?: emptyList()
    }

    override suspend fun getMovieById(id: Int): MovieBaseInfo? {
        return api.getMovieById(id)
    }


    override suspend fun getSearchByFilters(
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
        return api.getSearchByFilters(
            countries,
            genres, order, type, ratingFrom, ratingTo, yearFrom, yearTo, keyword, page
        ).items
    }

    override suspend fun getSearchByKeyWord(key: String, page: Int): List<MovieBaseInfo> {
        return api.getSearchByKeyWord(key,page).films.map {
            MovieBaseInfoImp(
                kpID = it.filmId,
                nameRU = it.nameRu,
                nameENG = it.nameEn,
                type = it.type,
                year = it.year,
                description = it.description,
                filmLength = null,
                countries = it.countries,
                genres = it.genres,
                ratingImdbVoteCount = it.ratingVoteCount,
                posterUrl = it.posterUrl,
                prevPosterUrl = it.posterUrlPreview

            )
        }
    }

    override suspend fun getMyCollections(): List<MyMovieCollections> {
        return movieStorage.getMyCollections().map { it.toMyMovieCollections() }
    }

    override suspend fun getCollectionById(collectionId: Int): List<MovieDb> {
        return movieStorage.getCollectionById(collectionId).map { it.toMovieDb() }
    }

    override suspend fun getMovieCollectionId(kpId: Int): List<Int>? {
        return movieStorage.getMovieCollectionId(kpId)
    }

    override suspend fun addCollection(name: String) {
        movieStorage.addCollection(name)
    }

    override suspend fun getMovieFromDB(kpId: Int): MovieDb? {
        return movieStorage.getMovieFromDB(kpId)?.toMovieDb()
    }

    override suspend fun addMovie(movieCollectionDB: MovieDb) {
        movieStorage.addMovie(movieCollectionDB.toMyMovieDb())
    }

    override suspend fun getCollectionByNameFlow(collectionId: Int): Flow<List<MovieDb>> {
        return movieStorage.getCollectionByNameFlow(collectionId).map {list-> list.map { it.toMovieDb() } }
    }

    override suspend fun getAllMyMovies(): List<MovieDb> {
        return movieStorage.getAllMyMovies().map { it.toMovieDb() }
    }

    override suspend fun removeMovie(movie: MovieDb) {
        movieStorage.removeMovie(movie.toMyMovieDb())
    }

    override suspend fun updateMovie(movie: MovieDb) {
        movieStorage.updateMovie(movie.toMyMovieDb())
    }
}