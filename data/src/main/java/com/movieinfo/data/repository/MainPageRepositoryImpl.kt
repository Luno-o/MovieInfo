package com.movieinfo.data.repository


import com.movieinfo.data.KinopoiskApi
import com.movieinfo.data.db.Database
import com.movieinfo.data.db.MovieCollectionDB
import com.movieinfo.data.db.MyMovieCollectionsDb
import com.movieinfo.data.moviesDto.movieCollectionDbtoMovieDb
import com.movieinfo.data.moviesDto.movieDbToMovieCollectionDb
import com.movieinfo.data.repository.storage.models.MovieBaseInfoImp
import com.movieinfo.data.repository.storage.models.MovieCollectionImpl
import com.movieinfo.domain.repository.MainPageRepository
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import org.threeten.bp.Instant
import org.threeten.bp.Month
import org.threeten.bp.Year
import org.threeten.bp.ZoneId
import timber.log.Timber

class MainPageRepositoryImpl(private val api: KinopoiskApi): MainPageRepository {
    private val movieDao = Database.instance.movieDBDao()
    private val movieCollectionsNameDao = Database.instance.movieCollectionsNameDao()

    override suspend fun loadCollection(
        collectionType: CollectionType,
        page: Int
    ): List<MovieCollection> {
        return api.getCollectionMovies(collectionType,page).items
    }

    override suspend fun loadCollectionFlow(
        collectionType: CollectionType,
        page: Int
    ): Flow<List<MovieCollection>> {
        val collection = mutableListOf<List<MovieCollection>>()
        api.getCollectionMovies(collectionType,page).items.let { collection.add(it) }
        return collection.asFlow()
    }

    override suspend fun loadPremieres(): Flow<List<MovieCollection>>{
         val collection = mutableListOf<List<MovieCollection>>()

             api.getPremiers(
            Year.now().toString().toInt(), Month.from(
                Instant.now().atZone(ZoneId.systemDefault())
            ).toString()
        )?.items.orEmpty().map {
            MovieCollectionImpl(
                prevPosterUrl = it.prevPosterUrl,
                nameRU = it.nameRU, raitingKP = null,
                genre = it.genre, kpID = it.kpID, nameENG = it.nameENG,
                year = it.year, posterUrl = it.posterUrl, countries = it.countries,
                raitingImdb = null, nameOriginal = null, type = null, imdbId = null
            )
        }.let { collection.add(it) }
       return collection.asFlow()
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

    override suspend fun getMyCollections(): List<MyMovieCollections> {
        return movieCollectionsNameDao.getAllCollectionNames()
    }

    override suspend fun getCollectionByName(collectionId: Int): List<MovieDb> {
        return movieCollectionsNameDao.getMoviesByCollectionId(collectionId.toString()).map {
            movieCollectionDbtoMovieDb(it)
        }
    }

    override suspend fun getMovieCollectionId(kpId: Int): List<Int>? {
        return movieDao.getMovieDB(kpId)?.collectionId
    }

    override suspend fun addCollection(name: String) {
        movieCollectionsNameDao.insertCollection(MyMovieCollectionsDb(0, name))
    }

    override suspend fun getMovieFromDB(kpId: Int): MovieDb? {
        return movieDao.getMovieDB(kpId)?.let { movieCollectionDbtoMovieDb(it) }
    }

    override suspend fun addMovie(movieCollectionDB: MovieDb) {
        movieDao.insertMovieDB(movieDbToMovieCollectionDb(movieCollectionDB))
    }

    override suspend fun getCollectionByNameFlow(collectionId: Int): Flow<List<MovieDb>> {

         val flow = movieCollectionsNameDao.getMoviesByCollectionIdFlow(collectionId.toString()).map {
             it.map {it2-> movieCollectionDbtoMovieDb(it2) }
         }
        return flow
    }

    override suspend fun getAllCollections(): List<MovieDb> {
        return movieDao.getAllMoviesDB().map { movieCollectionDbtoMovieDb(it) }
    }

    override suspend fun removeMovie(movie: MovieDb) {
        movieDao.removeMovie(movieDbToMovieCollectionDb( movie))
    }

    override suspend fun updateMovie(movie: MovieDb) {
        movieDao.updateMovieDB(movieDbToMovieCollectionDb( movie))
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

    override suspend fun getCollection(type: CollectionType, page: Int): List<MovieCollection> {
        return api.getCollectionMovies(type,page).items
    }

    override suspend fun getPremiers(page: Int): List<MovieCollection> {
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
                raitingImdb = null, nameOriginal = null, type = null, imdbId = null
            )
        }
    }


}