package com.movieinfo.domain.models

import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieCollection
import kotlinx.coroutines.flow.MutableStateFlow

data class MovieCollectionRowMut(
    val movieCards: MutableStateFlow<LoadStateUI<List<MovieCollection>>>,
    val collectionName: String,
    val movieType: CollectionType
)
sealed class LoadStateUI<out T : Any> {
    object Loading : LoadStateUI<Nothing>()
    data class Success<out T : Any>(val data: T) : LoadStateUI<T>()
    data class Error(val throwable: Throwable) : LoadStateUI<Nothing>()
}