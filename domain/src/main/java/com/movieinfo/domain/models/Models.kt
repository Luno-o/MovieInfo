package com.movieinfo.domain.models

import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieCollection
import kotlinx.coroutines.flow.MutableStateFlow

data class MovieCollectionRowMut(
    val movieCards: MutableStateFlow<List<MovieCollection>>,
    val collectionName: String,
    val movieType: CollectionType
)