package com.example.movieinfo.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.movieinfo.utils.ListPagingMovieCollection
import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.MyMovieCollections
import com.movieinfo.domain.usecase.ShowCollectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShowCollectionViewModel @Inject constructor(private val showCollectionUseCase: ShowCollectionUseCase) :
    ViewModel() {

    private val _yourCollection = MutableStateFlow<List<MovieCollection>>(emptyList())
    val yourCollection = _yourCollection.asStateFlow()
    private val _yourCollections = MutableStateFlow<List<List<MovieCollection>>>(emptyList())
    private var collectionsList = MutableStateFlow(emptyList<MyMovieCollections>())

    fun getPagingData(collectionType: String?, kpId: String): Flow<PagingData<MovieCollection>>? {
        return collectionType?.let { CollectionType.valueOf(it) }?.let {
            ListPagingMovieCollection.pager(
                this,
                it, kpId.toInt()
            ).flow
        }
    }

    suspend fun loadCollectionPaging(
        page: Int = 1,
        collectionType: CollectionType
    ): List<MovieCollection> {
        return showCollectionUseCase
            .getCollection(collectionType, page)
    }

    suspend fun loadPremieres(page: Int): List<MovieCollection> {
        return showCollectionUseCase.getPremieres(page)
    }

    suspend fun loadSimilarMovie(id: Int): List<MovieCollection> {
        return showCollectionUseCase.getSimilarMovie(id)
    }

    suspend fun getCollectionByNameFlow(name: String){
        val deferred = viewModelScope.async {
            val id = collectionsList.value.find { it.collectionName == name }?.id
            Timber.d("getCollectionByNameflow  id $id")
            if (id != null) {
                showCollectionUseCase.getCollectionByIdFlow(id)
            }else flow { emptyList<MovieCollection>() }
        }.await()
        deferred.collectLatest {
        _yourCollection.value = it
        }
    }

    suspend fun getCollectionsList() {
        val deferred = viewModelScope.async {
            showCollectionUseCase.getCollectionsName()
        }.await()
        collectionsList.value = deferred
        loadYourCollections(deferred)
    }

    private suspend fun loadYourCollections(list: List<MyMovieCollections>) {
        val fullList = mutableListOf<List<MovieCollection>>()
        list.forEach { myCollection ->
            fullList.add(showCollectionUseCase.getCollectionById(myCollection.id))
        }
        _yourCollections.value = fullList
    }

    companion object {
        fun provideFactory(
            useCase: ShowCollectionUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ShowCollectionViewModel(useCase) as T
            }
        }
    }
}