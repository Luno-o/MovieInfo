package com.example.movieinfo.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieinfo.domain.usecase.GetCollectionUseCase
import com.example.movieinfo.presentation.ui.layout.MovieCollectionRow
import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.models.LoadStateUI
import com.movieinfo.domain.models.MovieCollectionRowMut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val getCollectionUseCase: GetCollectionUseCase
) : ViewModel() {


    private val _premieres = MutableStateFlow<LoadStateUI<List<MovieCollection>>>(LoadStateUI.Loading)
    private val premieres = _premieres.asStateFlow()

    private val _collectionTopPopularAll = MutableStateFlow<LoadStateUI<List<MovieCollection>>>(LoadStateUI.Loading)
    private val collectionTopPopularAll = _collectionTopPopularAll.asStateFlow()

    private val _collectionComicsTheme = MutableStateFlow<LoadStateUI<List<MovieCollection>>>(LoadStateUI.Loading)
    private val collectionComicsTheme = _collectionComicsTheme.asStateFlow()

    private val _collectionTopPopularMovies = MutableStateFlow<LoadStateUI<List<MovieCollection>>>(LoadStateUI.Loading)
    private val collectionTopPopularMovies = _collectionTopPopularMovies.asStateFlow()

    private val _collectionTopPopularTVShows = MutableStateFlow<LoadStateUI<List<MovieCollection>>>(LoadStateUI.Loading)
    private val collectionTopPopularTVShows = _collectionTopPopularTVShows.asStateFlow()

    private val _collectionVampireTheme = MutableStateFlow<LoadStateUI<List<MovieCollection>>>(LoadStateUI.Loading)
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
        MovieCollectionRowMut(
            _collectionComicsTheme, "Комиксы",
            CollectionType.COMICS_THEME
        ),
        MovieCollectionRowMut(
            _collectionTopPopularMovies, "Популярные фильмы",
            CollectionType.TOP_POPULAR_MOVIES
        ),
        MovieCollectionRowMut(
            _collectionVampireTheme, "Вампиры",
            CollectionType.VAMPIRE_THEME
        ),
        MovieCollectionRowMut(
            _collectionTopPopularTVShows, "Топ 250 сериалов",
            CollectionType.TOP_250_TV_SHOWS
        ),
    )

    init {
        runBlocking {
            _collections.forEach {row->
                viewModelScope.launch(Dispatchers.IO) {
getCollectionUseCase(row).collect{
    row.movieCards.emit(it)
}
                }
            }
        }
    }

//    companion object {
//        fun provideFactory(

//        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return MainPageViewModel(useCase) as T
//            }
//        }
//    }
}