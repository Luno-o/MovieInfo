package com.example.movieinfo.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movieinfo.data.repository.storage.models.MovieCollectionImpl
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.entity.StaffFullInfo
import com.movieinfo.domain.models.LoadStateUI
import com.movieinfo.domain.usecase.ActorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(private val getStaffUseCase: ActorUseCase) : ViewModel() {


    private val _staff = MutableStateFlow<LoadStateUI<StaffFullInfo>>(LoadStateUI.Loading)
    val staff = _staff.asStateFlow()

    private val _staffMovieCollection = MutableStateFlow<LoadStateUI<List<MovieCollection>>>(LoadStateUI.Loading)
    val staffMovieCollection = _staffMovieCollection.asStateFlow()

    private val _staffForMovieCollections =
        MutableStateFlow<Map<MovieCollectionImpl, String?>>(emptyMap())
    val staffForMovieCollections = _staffForMovieCollections.asStateFlow()

    suspend fun loadStaffById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getStaffUseCase(id).collect {
                _staff.value = it
                if (it is LoadStateUI.Success) {
                    val movieCollection = it.data.films.associate { film ->
                        Pair(
                            MovieCollectionImpl(
                                kpID = film.filmId,
                                imdbId = null,
                                nameRU = film.nameRU,
                                nameENG = film.nameENG,
                                nameOriginal = null,
                                countries = null,
                                genre = null,
                                ratingKP = null,
                                ratingImdb = null,
                                year = null,
                                type = null,
                                posterUrl = null,
                                prevPosterUrl = null
                            ), film.professionKey
                        )
                    }
                    _staffForMovieCollections.emit(movieCollection)
                    _staffMovieCollection.emit(LoadStateUI.Success(it.data.films.map { film ->
                        MovieCollectionImpl(
                            kpID = film.filmId,
                            imdbId = null,
                            nameRU = film.nameRU,
                            nameENG = film.nameENG,
                            nameOriginal = null,
                            countries = null,
                            genre = null,
                            ratingKP = film.rating?.toFloat() ?: 0.0f,
                            ratingImdb = null,
                            year = null,
                            type = null,
                            posterUrl = null,
                            prevPosterUrl = null
                        )
                    }))
                }
            }


        }
    }

    val tabList = listOf(
        "Писатель", "Оператор",
        "Редактор", "Композитор", "Продюсер СССР",
        "Переводчик", "Директор", "Дизайнер",
        "Продюсер озвучки", "Актер", "Режиссер",
        "Неизвестно",
        "Себя"
    )

//    companion object {
//        fun provideFactory(
//            useCase: ActorUseCase
//        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return ActorViewModel(useCase) as T
//            }
//        }
//    }
}