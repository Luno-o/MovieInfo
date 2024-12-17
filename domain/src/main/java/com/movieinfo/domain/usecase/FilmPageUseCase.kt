package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.repository.MainMovieRepository
import javax.inject.Inject

class GetFilmUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {

    suspend operator fun invoke(id: Int) =
         mainMovieRepository.getMovieById(id)

}

class GetSeasonsUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {
    suspend operator fun invoke(id: Int) =
        mainMovieRepository.getSeasons(id)
}

class GetMovieCollectionsIdUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {
    suspend operator fun invoke(kpId: Int) =
         mainMovieRepository.getMovieCollectionId(kpId) ?: emptyList()

}

class GetStaffByFilmUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {
    suspend operator fun invoke(id: Int) =
         mainMovieRepository.getStaffByFilmId(id)

}

class GetMovieGalleryFlowUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {

    suspend operator fun invoke(id: Int, galleryType: GalleryType) =
         mainMovieRepository.getMovieGalleryFlow(id, galleryType)

}
class GetMovieGalleryUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {

    suspend operator fun invoke(id: Int, galleryType: GalleryType) =
        mainMovieRepository.getMovieGallery(id, galleryType)

}

class GetMovieFromDbUseCase @Inject constructor(private val mainMovieRepository: MainMovieRepository) {
    suspend operator fun invoke(kpId: Int): MovieDb? {
        return mainMovieRepository.getMovieFromDB(kpId)
    }
}

class AddToMyCollectionUseCase @Inject constructor(
    private val mainMovieRepository: MainMovieRepository
) {

    suspend operator fun invoke(movieBaseInfo: MovieBaseInfo?, id: Int) {
        val newCollectionList = mutableListOf<Int>()
        movieBaseInfo?.let {
            val movieToCollection = mainMovieRepository.getMovieFromDB(movieBaseInfo.kpID)
                ?.let { movieDb ->
                    if (!movieDb.collectionId.contains(id)) {
                        if (movieDb.collectionId.isNotEmpty()) {
                            newCollectionList.addAll(movieDb.collectionId)
                        newCollectionList.add(id)
                        }
                    }else{
                        newCollectionList.addAll(movieDb.collectionId)
                        newCollectionList.remove(id)
                    }
                    MovieDb(
                        collectionId = newCollectionList,
                        posterUrl = movieBaseInfo.posterUrl,
                        prevPosterUrl = movieBaseInfo.prevPosterUrl,
                        countries = movieBaseInfo.countries,
                        genre = movieBaseInfo.genres,
                        kpID = movieBaseInfo.kpID,
                        imdbId = movieBaseInfo.ImdbId,
                        nameRU = movieBaseInfo.nameRU,
                        nameENG = movieBaseInfo.nameENG,
                        nameOriginal = movieBaseInfo.nameOriginal,
                        raitingKP = movieBaseInfo.ratingKinopoisk,
                        raitingImdb = movieBaseInfo.ratingImdb,
                        year = movieBaseInfo.year,
                        type = movieBaseInfo.type
                    )
                }

            if (movieToCollection != null) {
                if (movieToCollection.collectionId.isEmpty()) {
                    mainMovieRepository.removeMovie(movieToCollection)
                } else {
                    mainMovieRepository.updateMovie(movieToCollection)
                }
            }else{
                mainMovieRepository.addMovie(
                    MovieDb(
                        collectionId = listOf(id),
                        posterUrl = movieBaseInfo.posterUrl,
                        prevPosterUrl = movieBaseInfo.prevPosterUrl,
                        countries = movieBaseInfo.countries,
                        genre = movieBaseInfo.genres,
                        kpID = movieBaseInfo.kpID,
                        imdbId = movieBaseInfo.ImdbId,
                        nameRU = movieBaseInfo.nameRU,
                        nameENG = movieBaseInfo.nameENG,
                        nameOriginal = movieBaseInfo.nameOriginal,
                        raitingKP = movieBaseInfo.ratingKinopoisk,
                        raitingImdb = movieBaseInfo.ratingImdb,
                        year = movieBaseInfo.year,
                        type = movieBaseInfo.type
                    )
                )
            }
        }

    }
}


