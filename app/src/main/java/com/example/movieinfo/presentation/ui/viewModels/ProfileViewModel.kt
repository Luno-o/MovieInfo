package com.example.movieinfo.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.entity.MyMovieCollections
import com.movieinfo.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: ProfileUseCase
) : ViewModel() {
    var collectionsList = MutableStateFlow(emptyList<MyMovieCollections>())

    private val _yourCollections = MutableStateFlow<List<List<MovieCollection>>>(emptyList())
    val yourCollections = _yourCollections.asStateFlow()

    private val _yourInterest = MutableStateFlow<List<MovieCollection>>(emptyList())
    val yourInterest = _yourInterest.asStateFlow()

    private val _watchedMovie = MutableStateFlow<List<MovieDb>>(emptyList())
    val watchedMovie = _watchedMovie.asStateFlow()

    fun addCollection(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.addCollection(text)
            getCollectionsList()
        }
    }

    suspend fun loadWatchedMovie() {
        viewModelScope.launch {
            val id = collectionsList.value.find { it.collectionName == "Просмотрено" }?.id
            if (id != null) {
                _watchedMovie.value = useCase.getCollectionById(id)
            }
        }
    }

    suspend fun loadYourInterestMovie() {
        viewModelScope.launch {
            val id = collectionsList.value.find { it.collectionName == "Вам было интересно" }?.id
            if (id != null) {
                _yourInterest.value = useCase.getCollectionById(id)
            }
        }
    }

    suspend fun loadYourCollections(list: List<MyMovieCollections>) {
        val fullList = mutableListOf<List<MovieCollection>>()
        list.forEach { myCollection ->
            fullList.add(useCase.getCollectionById(myCollection.id))
        }
        _yourCollections.value = fullList
    }

    suspend fun getAllCollections() {
        useCase.getAllCollections()

    }

    suspend fun getCollectionsList() {
        val deferred = viewModelScope.async {
            useCase.getCollectionsName()
        }.await()
        collectionsList.value = deferred
        loadYourCollections(deferred)
    }

    fun clearHistory(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = collectionsList.value.find { it.collectionName == name }?.id
            if (id != null) {
                useCase.deleteHistory(id)
                Timber.d("deleteCollectionById $id")
                getCollectionsList()
            }
            loadWatchedMovie()
            loadYourInterestMovie()
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllCollections()
            if (collectionsList.value.size < 4) {
                this.let {
                    addCollection("Избранное")
                    addCollection("Закладки")
                    addCollection("Просмотрено")
                    addCollection("Вам было интересно")
                }
            }
        }
        runBlocking {
            viewModelScope.launch(Dispatchers.IO) {
                if (_yourCollections.value.isEmpty()) {
                    getCollectionsList()
                    loadYourCollections(collectionsList.value)
                }
                loadYourInterestMovie()
                loadWatchedMovie()
            }
        }
    }

    companion object {
        fun provideFactory(
            useCase: ProfileUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProfileViewModel(useCase) as T
            }
        }
    }
}