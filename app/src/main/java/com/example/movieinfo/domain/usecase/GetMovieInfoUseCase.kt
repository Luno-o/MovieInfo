package com.example.movieinfo.domain.usecase

import com.example.movieinfo.data.MovieRepository
import com.example.movieinfo.data.db.MovieCollectionDB
import com.example.movieinfo.data.db.MyMovieCollections
import com.example.movieinfo.data.moviesDto.SerialWrapperDto
import com.example.movieinfo.entity.GalleryType
import com.example.movieinfo.entity.MovieBaseInfo
import com.example.movieinfo.entity.MovieCollection
import com.example.movieinfo.entity.MovieGallery

import com.example.movieinfo.entity.CollectionType
import com.example.movieinfo.entity.SimilarMovie
import com.example.movieinfo.entity.Staff
import com.example.movieinfo.entity.StaffFullInfo
import com.example.movieinfo.presentation.ui.layout.MovieCard
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class GetMovieInfoUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend fun getPremieres(): List<MovieCard>{
        return movieRepository.getPremiers()
    }
    suspend fun getSearchByKeyWord(key: String,page: Int): List<MovieBaseInfo>{
        return if (key.isNotEmpty()){
         movieRepository.getSearchByKeyWord(key,page)
        }else emptyList()
    }



suspend fun deleteHistory(collectionId: Int){
   val collection =  movieRepository.getCollectionByName(collectionId)
collection.forEach { movie->
    if (movie.collectionID.size == 1){
        movieRepository.removeMovie(movie)
    }else{
        val newCollection : MutableList<Int> = mutableListOf()
        newCollection.addAll(movie.collectionID)
        newCollection.remove(collectionId)
        Timber.d("new collections $newCollection without $collectionId")
        movieRepository.updateMovie(movie.copy(collectionID = newCollection))
    }
}

}

    suspend fun getCollection(type: CollectionType,page: Int = 1): List<MovieCollection>{
        return movieRepository.getCollection(type,page)
    }

    suspend fun getMovieById(id: Int): MovieBaseInfo{
        return movieRepository.getMovieById(id)
    }
    suspend fun getMovieFromDB(kpId: Int): MovieCollectionDB?{
        return movieRepository.getMovieFromDB(kpId)
    }

    suspend fun getSeasons(id: Int):List<SerialWrapperDto>{
        return movieRepository.getSeasons(id)
    }
    suspend fun getSimilarMovie(id: Int): List<SimilarMovie>{
        return movieRepository.getSimilarMovie(id)
    }
    suspend fun getMovieGallery(id: Int,galleryType: GalleryType): List<MovieGallery> {
        return movieRepository.getMovieGallery(id, galleryType)
    }
    suspend fun getStaffByFilmId(id: Int): List<Staff> {
        return movieRepository.getStaffByFilmId(id)
    }
    suspend fun getStaffById(id: Int): StaffFullInfo {
        return movieRepository.getStaffById(id)
    }
    suspend fun getSearchByFilter(   countries: Array<Int>?,
                                     genres: Array<Int>?,
                                     order: String?,
                                     type: String?,
                                     ratingFrom: Int?,
                                     ratingTo: Int?,
                                     yearFrom: Int?,
                                     yearTo: Int?,
                                     keyword: String?,
                                     page: Int): List<MovieCollection>{
        return movieRepository.
        getSearchByFilters(countries,genres, order,
            type, ratingFrom, ratingTo, yearFrom, yearTo,
            keyword, page)
    }


    suspend fun addToCollection(movieCollectionDB: MovieCollectionDB) {
movieRepository.addMovie(movieCollectionDB)
    }
    suspend fun removeFromCollection(movieCollectionDB: MovieCollectionDB){
        movieRepository.removeMovie(movieCollectionDB)
    }
    suspend fun getCollectionById(collectionId: Int):List<MovieCollectionDB>{
    return    movieRepository.getCollectionByName(collectionId)
    }
    fun getCollectionByIdFlow(collectionId: Int):Flow<List<MovieCollectionDB>>{
        return    movieRepository.getCollectionByNameFlow(collectionId)
    }
    suspend fun getCollectionsName(): List<MyMovieCollections>{
        return movieRepository.getMyCollections()
    }
    suspend fun getAllCollections(): List<MovieCollectionDB>{
        return movieRepository.getAllCollections()
    }
    suspend fun addCollection(name: String){
        movieRepository.addCollection(name)
    }
}