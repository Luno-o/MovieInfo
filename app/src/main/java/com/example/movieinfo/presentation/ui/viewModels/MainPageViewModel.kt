package com.example.movieinfo.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movieinfo.domain.usecase.GetMainPageUseCase
import com.example.movieinfo.presentation.ui.layout.MovieCollectionRow
import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.models.MovieCollectionRowMut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val getMainPageUseCase: GetMainPageUseCase
) : ViewModel() {


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
        MovieCollectionRowMut(
            _premieres, "Премьеры",
            CollectionType.PREMIERES
        ),
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
getMainPageUseCase.execute(_collections)
        }
    }

    companion object {
        fun provideFactory(
            useCase: GetMainPageUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainPageViewModel(useCase) as T
            }
        }
    }
}