package com.example.movieinfo.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.example.movieinfo.utils.ListPagingMovieCollection
import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.models.LoadStateUI
import com.movieinfo.domain.usecase.GetSimilarCollectionUseCase
import com.movieinfo.domain.usecase.ShowCollectionUseCase
import com.movieinfo.domain.usecase.ShowMyCollectionFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ShowCollectionViewModel @Inject constructor(
    private val showMyCollectionFlowUseCase: ShowMyCollectionFlowUseCase,
    private val showCollectionUseCase: ShowCollectionUseCase,
    private val similarCollectionUseCase: GetSimilarCollectionUseCase
) :
    ViewModel() {

    private val _yourCollection = MutableStateFlow<LoadStateUI<List<MovieCollection>>>(LoadStateUI.Loading)
    val yourCollection = _yourCollection.asStateFlow()

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
        return showCollectionUseCase(collectionType, page)
    }

    suspend fun loadSimilarMovie(id: Int): List<MovieCollection> {
        return similarCollectionUseCase(id)
    }

    suspend fun getCollectionByNameFlow(name: String){
  showMyCollectionFlowUseCase(name).collect{
      _yourCollection.emit(it)
  }


//        val deferred = viewModelScope.async {
//                showMyCollectionFlowUseCase(name)
//        }.await()
//        deferred.collectLatest {
//        _yourCollection.value = it
//        }
    }

    companion object {
        fun provideFactory(
           showMyCollectionFlowUseCase: ShowMyCollectionFlowUseCase,
           showCollectionUseCase: ShowCollectionUseCase,
           similarCollectionUseCase: GetSimilarCollectionUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ShowCollectionViewModel(   showMyCollectionFlowUseCase,
                    showCollectionUseCase,
                    similarCollectionUseCase) as T
            }
        }
    }
}