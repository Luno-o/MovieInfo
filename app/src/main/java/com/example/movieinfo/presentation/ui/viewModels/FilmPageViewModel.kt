package com.example.movieinfo.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.entity.MovieBaseInfo
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieGallery
import com.movieinfo.domain.entity.MyMovieCollections
import com.movieinfo.domain.entity.SerialWrapper
import com.movieinfo.domain.entity.Staff
import com.movieinfo.domain.usecase.AddToMyCollectionUseCase
import com.movieinfo.domain.usecase.GetCollectionByNameUseCase
import com.movieinfo.domain.usecase.GetFilmUseCase
import com.movieinfo.domain.usecase.GetMovieCollectionsIdUseCase
import com.movieinfo.domain.usecase.GetMovieGalleryUseCase
import com.movieinfo.domain.usecase.GetMyCollectionsUseCase
import com.movieinfo.domain.usecase.GetSeasonsUseCase
import com.movieinfo.domain.usecase.GetSimilarCollectionUseCase
import com.movieinfo.domain.usecase.GetStaffByFilmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FilmPageViewModel @Inject constructor(
    private val getFilmUseCase: GetFilmUseCase,
    private val getSeasonsUseCase: GetSeasonsUseCase,
    private val getMovieCollectionsIdUseCase: GetMovieCollectionsIdUseCase,
    private val getStaffByFilmUseCase: GetStaffByFilmUseCase,
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase,
    private val addToMyCollectionUseCase: AddToMyCollectionUseCase,
    private val getMyCollectionsUseCase: GetMyCollectionsUseCase,
    private val getCollectionByNameUseCase: GetCollectionByNameUseCase,
    private val getSimilarCollectionUseCase: GetSimilarCollectionUseCase
) : ViewModel() {

    private val _movie = MutableStateFlow<MovieBaseInfo?>(null)
    val movie = _movie.asStateFlow()
    private val _seasons = MutableStateFlow<List<SerialWrapper>>(listOf())
    val seasons = _seasons.asStateFlow()

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

    private suspend fun getCollectionsList() {
        val deferred = viewModelScope.async {
            getMyCollectionsUseCase.execute()
        }.await()
        collectionsList.value = deferred
        loadYourCollections(deferred)
    }

    private suspend fun loadYourCollections(list: List<MyMovieCollections>) {
        val fullList = mutableListOf<List<MovieCollection>>()
        list.forEach { myCollection ->
            fullList.add(getCollectionByNameUseCase.execute(myCollection.collectionName))
        }
        _yourCollections.value = fullList
    }

    suspend fun loadSeasons(id: Int) {
        viewModelScope.launch {
            _seasons.value = getSeasonsUseCase.execute(id)
        }
    }

    suspend fun getCollectionsIdForMovie(kpId: Int) {
        collectionsIdForMovie.value = getMovieCollectionsIdUseCase.execute(kpId)
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
            _movie.value = getFilmUseCase.execute(id)
        }
    }

    suspend fun loadStaffByFilmId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _staffByFilm.value = getStaffByFilmUseCase.execute(id)
        }
    }

    suspend fun loadMovieGallery(id: Int, galleryType: GalleryType = GalleryType.SHOOTING) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieGallery.value = getMovieGalleryUseCase.execute(id, galleryType)
        }
    }

    suspend fun loadMovieGalleryAll(id: Int) {
        val list = mutableListOf<Pair<GalleryType, List<MovieGallery>>>()
        viewModelScope.launch(Dispatchers.IO) {
            GalleryType.entries.forEach { galleryType ->
                list.add(Pair(galleryType, getMovieGalleryUseCase.execute(id, galleryType)))
                delay(100)
            }
            _movieGalleryAll.value = list

        }
    }

    suspend fun loadSimilarMovie(id: Int) {
        viewModelScope.launch {
            _similarMovies.value = getSimilarCollectionUseCase.execute(id)
        }
    }

    fun addRemoveToMyCollection(id: Int) {
        viewModelScope.launch {
                addToMyCollectionUseCase.execute(_movie.value,id)
                _movie.value?.let { getCollectionsIdForMovie(it.kpID) }
                getCollectionsList()
            Timber.d(" ViewModel add to collection film ${_movie.value?.nameRU} collection id $id " +
                    " collections id ${collectionsIdForMovie.value}")
            }
        }


    val tabListGallery = listOf(
        "Кадры", "Со съемок", "Постеры",
        "Фан-арты",
        "Промо", "Концепт-арты",
        "Обои", "Обложки", "Скриншоты"
    )

    companion object {
        fun provideFactory(
             getFilmUseCase: GetFilmUseCase,
            getSeasonsUseCase: GetSeasonsUseCase,
            getMovieCollectionsIdUseCase: GetMovieCollectionsIdUseCase,
            getStaffByFilmUseCase: GetStaffByFilmUseCase,
             getMovieGalleryUseCase: GetMovieGalleryUseCase,
             addToMyCollectionUseCase: AddToMyCollectionUseCase,
            getMyCollectionsUseCase: GetMyCollectionsUseCase,
            getCollectionByNameUseCase: GetCollectionByNameUseCase,
            getSimilarCollectionUseCase: GetSimilarCollectionUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FilmPageViewModel(        getFilmUseCase,
                    getSeasonsUseCase,
                    getMovieCollectionsIdUseCase,
                    getStaffByFilmUseCase,
                    getMovieGalleryUseCase,
                    addToMyCollectionUseCase,
                    getMyCollectionsUseCase,
                    getCollectionByNameUseCase,
                    getSimilarCollectionUseCase) as T
            }
        }
    }
}