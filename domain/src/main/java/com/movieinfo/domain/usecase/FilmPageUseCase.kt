package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.entity.MovieGallery
import com.movieinfo.domain.entity.MyMovieCollections
import com.movieinfo.domain.entity.SerialWrapper
import com.movieinfo.domain.entity.Staff
import com.movieinfo.domain.repository.MainPageRepository
import javax.inject.Inject

class FilmPageUseCase @Inject constructor(private val mainPageRepository: MainPageRepository) {

     suspend fun getCollectionsName(): List<MyMovieCollections> {
        return mainPageRepository.getMyCollections()
    }

     suspend fun getCollectionById(collectionId: Int): List<MovieCollection> {
        return mainPageRepository.getCollectionByName(collectionId)
    }

     suspend fun getSeasons(id: Int): List<SerialWrapper> {
        return mainPageRepository.getSeasons(id)
    }

     suspend fun getMovieCollectionsId(kpId: Int): List<Int> {
        return mainPageRepository.getMovieCollectionId(kpId) ?: emptyList()
    }



     suspend fun getMovieById(id: Int): MovieBaseInfo? {
        return mainPageRepository.getMovieById(id)
    }

     suspend fun getStaffByFilmId(id: Int): List<Staff> {
        return mainPageRepository.getStaffByFilmId(id)
    }

     suspend fun getMovieGallery(id: Int, galleryType: GalleryType): List<MovieGallery> {
        return mainPageRepository.getMovieGallery(id, galleryType)
    }
     suspend fun getSimilarMovie(id: Int): List<MovieCollection>{
        return mainPageRepository.getSimilarMovie(id)
//            .map {
//            object : MovieCollection {
//                override val kpID: Int
//                    get() = it.filmID
//                override val imdbId: String?
//                    get() = null
//                override val nameRU: String?
//                    get() = it.nameRU
//                override val nameENG: String?
//                    get() = it.nameENG
//                override val nameOriginal: String?
//                    get() = it.nameOriginal
//                override val countries: List<CountryDto>?
//                    get() = null
//                override val genre: List<GenreDto>?
//                    get() = null
//                override val raitingKP: Float?
//                    get() = null
//                override val raitingImdb: Float?
//                    get() = null
//                override val year: Int?
//                    get() = null
//                override val type: String?
//                    get() = null
//                override val posterUrl: String?
//                    get() = it.posterUrl
//                override val prevPosterUrl: String?
//                    get() = it.prevPosterUrl
//
//            }
//        }
    }
     suspend fun getMovieFromDB(kpId: Int): MovieDb?{
        return mainPageRepository.getMovieFromDB(kpId)
    }

    suspend fun addToCollection(movieToCollection: MovieDb) {
        mainPageRepository.addMovie(movieCollectionDB = movieToCollection)
    }

}