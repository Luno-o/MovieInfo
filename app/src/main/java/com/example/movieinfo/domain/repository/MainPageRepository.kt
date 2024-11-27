package com.example.movieinfo.domain.repository

import com.example.movieinfo.entity.CollectionType
import com.example.movieinfo.entity.MovieCollection
import com.example.movieinfo.presentation.ui.layout.MovieCollectionRow

interface MainPageRepository {

    fun setCollections(): List<MovieCollectionRow>
    fun loadPremieres()
    fun loadCollections(
        collectionType: List<CollectionType>
    ): List<List<MovieCollection>>
}