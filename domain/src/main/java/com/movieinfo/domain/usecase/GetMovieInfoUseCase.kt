//package com.movieinfo.domain.usecase
//
//import com.movieinfo.domain.entity.CollectionType
//import com.movieinfo.domain.entity.GalleryType
//import com.movieinfo.domain.entity.MovieBaseInfo
//import com.movieinfo.domain.entity.MovieCollection
//import com.movieinfo.domain.entity.MovieGallery
//import com.movieinfo.domain.entity.SimilarMovie
//import com.movieinfo.domain.entity.Staff
//import com.movieinfo.domain.entity.StaffFullInfo
//
//
//class GetMovieInfoUseCase (
//    private val movieRepository: MovieRepository
//) {
//
//    suspend fun getPremieres(): List<MovieCard>{
//        return movieRepository.getPremiers()
//    }
//    suspend fun getSearchByKeyWord(key: String,page: Int): List<MovieBaseInfo>{
//        return if (key.isNotEmpty()){
//         movieRepository.getSearchByKeyWord(key,page)
//        }else emptyList()
//    }
//
//
//
//suspend fun deleteHistory(collectionId: Int){
//   val collection =  movieRepository.getCollectionByName(collectionId)
//collection.forEach { movie->
//    if (movie.collectionID.size == 1){
//        movieRepository.removeMovie(movie)
//    }else{
//        val newCollection : MutableList<Int> = mutableListOf()
//        newCollection.addAll(movie.collectionID)
//        newCollection.remove(collectionId)
//        movieRepository.updateMovie(movie.copy(collectionID = newCollection))
//    }
//}
//
//}
//
//    suspend fun getCollection(type: CollectionType, page: Int = 1): List<MovieCollection>{
//        return movieRepository.getCollection(type,page)
//    }
//
//    suspend fun getMovieById(id: Int): MovieBaseInfo {
//        return movieRepository.getMovieById(id)
//    }
//    suspend fun getMovieFromDB(kpId: Int): MovieCollectionDB?{
//        return movieRepository.getMovieFromDB(kpId)
//    }
//
//    suspend fun getSeasons(id: Int):List<SerialWrapperDto>{
//        return movieRepository.getSeasons(id)
//    }
//    suspend fun getSimilarMovie(id: Int): List<SimilarMovie>{
//        return movieRepository.getSimilarMovie(id)
//    }
//    suspend fun getMovieGallery(id: Int,galleryType: GalleryType): List<MovieGallery> {
//        return movieRepository.getMovieGallery(id, galleryType)
//    }
//    suspend fun getStaffByFilmId(id: Int): List<Staff> {
//        return movieRepository.getStaffByFilmId(id)
//    }
//    suspend fun getStaffById(id: Int): StaffFullInfo {
//        return movieRepository.getStaffById(id)
//    }
//    suspend fun getSearchByFilter(   countries: Array<Int>?,
//                                     genres: Array<Int>?,
//                                     order: String?,
//                                     type: String?,
//                                     ratingFrom: Int?,
//                                     ratingTo: Int?,
//                                     yearFrom: Int?,
//                                     yearTo: Int?,
//                                     keyword: String?,
//                                     page: Int): List<MovieCollection>{
//        return movieRepository.
//        getSearchByFilters(countries,genres, order,
//            type, ratingFrom, ratingTo, yearFrom, yearTo,
//            keyword, page)
//    }
//
//
//    suspend fun addToCollection(movieCollectionDB: MovieCollectionDB) {
//movieRepository.addMovie(movieCollectionDB)
//    }
//    suspend fun removeFromCollection(movieCollectionDB: MovieCollectionDB){
//        movieRepository.removeMovie(movieCollectionDB)
//    }
//    suspend fun getCollectionById(collectionId: Int):List<MovieCollectionDB>{
//    return    movieRepository.getCollectionByName(collectionId)
//    }
//    fun getCollectionByIdFlow(collectionId: Int):Flow<List<MovieCollectionDB>>{
//        return    movieRepository.getCollectionByNameFlow(collectionId)
//    }
//    suspend fun getCollectionsName(): List<MyMovieCollections>{
//        return movieRepository.getMyCollections()
//    }
//    suspend fun getAllCollections(): List<MovieCollectionDB>{
//        return movieRepository.getAllCollections()
//    }
//    suspend fun addCollection(name: String){
//        movieRepository.addCollection(name)
//    }
//}