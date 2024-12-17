package com.example.movieinfo.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.entity.MyMovieCollections
import com.movieinfo.domain.models.LoadStateUI
import com.movieinfo.domain.usecase.AddCollectionUseCase
import com.movieinfo.domain.usecase.DeleteHistoryUseCase
import com.movieinfo.domain.usecase.GetCollectionByNameUseCase
import com.movieinfo.domain.usecase.GetMyCollectionsUseCase
import com.movieinfo.domain.usecase.ShowMyCollectionFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCollectionByNameUseCase: GetCollectionByNameUseCase,
    private val getCollectionByNameUseCaseFlow: ShowMyCollectionFlowUseCase,
    private val addCollectionUseCase: AddCollectionUseCase,
    private val getMyCollectionsUseCase: GetMyCollectionsUseCase,
    private val clearHistoryUseCase: DeleteHistoryUseCase
) : ViewModel() {

    private val _collectionsList = MutableStateFlow(emptyList<MyMovieCollections>())
    var collectionsList = _collectionsList.asStateFlow()

    private val _yourCollections = MutableStateFlow<List<List<MovieCollection>>>(emptyList())
    val yourCollections = _yourCollections.asStateFlow()

    private val _yourInterest = MutableStateFlow<LoadStateUI<List<MovieCollection>>>(LoadStateUI.Loading)
    val yourInterest = _yourInterest.asStateFlow()

    private val _watchedMovie = MutableStateFlow<LoadStateUI<List<MovieDb>>>(LoadStateUI.Loading)
    val watchedMovie = _watchedMovie.asStateFlow()

    fun addCollection(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addCollectionUseCase(text)
            getCollectionsList()
        }
    }

    private suspend fun loadWatchedMovie() {
        viewModelScope.launch(Dispatchers.IO) {
                    getCollectionByNameUseCaseFlow("Просмотрено").collect{
                _watchedMovie.value = it
                    }
        }
        }

    private suspend fun loadYourInterestMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            getCollectionByNameUseCaseFlow("Вам было интересно").collect {
                _yourInterest.value = it
            }
        }
    }

    private suspend fun loadYourCollections(list: List<MyMovieCollections>) {
        viewModelScope.launch(Dispatchers.IO) {
            val fullList = mutableListOf<List<MovieCollection>>()
            list.forEach { myCollection ->
                fullList.add(getCollectionByNameUseCase(myCollection.collectionName))
            }
            _yourCollections.value = fullList
        }
    }

    private suspend fun getCollectionsList() {
        val deferred = viewModelScope.async(Dispatchers.IO) {
            getMyCollectionsUseCase()
        }.await()
        _collectionsList.value = deferred
        loadYourCollections(deferred)
    }

    fun clearHistory(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = _collectionsList.value.find { it.collectionName == name }?.id
            if (id != null) {
                clearHistoryUseCase(id)
                Timber.d("deleteCollectionById $id")
                getCollectionsList()
            }
            loadWatchedMovie()
            loadYourInterestMovie()
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {

            if (_collectionsList.value.size < 4) {
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
                    loadYourCollections(_collectionsList.value)
                }
                loadYourInterestMovie()
                loadWatchedMovie()
            }
        }
    }

    companion object {
        fun provideFactory(
            getCollectionByNameUseCase: GetCollectionByNameUseCase,
            getCollectionByNameUseCaseFlow: ShowMyCollectionFlowUseCase,
            addCollectionUseCase: AddCollectionUseCase,
            getMyCollectionsUseCase: GetMyCollectionsUseCase,
            clearHistoryUseCase: DeleteHistoryUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProfileViewModel(getCollectionByNameUseCase,
                    getCollectionByNameUseCaseFlow,
                    addCollectionUseCase,
                    getMyCollectionsUseCase,
                    clearHistoryUseCase) as T
            }
        }
    }
}