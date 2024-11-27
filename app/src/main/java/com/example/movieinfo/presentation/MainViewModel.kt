package com.example.movieinfo.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieinfo.App
import com.example.movieinfo.R
import com.example.movieinfo.data.db.MovieCollectionDB
import com.example.movieinfo.data.db.MyMovieCollections
import com.example.movieinfo.data.moviesDto.CountryDto
import com.example.movieinfo.data.moviesDto.GenreDto
import com.example.movieinfo.data.moviesDto.SerialWrapperDto
import com.example.movieinfo.domain.usecase.GetMovieInfoUseCase
import com.example.movieinfo.entity.GalleryType
import com.example.movieinfo.entity.MovieBaseInfo
import com.example.movieinfo.entity.MovieCollection
import com.example.movieinfo.entity.MovieGalleryImpl
import com.example.movieinfo.entity.MoviePremiere
import com.example.movieinfo.entity.CollectionType
import com.example.movieinfo.entity.Country
import com.example.movieinfo.entity.Genre
import com.example.movieinfo.entity.MovieBaseInfoImp
import com.example.movieinfo.entity.MovieCollectionImp
import com.example.movieinfo.entity.MovieGallery
import com.example.movieinfo.entity.MovieType
import com.example.movieinfo.entity.OrderType
import com.example.movieinfo.entity.Staff
import com.example.movieinfo.entity.emptyStaffFullInfo
import com.example.movieinfo.presentation.ui.layout.MovieCard
import com.example.movieinfo.presentation.ui.layout.MovieCollectionRow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMovieInfoUseCase: GetMovieInfoUseCase,
    app: App
) : AndroidViewModel(app) {
    init {
        viewModelScope.launch(Dispatchers.IO) {
//            viewModel.loadPrevCollectionTopPopularAll()
//            viewModel.loadPrevPremieres()
            loadPrevCollectionComicsTheme()
//            viewModel.loadCollectionTopPopularMovies()
//            loadCollectionTop250TVShows()
//            viewModel.loadCollectionVampireTheme()
            getAllCollections()
            if (collectionsList.value.size <= 3) {
                this.let {
                    addCollection("Избранное")
                    addCollection("Закладки")
                    addCollection("Просмотрено")
                }
            }
        }
    }


    private val _premieres = MutableStateFlow<List<MovieCard>>(emptyList())
    val premieres = _premieres.asStateFlow()

    private val _collectionTopPopularAll = MutableStateFlow<List<MovieCollection>>(emptyList())
    val collectionTopPopularAll = _collectionTopPopularAll.asStateFlow()

    private val _collectionComicsTheme = MutableStateFlow<List<MovieCollection>>(emptyList())
    val collectionComicsTheme = _collectionComicsTheme.asStateFlow()

    private val _collectionTopPopularMovies = MutableStateFlow<List<MovieCollection>>(emptyList())
    val collectionTopPopularMovies = _collectionTopPopularMovies.asStateFlow()

    private val _collectionTopPopularTVShows = MutableStateFlow<List<MovieCollection>>(emptyList())
    val collectionTopPopularTVShows = _collectionTopPopularTVShows.asStateFlow()

    private val _popularSeries = MutableStateFlow<List<MovieCollection>>(emptyList())
    val popularSeries = _popularSeries.asStateFlow()

    private val _collectionVampireTheme = MutableStateFlow<List<MovieCollection>>(emptyList())
    val collectionVampireTheme = _collectionVampireTheme.asStateFlow()

    private val _collectionTop250Movies = MutableStateFlow<List<MovieCollection>>(emptyList())
    val collectionTop250Movies = _collectionTop250Movies.asStateFlow()

    private val _collectionZombieTheme = MutableStateFlow<List<MovieCollection>>(emptyList())
    val collectionZombie = _collectionZombieTheme.asStateFlow()


    private val _premieresBaseInfo = MutableStateFlow<List<MovieBaseInfo>>(emptyList())
    val premiereBaseInfo = _premieresBaseInfo.asStateFlow()

    private val _similarMovies = MutableStateFlow<List<MovieCollection>>(emptyList())
    val similarMovies = _similarMovies.asStateFlow()


    private val _movie = MutableStateFlow<MovieBaseInfo?>(null)

    private val _movieToBase = MutableStateFlow<MovieBaseInfo?>(null)
    val movieToBase = _movieToBase.asStateFlow()
    val movie = _movie.asStateFlow()


    private val _movieGallery = MutableStateFlow<List<MovieGallery>>(emptyList())
    val movieGallery = _movieGallery.asStateFlow()

    private val _movieGalleryAll =
        MutableStateFlow<List<Pair<GalleryType, List<MovieGallery>>>>(emptyList())
    val movieGalleryAll = _movieGalleryAll.asStateFlow()

    private val _staffByFilm = MutableStateFlow<List<Staff>>(emptyList())
    val staffByFilm = _staffByFilm.asStateFlow()

    private val _staff = MutableStateFlow(emptyStaffFullInfo)
    val staff = _staff.asStateFlow()

    private val _staffForMovieCollection = MutableStateFlow<List<MovieCollection>>(emptyList())
    val staffForMovieCollection = _staffForMovieCollection.asStateFlow()

    private val _staffForMovieCollections =
        MutableStateFlow<Map<MovieCollectionImp, String?>>(emptyMap())
    val staffForMovieCollections = _staffForMovieCollections.asStateFlow()

    private val _seasons = MutableStateFlow(listOf(SerialWrapperDto(1, listOf())))
    val seasons = _seasons.asStateFlow()

    private val _watchedMovie = MutableStateFlow<List<MovieCollection>>(emptyList())
    val watchedMovie = _watchedMovie.asStateFlow()

    private val _loveTheme = MutableStateFlow<List<MovieCollection>>(emptyList())
    val loveTheme = _loveTheme.asStateFlow()

    private val _yourInterest = MutableStateFlow<List<MovieCollection>>(emptyList())
    val yourInterest = _yourInterest.asStateFlow()

    private val _yourCollection = MutableStateFlow<List<MovieCollection>>(emptyList())
    val yourCollection = _yourCollection.asStateFlow()

    private val _yourCollections = MutableStateFlow<List<List<MovieCollection>>>(emptyList())
    val yourCollections = _yourCollections.asStateFlow()

    var coutryInd = MutableStateFlow(0)
    var collectionsList = MutableStateFlow(emptyList<MyMovieCollections>())
    var genreInd = MutableStateFlow(0)
    var movieTypeInd = mutableIntStateOf(0)
    var sortTypeIndex = mutableIntStateOf(0)
    var yearRange = MutableStateFlow(Pair(1998, 2024))
    val yearBefore = mutableIntStateOf(2024)
    val yearAfter = mutableIntStateOf(1998)
    var raitingRange = MutableStateFlow((1f..10f))
    val queryState = TextFieldState("")
    val collectionsIdForMovie = MutableStateFlow(emptyList<Int>())
    val isMyFavourite = MutableStateFlow(false)
    val isMyWatched = MutableStateFlow(false)
    val isMyBookmark = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val movieSearchResult = snapshotFlow { queryState.text }.debounce(1000).distinctUntilChanged()
        .flatMapLatest { query ->
            Pager(
                PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = false
                )
            ) {
                ListPagingMovieBaseInfo(
                    this,
                    query.toString()
                )
            }.flow.cachedIn(viewModelScope)
        }

    val queryCountry = TextFieldState("")
    val queryGenre = TextFieldState("")

    suspend fun searchByFilter(
        page: Int = 1
    ): List<MovieCollection> {

        Timber.d("search by query ${queryState.text}")
        return getMovieInfoUseCase
            .getSearchByFilter(
                arrayOf(countriesToId[coutryInd.value].first),
                arrayOf(genresToId[genreInd.value].first),
                OrderType.entries[sortTypeIndex.intValue].name,
                MovieType.entries[movieTypeInd.intValue].name,
                raitingRange.value.start.toInt(),
                raitingRange.value.endInclusive.toInt(),
                yearRange.value.first,
                yearRange.value.second,
                queryState.text.toString(),
                page
            )
    }

    suspend fun loadWatchedMovie() {
        viewModelScope.launch {
            val id = collectionsList.value.find { it.collectionName == "Просмотрено" }?.id
            if (id != null) {
                _watchedMovie.value = getMovieInfoUseCase.getCollectionById(id).map {
                    movieCollectionDBToMovieCollection(it)
                }
            }
        }
    }

    suspend fun loadYourInterestMovie() {
        viewModelScope.launch {
            val id = collectionsList.value.find { it.collectionName == "Закладки" }?.id
            if (id != null) {
                _yourInterest.value = getMovieInfoUseCase.getCollectionById(id).map {
                    movieCollectionDBToMovieCollection(it)
                }
            }
        }
    }

    suspend fun loadYourCollections(list: List<MyMovieCollections>) {
        val fullList = mutableListOf<List<MovieCollection>>()
        list.forEach { myCollection ->
            fullList.add(getMovieInfoUseCase.getCollectionById(myCollection.id)
                .map { movieCollectionDBToMovieCollection(it) })
        }
        _yourCollections.value = fullList
    }

    suspend fun getAllCollections() {
        getMovieInfoUseCase.getAllCollections()

    }
 fun getCollectionByNameFlow(name: String){
    viewModelScope.launch {
        val id = collectionsList.value.find { it.collectionName == name }?.id
        if (id != null) {
            getMovieInfoUseCase.getCollectionByIdFlow(id).collect{flow->
        _yourCollection.value = flow.map { movieCollectionDBToMovieCollection(it) }
            }
            Timber.d("getCollectionByNameflow ${yourCollection.value.first().nameRU}")
        }
    }
}
    suspend fun getCollectionByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = collectionsList.value.find { it.collectionName == name }?.id
            if (id != null) {
                    val collection = getMovieInfoUseCase.getCollectionById(id)
                    .map { movieCollectionDBToMovieCollection(it) }
                if (collection !== yourCollection.value){
                _yourCollection.value = collection
                }
                Timber.d("getCollectionByName ${yourCollection.value.first().nameRU}")
            }
        }
    }

    suspend fun getCollectionsList() {
        val defered = viewModelScope.async {
            getMovieInfoUseCase.getCollectionsName()
        }.await()
        collectionsList.value = defered
        loadYourCollections(defered)
    }


    suspend fun loadPremieres() {
        viewModelScope.launch {
            Timber.d("searching by premieres")
            _premieres.value = getMovieInfoUseCase.getPremieres()
            _premieresBaseInfo.value = premieres.value.map {
                MovieBaseInfoImp(
                    kpID = it.kpID,
                    nameRU = it.nameRU,
                    nameENG = it.nameENG,
                    nameOriginal = it.nameOriginal,
                    posterUrl = it.posterUrl,
                    ratingKinopoisk = it.raitingKP,
                    year = it.year,
                    prevPosterUrl = it.prevPosterUrl,
                    countries = it.countries,
                    genreDtos = it.genreDtos,
                    type = it.type
                )
            }
        }

    }

    suspend fun searchByKeyWordPaging(query: String, page: Int = 1): List<MovieBaseInfo> {
        return viewModelScope.async(Dispatchers.IO) {

            getMovieInfoUseCase.getSearchByKeyWord(query, page)
        }.await()
    }

    suspend fun loadCollectionPaging(
        page: Int = 1,
        collectionType: CollectionType
    ): List<MovieCollection> {
        return getMovieInfoUseCase
            .getCollection(collectionType, page)
    }


    suspend fun loadPrevPremieres() {
        viewModelScope.launch {

            _premieres.value = getMovieInfoUseCase.getPremieres().take(8)
        }

    }

    suspend fun loadLoveTheme() {
        viewModelScope.launch {
            _loveTheme.value = getMovieInfoUseCase.getCollection(CollectionType.LOVE_THEME)
        }
    }

    suspend fun loadPrevLoveTheme() {
        viewModelScope.launch {
            _loveTheme.value = getMovieInfoUseCase.getCollection(CollectionType.LOVE_THEME).take(8)
        }
    }

    suspend fun loadSerials() {
        viewModelScope.launch {
            _popularSeries.value =
                getMovieInfoUseCase.getCollection(CollectionType.POPULAR_SERIES)
        }
    }

    suspend fun loadPrevSerials() {
        viewModelScope.launch {
            _popularSeries.value =
                getMovieInfoUseCase.getCollection(CollectionType.POPULAR_SERIES).take(8)
        }
    }

    suspend fun loadCollectionTopPopularAll() {
        viewModelScope.launch {
            _collectionTopPopularAll.value = getMovieInfoUseCase
                .getCollection(CollectionType.TOP_POPULAR_ALL)
        }
    }


    suspend fun loadPrevCollectionTopPopularAll() {
        viewModelScope.launch {
            _collectionTopPopularAll.value = getMovieInfoUseCase
                .getCollection(CollectionType.TOP_POPULAR_ALL).take(8)
        }
    }

    suspend fun loadCollectionTopPopularMovies() {
        viewModelScope.launch {
            _collectionTopPopularMovies.value = getMovieInfoUseCase
                .getCollection(CollectionType.TOP_POPULAR_MOVIES)
        }
    }

    suspend fun loadCollectionTop250TVShows() {
        _collectionTopPopularTVShows.value = viewModelScope.async {
            getMovieInfoUseCase
                .getCollection(CollectionType.TOP_250_TV_SHOWS)
        }.await()
    }

    suspend fun loadCollectionTop250Movies() {
        viewModelScope.launch {
            _collectionTop250Movies.value = getMovieInfoUseCase
                .getCollection(CollectionType.TOP_250_MOVIES)
        }
    }

    suspend fun loadPrevCollectionTop250Movies() {
        viewModelScope.launch {
            _collectionTop250Movies.value = getMovieInfoUseCase
                .getCollection(CollectionType.TOP_250_MOVIES).take(8)
        }
    }

    suspend fun loadCollectionVampireTheme() {
        viewModelScope.launch {
            _collectionVampireTheme.value = getMovieInfoUseCase
                .getCollection(CollectionType.VAMPIRE_THEME)
        }
    }

    suspend fun loadCollectionZombieTheme() {
        viewModelScope.launch {
            _collectionZombieTheme.value = getMovieInfoUseCase
                .getCollection(CollectionType.ZOMBIE_THEME)
        }
    }

    suspend fun loadCollectionComicsTheme() {
        viewModelScope.launch {
            _collectionComicsTheme.value = getMovieInfoUseCase
                .getCollection(CollectionType.COMICS_THEME)
        }
    }

    suspend fun loadPrevCollectionComicsTheme() {
        viewModelScope.launch {
            _collectionComicsTheme.value = getMovieInfoUseCase
                .getCollection(CollectionType.COMICS_THEME).take(8)
        }
    }

    suspend fun loadMovieById(id: Int) {
        viewModelScope.launch {
            _movie.value = getMovieInfoUseCase.getMovieById(id)
        }
    }

    suspend fun loadSimilarMovie(id: Int) {
        viewModelScope.launch {
            _similarMovies.value = getMovieInfoUseCase.getSimilarMovie(id).map {
                object : MovieCollection {
                    override val kpID: Int
                        get() = it.filmID
                    override val imdbId: String?
                        get() = null
                    override val nameRU: String?
                        get() = it.nameRU
                    override val nameENG: String?
                        get() = it.nameENG
                    override val nameOriginal: String?
                        get() = it.nameOriginal
                    override val countries: List<CountryDto>?
                        get() = null
                    override val genreDtos: List<GenreDto>?
                        get() = null
                    override val raitingKP: Float?
                        get() = null
                    override val raitingImdb: Float?
                        get() = null
                    override val year: Int?
                        get() = null
                    override val type: String?
                        get() = null
                    override val posterUrl: String?
                        get() = it.posterUrl
                    override val prevPosterUrl: String?
                        get() = it.prevPosterUrl

                }
            }
        }
    }

    suspend fun loadSeasons(id: Int) {
        viewModelScope.launch {
            _seasons.value = getMovieInfoUseCase.getSeasons(id)
        }
    }

    suspend fun loadMovieGallery(id: Int, galleryType: GalleryType = GalleryType.SHOOTING) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieGallery.value = getMovieInfoUseCase.getMovieGallery(id, galleryType)
                .map { MovieGalleryImpl(it.imageUrl, it.previewUrl) }
        }
    }

    suspend fun loadMovieGalleryAll(id: Int) {
        val list = mutableListOf<Pair<GalleryType, List<MovieGallery>>>()
        viewModelScope.launch(Dispatchers.IO) {
            GalleryType.entries.forEach { galleryType ->
                list.add(Pair(galleryType, getMovieInfoUseCase.getMovieGallery(id, galleryType)))
                delay(100)
            }
            _movieGalleryAll.value = list

        }
    }

    suspend fun loadStaffByFilmId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _staffByFilm.value = getMovieInfoUseCase.getStaffByFilmId(id)
        }
    }

    suspend fun loadStaffById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _staff.value = getMovieInfoUseCase.getStaffById(id)

            val movieCollection = _staff.value.films.associate { film ->
                Pair(
                    MovieCollectionImp(
                        kpID = film.filmId,
                        imdbId = null,
                        nameRU = film.nameRU,
                        nameENG = film.nameENG,
                        nameOriginal = null,
                        countries = null,
                        genreDtos = null,
                        raitingKP = null,
                        raitingImdb = null,
                        year = null,
                        type = null,
                        posterUrl = null,
                        prevPosterUrl = null
                    ), film.professionKey
                )
            }
            _staffForMovieCollections.emit(movieCollection)
            _staffForMovieCollection.emit(_staff.value.films.map { film ->
                MovieCollectionImp(
                    kpID = film.filmId,
                    imdbId = null,
                    nameRU = film.nameRU,
                    nameENG = film.nameENG,
                    nameOriginal = null,
                    countries = null,
                    genreDtos = null,
                    raitingKP = film.rating?.toFloat() ?: 0.0f,
                    raitingImdb = null,
                    year = null,
                    type = null,
                    posterUrl = null,
                    prevPosterUrl = null
                )
            })
        }
    }

    suspend fun load(page: Int): List<MoviePremiere> {
        delay(200L)
        return (0..10).map {
            object : MoviePremiere {
                override val kpID: Int
                    get() = 1111111
                override val nameRU: String
                    get() = "Tiltanic"
                override val nameENG: String
                    get() = "Tiltanic"
                override val year: Int
                    get() = 1999
                override val posterUrl: String
                    get() = "url poster"
                override val prevPosterUrl: String
                    get() = "url prew poster"
                override val countries: List<CountryDto>
                    get() = listOf(CountryDto("USA"))
                override val genreDtos: List<GenreDto>
                    get() = listOf(GenreDto("Comedy"))
                override val duration: Int
                    get() = 124
                override val premiereRu: String
                    get() = "21/01/1999"
            }
        }
    }


    fun movieBaseInfoToMovieCollectionDB(
        collectionId: List<Int>,
        movieBaseInfo: MovieBaseInfo
    ): MovieCollectionDB {
        return MovieCollectionDB(
            collectionID = collectionId,
            posterUrl = movieBaseInfo.posterUrl,
            prevPosterUrl = movieBaseInfo.prevPosterUrl,
            countries = movieBaseInfo.countries.firstOrNull()?.country,
            genreDtos = movieBaseInfo.genreDtos.firstOrNull()?.genre,
            kpID = movieBaseInfo.kpID,
            imdbId = movieBaseInfo.ImdbId,
            nameRU = movieBaseInfo.nameRU,
            nameENG = movieBaseInfo.nameENG,
            nameOriginal = movieBaseInfo.nameOriginal,
            raitingKP = movieBaseInfo.ratingKinopoisk,
            raitingImdb = movieBaseInfo.ratingImdb,
            year = movieBaseInfo.year,
            type = movieBaseInfo.type
        )
    }

    fun movieCollectionDBToMovieBaseInfo(movieCollectionDB: MovieCollectionDB): MovieBaseInfo {
        return MovieBaseInfoImp(
            posterUrl = movieCollectionDB.posterUrl,
            prevPosterUrl = movieCollectionDB.prevPosterUrl,
            countries = listOf(object : Country {
                override val country: String
                    get() = movieCollectionDB.countries ?: ""

            }),
            genreDtos = listOf(object : Genre {
                override val genre: String
                    get() = movieCollectionDB.genreDtos ?: ""
            }),
            kpID = movieCollectionDB.kpID,
            nameRU = movieCollectionDB.nameRU,
            nameENG = movieCollectionDB.nameENG,
            nameOriginal = movieCollectionDB.nameOriginal,
            ratingKinopoisk = movieCollectionDB.raitingKP,
            ratingImdb = movieCollectionDB.raitingImdb,
            year = movieCollectionDB.year,
            type = movieCollectionDB.type
        )
    }

    fun movieCollectionDBToMovieCollection(movieCollectionDB: MovieCollectionDB): MovieCollection {
        return MovieCollectionImp(
            posterUrl = movieCollectionDB.posterUrl,
            prevPosterUrl = movieCollectionDB.prevPosterUrl,
            countries = listOf(CountryDto(country = movieCollectionDB.countries ?: "")),
            genreDtos = listOf(GenreDto(genre = movieCollectionDB.genreDtos ?: "")),
            kpID = movieCollectionDB.kpID,
            imdbId = movieCollectionDB.imdbId,
            nameRU = movieCollectionDB.nameRU,
            nameENG = movieCollectionDB.nameENG,
            nameOriginal = movieCollectionDB.nameOriginal,
            raitingKP = movieCollectionDB.raitingKP,
            raitingImdb = movieCollectionDB.raitingImdb,
            year = movieCollectionDB.year,
            type = movieCollectionDB.type
        )
    }


    fun addToMyCollection(id: Int) {
        viewModelScope.launch {
            _movieToBase.value = _movie.value
            val newCollectionList = mutableListOf<Int>()
            val movieToCollection =
                _movieToBase.value?.let {
                    getMovieInfoUseCase.getMovieFromDB(_movieToBase.value!!.kpID)
                        ?.let { movieDb ->
                            if (!movieDb.collectionID.contains(id)) {
                                if (movieDb.collectionID.isNotEmpty()) {
                                    newCollectionList.addAll(movieDb.collectionID)
                                }
                            }

                        }
                    newCollectionList.add(id)
                    movieBaseInfoToMovieCollectionDB(newCollectionList, it)
                }
            if (movieToCollection != null) {
                Timber.d("my movie $movieToCollection")
                getMovieInfoUseCase.addToCollection(movieToCollection)
                movie.value?.let { getCollectionsIdForMovie(it.kpID) }
                getCollectionsList()
            }

        }
    }
    fun removeFromMyCollection(id: Int) {
        viewModelScope.launch {
            _movieToBase.value = _movie.value
            val newCollectionList = mutableListOf<Int>()
            val movieToCollection =
                _movieToBase.value?.let {
                    getMovieInfoUseCase.getMovieFromDB(_movieToBase.value!!.kpID)
                        ?.let { movieDb ->
                                if (movieDb.collectionID.isNotEmpty()) {
                                    newCollectionList.addAll(movieDb.collectionID)
                                }
                        }
                    if (newCollectionList.contains(id)) newCollectionList.remove(id)
                    movieBaseInfoToMovieCollectionDB(newCollectionList, it)
                }
            if (movieToCollection != null) {
                getMovieInfoUseCase.addToCollection(movieToCollection)
                movie.value?.let { getCollectionsIdForMovie(it.kpID) }
                getCollectionsList()
                val newColl = mutableListOf<MovieCollection>()
                newColl.addAll(_yourCollection.value)
                newColl.remove(movieCollectionDBToMovieCollection(movieToCollection))
                _yourCollection.value = newColl
                Timber.d("my movie $movieToCollection your collectionsize ${yourCollection.value.size}")
            }

        }
    }
suspend fun getCollectionsIdForMovie(kpId: Int){
    collectionsIdForMovie.value= getMovieInfoUseCase.getMovieFromDB(kpId)
        ?.collectionID ?:  emptyList()
    checkMyFavourite()
    checkMyWatched()
    checkMyBookmark()
}
    suspend fun isMovieInMyCollection(name: String): Boolean {

        if (collectionsList.value.isEmpty()){
        getCollectionsList()
        }
        val id = collectionsList.value.find { it.collectionName == name }?.id
        return if (id != null) {
            collectionsIdForMovie.value.contains(id)
        } else false
    }

 fun checkMyFavourite(){
    if (collectionsList.value.isEmpty()){
        runBlocking {
        getCollectionsList()
        }
    }
    val id = collectionsList.value.find { it.collectionName == "Избранное" }?.id
    isMyFavourite.value= collectionsIdForMovie.value.contains(id)
}
    fun checkMyBookmark(){
        if (collectionsList.value.isEmpty()){
            runBlocking {
                getCollectionsList()
            }
        }
        val id = collectionsList.value.find { it.collectionName == "Закладки" }?.id
        isMyBookmark.value= collectionsIdForMovie.value.contains(id)
    }
    fun checkMyWatched(){
        if (collectionsList.value.isEmpty()){
            runBlocking {
                getCollectionsList()
            }
        }
        val id = collectionsList.value.find { it.collectionName == "Просмотрено" }?.id
        isMyWatched.value= collectionsIdForMovie.value.contains(id)
    }
    fun addCollection(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getMovieInfoUseCase.addCollection(text)
            getCollectionsList()
        }
    }

    @Composable
    fun setCollections(): List<MovieCollectionRow> {

        return listOf(
            MovieCollectionRow(
                premieres, stringResource(R.string.premiers),
                CollectionType.PREMIERES
            ),
            MovieCollectionRow(
                collectionTopPopularAll, stringResource(R.string.popular),
                CollectionType.TOP_POPULAR_ALL
            ),
            MovieCollectionRow(
                collectionComicsTheme, stringResource(R.string.comics),
                CollectionType.COMICS_THEME
            ),
            MovieCollectionRow(
                collectionTopPopularMovies, stringResource(R.string.popular_movies),
                CollectionType.TOP_POPULAR_MOVIES
            ),
            MovieCollectionRow(
                collectionVampireTheme, stringResource(R.string.vampire_theme),
                CollectionType.VAMPIRE_THEME
            ),
            MovieCollectionRow(
                collectionTopPopularTVShows, stringResource(R.string.serial_top_250),
                CollectionType.TOP_250_TV_SHOWS
            ),
        )
    }

    fun clearHistory(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = collectionsList.value.find { it.collectionName == name }?.id
            if (id != null) {
                getMovieInfoUseCase.deleteHistory(id)
                Timber.d("deleteCollectionById $id")
                getCollectionsList()
            }
        }
    }


    val genresToId = listOf(
        1 to "триллер",
        2 to "драма",
        3 to "криминал",
        4 to "мелодрама",
        5 to "детектив",
        6 to "фантастика",
        7 to "приключения",
        8 to "биография",
        9 to "фильм-нуар",
        10 to "вестерн",
        11 to "боевик",
        12 to "фэнтези",
        13 to "комедия",
        14 to "военный",
        15 to "история",
        16 to "музыка",
        17 to "ужасы",
        18 to "мультфильм",
        19 to "семейный",
        20 to "мюзикл",
        21 to "спорт",
        22 to "документальный",
        23 to "короткометражка",
        24 to "аниме",
        25 to "",
        26 to "новости",
        27 to "концерт",
        28 to "для взрослых",
        29 to "церемония",
        30 to "реальное ТВ",
        31 to "игра",
        32 to "ток-шоу",
        33 to "детский"
    )
    val countriesToId = listOf(
        1 to "США",
        2 to "Швейцария",
        3 to "Франция",
        4 to "Польша",
        5 to "Великобритания",
        6 to "Швеция",
        7 to "Индия",
        8 to "Испания",
        9 to "Германия",
        10 to "Италия",
        11 to
                "Гонконг",
        12 to
                "Германия (ФРГ)",
        13 to
                "Австралия",
        14 to
                "Канада",
        15 to
                "Мексика",
        16 to
                "Япония",
        17 to
                "Дания",
        18 to
                "Чехия",
        19 to "Ирландия",
        20 to "Люксембург",
        21 to "Китай",
        22 to "Норвегия",
        23 to "Нидерланды",
        24 to "Аргентина",
        25 to "Финляндия",
        26 to "Босния и Герцеговина",
        27 to "Австрия",
        28 to "Тайвань",
        29 to "Новая Зеландия",
        30 to "Бразилия",
        31 to "Чехословакия",
        32 to "Мальта",
        33 to "СССР",
        34 to "Россия",
        35 to "Югославия",
        36 to "Португалия",
        37 to "Румыния",
        38 to "Хорватия",
        39 to "ЮАР",
        40 to "Куба",
        41 to "Колумбия",
        42 to "Израиль",
        43 to "Намибия",
        44 to "Турция",
        45 to "Бельгия",
        46 to "Сальвадор",
        47 to "Исландия",
        48 to "Венгрия",
        49 to "Корея Южная",
        50 to "Лихтенштейн",
        51 to "Болгария",
        52 to "Филиппины",
        53 to "Доминикана",
        54 to "",
        55 to "Марокко",
        56 to "Таиланд",
        57 to "Кения",
        58 to "Пакистан",
        59 to "Иран",
        60 to "Панама",
        61 to "Аруба",
        62 to "Ямайка",
        63 to "Греция",
        64 to "Тунис",
        65 to "Кыргызстан",
        66 to "Пуэрто Рико",
        67 to "Казахстан",
        68 to "Югославия (ФР)",
        69 to "Алжир",
        70 to "Германия (ГДР)",
        71 to "Сингапур",
        72 to "Словакия",
        73 to "Афганистан",
        74 to "Индонезия",
        75 to "Перу",
        76 to "Бермуды",
        77 to "Монако",
        78 to "Зимбабве",
        79 to "Вьетнам",
        80 to "Антильские Острова",
        81 to "Саудовская Аравия",
        82 to "Танзания",
        83 to "Ливия",
        84 to "Ливан",
        85 to "Кувейт",
        86 to "Египет",
        87 to "Литва",
        88 to "Венесуэла",
        89 to "Словения",
        90 to "Чили",
        91 to "Багамы",
        92 to "Эквадор",
        93 to "Коста-Рика",
        94 to "Кипр",
        95 to "Уругвай",
        96 to "Ирак",
        97 to "Мартиника",
        98 to "Эстония",
        99 to "ОАЭ",
        100 to "Бангладеш",
        101 to "Македония",
        102 to "Гвинея",
        103 to "Иордания",
        104 to "Латвия",
        105 to "Армения",
        106 to "Украина",
        107 to "Сирия",
        108 to "Шри-Ланка",
        109 to "Нигерия",
        110 to "Берег Слоновой кости",
        111 to "Грузия",
        112 to "Сенегал",
        113 to "Монголия",
        114 to "Габон",
        115 to "Замбия",
        116 to "Албания",
        117 to "Камерун",
        118 to "Буркина-Фасо",
        119 to "Узбекистан",
        120 to "Малайзия",
        121 to "Сербия",
        122 to "Гана",
        123 to "Таджикистан",
        124 to "Гаити",
        125 to "Конго (ДРК)",
        126 to "Гватемала",
        127 to "Российская империя",
        128 to "Беларусь",
        129 to "Молдова",
        130 to "Азербайджан",
        131 to "Палестина",
        132 to "Оккупированная Палестинская территория",
        133 to "Корея Северная",
        134 to "Никарагуа",
        135 to "Камбоджа",
        136 to "Ангола",
        137 to "Сербия и Черногория",
        138 to "Непал",
        139 to "Бенин",
        140 to "Гваделупа",
        141 to "Гренландия",
        142 to "Гвинея-Бисау",
        143 to "Макао",
        144 to "Парагвай",
        145 to "Мавритания",
        146 to "Руанда",
        147 to "Фарерские острова",
        148 to "Кот-д’Ивуар",
        149 to "Гибралтар",
        150 to "Ботсвана",
        151 to "Боливия",
        152 to "Мадагаскар",
        153 to "Кабо-Верде",
        154 to "Чад",
        155 to "Мали",
        156 to "Фиджи",
        157 to "Бутан",
        158 to "Барбадос",
        159 to "Тринидад и Тобаго",
        160 to "Мозамбик",
        161 to "Заир",
        162 to "Андорра",
        163 to "Туркменистан",
        164 to "Гайана",
        165 to "Корея",
        166 to "Нигер",
        167 to "Конго",
        168 to "Того",
        169 to "Ватикан",
        170 to "Черногория",
        171 to "Бурунди",
        172 to "Папуа - Новая Гвинея",
        173 to "Бахрейн",
        174 to "Гондурас",
        175 to "Судан",
        176 to "Эфиопия",
        177 to "Йемен",
        178 to "Вьетнам Северный",
        179 to "Суринам",
        180 to "Маврикий",
        181 to "Белиз",
        182 to "Либерия",
        183 to "Лесото",
        184 to "Уганда",
        185 to "Каймановы острова",
        186 to "Антигуа и Барбуда",
        187 to "Западная Сахара",
        188 to "Сан-Марино",
        189 to "Гуам",
        190 to "Косово",
        191 to "Лаос",
        192 to "Катар",
        193 to "Оман",
        194 to "Американские Виргинские острова",
        195 to "Сиам",
        196 to "Сьерра-Леоне",
        197 to "Эритрея",
        198 to "Сомали",
        199 to "Доминика",
        200 to "Бирма",
        201 to "Реюньон",
        202 to "Федеративные Штаты Микронезии",
        203 to "Самоа",
        204 to "Американское Самоа",
        205 to "Свазиленд",
        206 to "Французская Полинезия",
        207 to "Мьянма",
        208 to "Новая Каледония",
        209 to "Французская Гвиана",
        210 to "Сент-Винсент и Гренадины",
        211 to "Малави",
        212 to "Экваториальная Гвинея",
        213 to "Коморы",
        214 to "Кирибати",
        215 to "Тувалу",
        216 to "Тимор-Лесте",
        217 to "ЦАР",
        218 to "Тонга",
        219 to "Гренада",
        220 to "Гамбия",
        221 to "Антарктида",
        222 to "Острова Кука",
        223 to "Остров Мэн",
        224 to "Внешние малые острова США",
        225 to "Монтсеррат",
        226 to "Маршалловы острова",
        227 to "Бруней-Даруссалам",
        228 to "Сейшельские острова",
        229 to "Палау",
        230 to "Сент-Люсия",
        231 to "Вануату",
        232 to "Мальдивы",
        233 to "Босния",
        234 to "Уоллис и Футуна",
        235 to "Белоруссия",
        236 to "Киргизия",
        239 to "Джибути",
        240 to "Виргинские Острова (США)",
        241 to "Северная Македония",
        242 to "Виргинские Острова (Великобритания)",
        3545269 to "Сент-Люсия ",
        3781461 to "Сент-Китс и Невис",
        3985922 to "Соломоновы Острова",
        4336645 to "Виргинские Острова",
        7801402 to "Фолклендские острова",
        10842163 to "Остров Святой Елены",
        32518739 to "острова Теркс и Кайкос",
        47738117 to "Мелкие отдаленные острова США",
        65870322 to "Сан-Томе и Принсипи",
        100999433 to "Эсватини"
    )
}