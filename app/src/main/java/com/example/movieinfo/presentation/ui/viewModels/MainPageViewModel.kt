package com.example.movieinfo.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.movieinfo.data.db.MyMovieCollectionsDb
import com.movieinfo.domain.usecase.MainPageUseCase
import com.example.movieinfo.presentation.ui.layout.MovieCollectionRow
import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.parcel.RawValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val mainPageUseCase: MainPageUseCase
) : ViewModel() {
    private data class MovieCollectionRowMut(
        val movieCards: MutableStateFlow<List<MovieCollection>>,
        val collectionName: String,
        val movieType: CollectionType
    )

    private val _premieres = MutableStateFlow<List<MovieCollection>>(emptyList())
    private val premieres = _premieres.asStateFlow()

    private val _collectionTopPopularAll = MutableStateFlow<List<MovieCollection>>(emptyList())
    private val collectionTopPopularAll = _collectionTopPopularAll.asStateFlow()

    private val _collectionComicsTheme = MutableStateFlow<List<MovieCollection>>(emptyList())
    private val collectionComicsTheme = _collectionComicsTheme.asStateFlow()

    private val _collectionTopPopularMovies = MutableStateFlow<List<MovieCollection>>(emptyList())
    private val collectionTopPopularMovies = _collectionTopPopularMovies.asStateFlow()

    private val _collectionTopPopularTVShows = MutableStateFlow<List<MovieCollection>>(emptyList())
    private val collectionTopPopularTVShows = _collectionTopPopularTVShows.asStateFlow()

    private val _collectionVampireTheme = MutableStateFlow<List<MovieCollection>>(emptyList())
    private val collectionVampireTheme = _collectionVampireTheme.asStateFlow()


    private suspend fun loadPremieres() {
        viewModelScope.launch(Dispatchers.IO) {
            mainPageUseCase.getPremieres().collect {
                _premieres.value = it
            }
        }
    }

    val collections = listOf(
        MovieCollectionRow(
            premieres, "Премьеры",
            CollectionType.PREMIERES
        ),
        MovieCollectionRow(
            collectionTopPopularAll, "Популярное",
            CollectionType.TOP_POPULAR_ALL
        ),
        MovieCollectionRow(
            collectionComicsTheme, "Комиксы",
            CollectionType.COMICS_THEME
        ),
        MovieCollectionRow(
            collectionTopPopularMovies, "Популярные фильмы",
            CollectionType.TOP_POPULAR_MOVIES
        ),
        MovieCollectionRow(
            collectionVampireTheme, "Вампиры",
            CollectionType.VAMPIRE_THEME
        ),
        MovieCollectionRow(
            collectionTopPopularTVShows, "Топ 250 сериалов",
            CollectionType.TOP_250_TV_SHOWS
        ),
    )
    private val _collections = listOf(
//        MovieCollectionRowMut(
//            _premieres, "Премьеры",
//            CollectionType.PREMIERES
//        ),
        MovieCollectionRowMut(
            _collectionTopPopularAll, "Популярное",
            CollectionType.TOP_POPULAR_ALL
        ),
//        MovieCollectionRowMut(
//            _collectionComicsTheme, "Комиксы",
//            CollectionType.COMICS_THEME
//        ),
//        MovieCollectionRowMut(
//            _collectionTopPopularMovies, "Популярные фильмы",
//            CollectionType.TOP_POPULAR_MOVIES
//        ),
//        MovieCollectionRowMut(
//            _collectionVampireTheme, "Вампиры",
//            CollectionType.VAMPIRE_THEME
//        ),
//        MovieCollectionRowMut(
//            _collectionTopPopularTVShows, "Топ 250 сериалов",
//            CollectionType.TOP_250_TV_SHOWS
//        ),
    )

    init {
        runBlocking {
            if (_collections.isNotEmpty()) {
                _collections.forEach {
                    if (it.movieType == CollectionType.PREMIERES) {
                        loadPremieres()
                    } else {
                        it.movieCards.value = mainPageUseCase.getCollection(it.movieType)
                    }
                }
            }

        }
    }

    companion object {
        fun provideFactory(
            useCase: MainPageUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainPageViewModel(useCase) as T
            }
        }
    }
}