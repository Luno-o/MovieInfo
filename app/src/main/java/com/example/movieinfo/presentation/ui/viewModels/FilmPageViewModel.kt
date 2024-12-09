package com.example.movieinfo.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieinfo.utils.movieDBToMovieCollection
import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.entity.MovieGallery
import com.movieinfo.domain.entity.MyMovieCollections
import com.movieinfo.domain.entity.SerialWrapper
import com.movieinfo.domain.entity.Staff
import com.movieinfo.domain.usecase.FilmPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FilmPageViewModel @Inject constructor(private val useCase: FilmPageUseCase) : ViewModel() {

    private val _movie = MutableStateFlow<MovieBaseInfo?>(null)
    val movie = _movie.asStateFlow()
    private val _seasons = MutableStateFlow<List<SerialWrapper>>(listOf())
    val seasons = _seasons.asStateFlow()
    private val _movieToBase = MutableStateFlow<MovieBaseInfo?>(null)
    private val _staffByFilm = MutableStateFlow<List<Staff>>(emptyList())
    val staffByFilm = _staffByFilm.asStateFlow()
    private val _movieGallery = MutableStateFlow<List<MovieGallery>>(emptyList())
    val movieGallery = _movieGallery.asStateFlow()
    private val _similarMovies = MutableStateFlow<List<MovieCollection>>(emptyList())
    val similarMovies = _similarMovies.asStateFlow()
    val collectionsIdForMovie = MutableStateFlow(emptyList<Int>())
    val isMyFavourite = MutableStateFlow(false)
    val isMyWatched = MutableStateFlow(false)
    val isMyBookmark = MutableStateFlow(false)
    var collectionsList = MutableStateFlow(emptyList<MyMovieCollections>())
    private val _yourCollections = MutableStateFlow<List<List<MovieCollection>>>(emptyList())
    private val _movieGalleryAll =
        MutableStateFlow<List<Pair<GalleryType, List<MovieGallery>>>>(emptyList())
    val movieGalleryAll = _movieGalleryAll.asStateFlow()
    private val _yourCollection = MutableStateFlow<List<MovieCollection>>(emptyList())

    private suspend fun getCollectionsList() {
        val deferred = viewModelScope.async {
            useCase.getCollectionsName()
        }.await()
        collectionsList.value = deferred
        loadYourCollections(deferred)
    }

    private suspend fun loadYourCollections(list: List<MyMovieCollections>) {
        val fullList = mutableListOf<List<MovieCollection>>()
        list.forEach { myCollection ->
            fullList.add(useCase.getCollectionById(myCollection.id))
        }
        _yourCollections.value = fullList
    }

    suspend fun loadSeasons(id: Int) {
        viewModelScope.launch {
            _seasons.value = useCase.getSeasons(id)
        }
    }

    suspend fun getCollectionsIdForMovie(kpId: Int) {
        collectionsIdForMovie.value = useCase.getMovieCollectionsId(kpId)
        checkMyFavourite()
        checkMyWatched()
        checkMyBookmark()
    }

    private fun checkMyFavourite() {
        if (collectionsList.value.isEmpty()) {
            runBlocking {
                getCollectionsList()
            }
        }
        val id = collectionsList.value.find { it.collectionName == "Избранное" }?.id
        isMyFavourite.value = collectionsIdForMovie.value.contains(id)
    }

    private fun checkMyBookmark() {
        if (collectionsList.value.isEmpty()) {
            runBlocking {
                getCollectionsList()
            }
        }
        val id = collectionsList.value.find { it.collectionName == "Закладки" }?.id
        isMyBookmark.value = collectionsIdForMovie.value.contains(id)
    }

    private fun checkMyWatched() {
        if (collectionsList.value.isEmpty()) {
            runBlocking {
                getCollectionsList()
            }
        }
        val id = collectionsList.value.find { it.collectionName == "Просмотрено" }?.id
        isMyWatched.value = collectionsIdForMovie.value.contains(id)
    }


    suspend fun loadMovieById(id: Int) {
        viewModelScope.launch {
            _movie.value = useCase.getMovieById(id)
        }
    }

    suspend fun loadStaffByFilmId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _staffByFilm.value = useCase.getStaffByFilmId(id)
        }
    }

    suspend fun loadMovieGallery(id: Int, galleryType: GalleryType = GalleryType.SHOOTING) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieGallery.value = useCase.getMovieGallery(id, galleryType)
        }
    }

    suspend fun loadMovieGalleryAll(id: Int) {
        val list = mutableListOf<Pair<GalleryType, List<MovieGallery>>>()
        viewModelScope.launch(Dispatchers.IO) {
            GalleryType.entries.forEach { galleryType ->
                list.add(Pair(galleryType, useCase.getMovieGallery(id, galleryType)))
                delay(100)
            }
            _movieGalleryAll.value = list

        }
    }

    suspend fun loadSimilarMovie(id: Int) {
        viewModelScope.launch {
            _similarMovies.value = useCase.getSimilarMovie(id)
        }
    }

    fun addToMyCollection(id: Int) {
        viewModelScope.launch {
            _movieToBase.value = _movie.value
            val newCollectionList = mutableListOf<Int>()
            val movieToCollection =
                _movieToBase.value?.let {
                    useCase.getMovieFromDB(_movieToBase.value!!.kpID)
                        ?.let { movieDb ->
                            if (!movieDb.collectionId.contains(id)) {
                                if (movieDb.collectionId.isNotEmpty()) {
                                    newCollectionList.addAll(movieDb.collectionId)
                                }
                                newCollectionList.add(id)
                            }

                        }
                    updateMovieDb(newCollectionList, it)
                }
            if (movieToCollection != null) {
                useCase.addToCollection(movieToCollection)
                movie.value?.let { getCollectionsIdForMovie(it.kpID) }
                getCollectionsList()
            }

        }
    }

    fun removeFromMyCollection(id: Int) {
        viewModelScope.launch {
            _movieToBase.value = _movie.value
            val newCollectionList = mutableListOf<Int>()
            val movieToCollection =
                _movieToBase.value?.let {
                    useCase.getMovieFromDB(_movieToBase.value!!.kpID)
                        ?.let { movieDb ->
                            if (movieDb.collectionId.isNotEmpty()) {
                                newCollectionList.addAll(movieDb.collectionId)
                            }
                        }
                    if (newCollectionList.contains(id)) newCollectionList.remove(id)
                    updateMovieDb(newCollectionList, it)
                }
            if (movieToCollection != null) {
                useCase.addToCollection(movieToCollection)
                movie.value?.let { getCollectionsIdForMovie(it.kpID) }
                getCollectionsList()
                val newColl = mutableListOf<MovieCollection>()
                newColl.addAll(_yourCollection.value)
                newColl.remove(movieDBToMovieCollection(movieToCollection))
                _yourCollection.value = newColl
            }

        }
    }

    private fun updateMovieDb(
        collectionId: List<Int>,
        movieBaseInfo: MovieBaseInfo
    ): MovieDb {
        return MovieDb(
            collectionId = collectionId,
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

    val tabListGallery = listOf(
        "Кадры", "Со съемок", "Постеры",
        "Фан-арты",
        "Промо", "Концепт-арты",
        "Обои", "Обложки", "Скриншоты"
    )

    companion object {
        fun provideFactory(
            useCase: FilmPageUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FilmPageViewModel(useCase) as T
            }
        }
    }
}