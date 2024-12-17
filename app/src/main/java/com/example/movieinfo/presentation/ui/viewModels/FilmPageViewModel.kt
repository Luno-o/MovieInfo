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
import com.movieinfo.domain.models.LoadStateUI
import com.movieinfo.domain.usecase.AddToMyCollectionUseCase
import com.movieinfo.domain.usecase.GetCollectionByNameUseCase
import com.movieinfo.domain.usecase.GetFilmUseCase
import com.movieinfo.domain.usecase.GetMovieCollectionsIdUseCase
import com.movieinfo.domain.usecase.GetMovieGalleryFlowUseCase
import com.movieinfo.domain.usecase.GetMovieGalleryUseCase
import com.movieinfo.domain.usecase.GetMyCollectionsUseCase
import com.movieinfo.domain.usecase.GetSeasonsUseCase
import com.movieinfo.domain.usecase.GetSimilarCollectionFlowUseCase
import com.movieinfo.domain.usecase.GetStaffByFilmUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
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
    private val getMovieGalleryFlowUseCase: GetMovieGalleryFlowUseCase,
    private val addToMyCollectionUseCase: AddToMyCollectionUseCase,
    private val getMyCollectionsUseCase: GetMyCollectionsUseCase,
    private val getCollectionByNameUseCase: GetCollectionByNameUseCase,
    private val getSimilarCollectionFlowUseCase: GetSimilarCollectionFlowUseCase
) : ViewModel() {

    private val _movie = MutableStateFlow<LoadStateUI<MovieBaseInfo>>(LoadStateUI.Loading)
    val movie = _movie.asStateFlow()
    private val _seasons = MutableStateFlow<LoadStateUI<List<SerialWrapper>>>(LoadStateUI.Loading)
    val seasons = _seasons.asStateFlow()

    private val _staffByFilm = MutableStateFlow<LoadStateUI<List<Staff>>>(LoadStateUI.Loading)
    val staffByFilm = _staffByFilm.asStateFlow()
    private val _movieGallery = MutableStateFlow<LoadStateUI<List<MovieGallery>>>(LoadStateUI.Loading)
    val movieGallery = _movieGallery.asStateFlow()
    private val _similarMovies = MutableStateFlow<LoadStateUI<List<MovieCollection>>>(LoadStateUI.Loading)
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
        val deferred = viewModelScope.async(Dispatchers.IO) {
            getMyCollectionsUseCase()
        }.await()
        collectionsList.value = deferred
        loadYourCollections(deferred)
    }

    private suspend fun loadYourCollections(list: List<MyMovieCollections>) {
        viewModelScope.launch(Dispatchers.IO) {
        val fullList = mutableListOf<List<MovieCollection>>()
        list.forEach { myCollection ->
            fullList.add(getCollectionByNameUseCase(myCollection.collectionName))
        }
        _yourCollections.value = fullList
    }}

    suspend fun loadSeasons(id: Int) {
        viewModelScope.launch {
                getSeasonsUseCase(id).collect{
            _seasons.value = it
                }
        }
    }

    suspend fun getCollectionsIdForMovie(kpId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
        collectionsIdForMovie.value = getMovieCollectionsIdUseCase(kpId)
        checkMyFavourite()
        checkMyWatched()
        checkMyBookmark()
        }
    }

    private fun checkMyFavourite() {
        viewModelScope.launch(Dispatchers.IO) {
            if (collectionsList.value.isEmpty()) {
                runBlocking {
                    getCollectionsList()
                }
            }
            val id = collectionsList.value.find { it.collectionName == "Избранное" }?.id
            isMyFavourite.value = collectionsIdForMovie.value.contains(id)
        }
    }
    private fun checkMyBookmark() {
        viewModelScope.launch(Dispatchers.IO) {
        if (collectionsList.value.isEmpty()) {
            runBlocking {
                getCollectionsList()
            }
        }
        val id = collectionsList.value.find { it.collectionName == "Закладки" }?.id
        isMyBookmark.value = collectionsIdForMovie.value.contains(id)
    }}

    private fun checkMyWatched() {
        viewModelScope.launch(Dispatchers.IO) {
        if (collectionsList.value.isEmpty()) {
            runBlocking {
                getCollectionsList()
            }
        }
        val id = collectionsList.value.find { it.collectionName == "Просмотрено" }?.id
        isMyWatched.value = collectionsIdForMovie.value.contains(id)
    }}


    suspend fun loadMovieById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
                getFilmUseCase(id).collect{
            _movie.value = it
                }
        }
    }

    suspend fun loadStaffByFilmId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
                getStaffByFilmUseCase(id).collect{
            _staffByFilm.value = it
                }
        }
    }

    suspend fun loadMovieGallery(id: Int, galleryType: GalleryType = GalleryType.SHOOTING) {
        viewModelScope.launch(Dispatchers.IO) {
                getMovieGalleryFlowUseCase(id, galleryType).collect{
            _movieGallery.value = it
                }
        }
    }

    suspend fun loadMovieGalleryAll(id: Int) {
        val list = mutableListOf<Pair<GalleryType, List<MovieGallery>>>()
        viewModelScope.launch(Dispatchers.IO) {
            GalleryType.entries.forEach { galleryType ->
                list.add(Pair(galleryType, getMovieGalleryUseCase(id, galleryType)))
                delay(100)
            }
            _movieGalleryAll.value = list

        }
    }

    suspend fun loadSimilarMovie(id: Int) {
        viewModelScope.launch {
                getSimilarCollectionFlowUseCase(id).collect{
            _similarMovies.value = it
                }
        }
    }

    fun addRemoveToMyCollection(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
        val movie = _movie.value as LoadStateUI.Success
                addToMyCollectionUseCase(movie.data,id)
            getCollectionsIdForMovie(movie.data.kpID)
                getCollectionsList()
            Timber.d(" ViewModel add to collection film ${movie.data.nameRU} collection id $id " +
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
            getMovieGalleryFlowUseCase: GetMovieGalleryFlowUseCase,
            addToMyCollectionUseCase: AddToMyCollectionUseCase,
            getMyCollectionsUseCase: GetMyCollectionsUseCase,
            getCollectionByNameUseCase: GetCollectionByNameUseCase,
            getSimilarCollectionFlowUseCase: GetSimilarCollectionFlowUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FilmPageViewModel(        getFilmUseCase,
                    getSeasonsUseCase,
                    getMovieCollectionsIdUseCase,
                    getStaffByFilmUseCase,
                    getMovieGalleryUseCase,
                    getMovieGalleryFlowUseCase,
                    addToMyCollectionUseCase,
                    getMyCollectionsUseCase,
                    getCollectionByNameUseCase,
                    getSimilarCollectionFlowUseCase) as T
            }
        }
    }
}