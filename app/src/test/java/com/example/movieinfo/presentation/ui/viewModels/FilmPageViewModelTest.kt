package com.example.movieinfo.presentation.ui.viewModels

import app.cash.turbine.test
import com.example.movieinfo.presentation.ui.layout.onboarding.emptyMovieCard
import com.example.movieinfo.presentation.ui.layout.onboarding.fakeMovieBaseInfo
import com.movieinfo.domain.entity.Episode
import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.entity.MovieGallery
import com.movieinfo.domain.entity.SerialWrapper
import com.movieinfo.domain.entity.Staff
import com.movieinfo.domain.models.LoadStateUI
import com.movieinfo.domain.usecase.AddToMyCollectionUseCase
import com.movieinfo.domain.usecase.GetCollectionByNameUseCase
import com.movieinfo.domain.usecase.GetFilmUseCase
import com.movieinfo.domain.usecase.GetMovieCollectionsIdUseCase
import com.movieinfo.domain.usecase.GetMovieGalleryFlowUseCase
import com.movieinfo.domain.usecase.GetMovieGalleryUseCase
import com.movieinfo.domain.usecase.GetMyCollectionsUseCase
import com.movieinfo.domain.usecase.GetSeasonsUseCase
import com.movieinfo.domain.usecase.GetSimilarCollectionFlowUseCase
import com.movieinfo.domain.usecase.GetStaffByFilmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class FilmPageViewModelTest{

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()


    val getFilmUseCase: GetFilmUseCase = mock()
    private val getSeasonsUseCase: GetSeasonsUseCase = mock()
    private val getMovieCollectionsIdUseCase: GetMovieCollectionsIdUseCase = mock()
    private val getStaffByFilmUseCase: GetStaffByFilmUseCase = mock()
    private val getMovieGalleryUseCase: GetMovieGalleryUseCase = mock()
    private val getMovieGalleryFlowUseCase: GetMovieGalleryFlowUseCase = mock()
    private val addToMyCollectionUseCase: AddToMyCollectionUseCase = mock()
    private val getMyCollectionsUseCase: GetMyCollectionsUseCase = mock()
    private val getCollectionByNameUseCase: GetCollectionByNameUseCase = mock()
    private val getSimilarCollectionFlowUseCase: GetSimilarCollectionFlowUseCase = mock()

    private lateinit var viewModel: FilmPageViewModel

    @BeforeEach
    fun setUp() {
        viewModel = FilmPageViewModel(getFilmUseCase,getSeasonsUseCase,getMovieCollectionsIdUseCase,
            getStaffByFilmUseCase,getMovieGalleryUseCase,
            getMovieGalleryFlowUseCase,addToMyCollectionUseCase,getMyCollectionsUseCase,
            getCollectionByNameUseCase,getSimilarCollectionFlowUseCase)
    }


    @Test
    fun `should correctly get movie`() = runTest {
        `when`(getFilmUseCase(1))
            .thenReturn(flowOf(LoadStateUI.Success(data = fakeMovieBaseInfo)))
        withContext(Dispatchers.Default) {
            viewModel.movie.test {
                assertTrue(awaitItem() is LoadStateUI.Loading)
                viewModel.loadMovieById(1)
                assertEquals(fakeMovieBaseInfo, (awaitItem() as LoadStateUI.Success).data)
                cancelAndIgnoreRemainingEvents()
            }
        }

    }
    @Test
    fun `should correctly get seasons`() = runTest {
        `when`(getSeasonsUseCase(1))
            .thenReturn(flowOf(LoadStateUI.Success(data = listOf( serialWrapper))))
        withContext(Dispatchers.Default) {
            viewModel.seasons.test {
                assertTrue(awaitItem() is LoadStateUI.Loading)
                viewModel.loadSeasons(1)
                assertEquals(serialWrapper, (awaitItem() as LoadStateUI.Success).data.first())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `should get staff by film`() = runTest {
            `when`(getStaffByFilmUseCase(1))
                .thenReturn(flowOf(LoadStateUI.Success(data = listOf(staff))))
            withContext(Dispatchers.Default) {
                viewModel.staffByFilm.test {
                    assertTrue(awaitItem() is LoadStateUI.Loading)
                    viewModel.loadStaffByFilmId(1)
                    assertEquals(staff, (awaitItem() as LoadStateUI.Success).data.first())
                    cancelAndIgnoreRemainingEvents()
                }
            }
        }

    @Test
    fun `should get gallery by film`() = runTest {
        `when`(getMovieGalleryFlowUseCase(1,GalleryType.SHOOTING))
            .thenReturn(flowOf(LoadStateUI.Success(data = listOf(gallery))))
        withContext(Dispatchers.Default) {
            viewModel.movieGallery.test {
                assertTrue(awaitItem() is LoadStateUI.Loading)
                viewModel.loadMovieGallery(1)
                assertEquals(gallery, (awaitItem() as LoadStateUI.Success).data.first())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
    @Test
    fun `should get similar film`() = runTest {
        `when`(getSimilarCollectionFlowUseCase(1))
            .thenReturn(flowOf(LoadStateUI.Success(data = listOf(emptyMovieCard))))
        withContext(Dispatchers.Default) {
            viewModel.similarMovies.test {
                assertTrue(awaitItem() is LoadStateUI.Loading)
                viewModel.loadSimilarMovie(1)
                assertEquals(emptyMovieCard, (awaitItem() as LoadStateUI.Success).data.first())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}

val gallery = MovieGalleryImpl("url","prewvurl")
class MovieGalleryImpl(override val imageUrl: String, override val previewUrl: String) :
    MovieGallery
val episode = EpisodesImpl(1, 2, null, null, null, null)
val serialWrapper = SerialWrapperImpl(2, listOf(episode))
class SerialWrapperImpl(override val number: Int, override val episodes: List<Episode>):
    SerialWrapper
class EpisodesImpl(
    override val seasonNumber: Int,
    override val episodeNumber: Int,
    override val nameRu: String?,
    override val nameEng: String?,
    override val synopsys: String?,
    override val releasedDate: String?
) : Episode
val staff = StaffImpl(
    1, "nameru", "nameEng", "description",
    "poster", "profession", "key"
)
class StaffImpl(
    override val staffId: Int,
    override val nameRU: String?,
    override val nameENG: String?,
    override val description: String?,
    override val posterUrl: String?,
    override val professionText: String?,
    override val professionKey: String?
): Staff