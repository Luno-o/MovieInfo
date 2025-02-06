package com.example.movieinfo.presentation.ui.viewModels

import app.cash.turbine.test
import com.example.movieinfo.presentation.ui.layout.onboarding.emptyStaffFullInfo
import com.example.movieinfo.presentation.ui.layout.onboarding.staffMovie
import com.movieinfo.domain.models.LoadStateUI
import com.movieinfo.domain.usecase.ActorUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class ActorViewModelTest {

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()


    val actorUseCase: ActorUseCase = mock()

    private lateinit var viewModel: ActorViewModel

    @BeforeEach
    fun setUp() {
        viewModel = ActorViewModel(actorUseCase)
    }


    @Test
    fun `loading state should be correctly set when executing process`() = runTest {
        `when`(actorUseCase(1))
            .thenReturn(flowOf(LoadStateUI.Success(data = emptyStaffFullInfo)))
        withContext(Dispatchers.Default) {
            viewModel.staff.test {
                assertTrue(awaitItem() is LoadStateUI.Loading)
                viewModel.loadStaffById(1)
                assertFalse(awaitItem() is LoadStateUI.Loading)
                cancelAndIgnoreRemainingEvents()
            }
        }

    }
    @Test
    fun `should be success when executing process`() = runTest {
        `when`(actorUseCase(1))
            .thenReturn(flowOf(LoadStateUI.Success(data = emptyStaffFullInfo)))
        withContext(Dispatchers.Default) {
            viewModel.staff.test {
                assertTrue(awaitItem() is LoadStateUI.Loading)
                viewModel.loadStaffById(1)
                assertEquals(emptyStaffFullInfo, (awaitItem() as LoadStateUI.Success).data)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }    @Test
    fun `should be set movie for staff`() = runTest {
            `when`(actorUseCase(1))
                .thenReturn(flowOf(LoadStateUI.Success(data = emptyStaffFullInfo)))
            withContext(Dispatchers.Default) {
                viewModel.staffMovieCollection.test {
                    assertTrue(awaitItem() is LoadStateUI.Loading)
                    viewModel.loadStaffById(1)
                    assertEquals(staffMovie.filmId, (awaitItem() as LoadStateUI.Success).data.first().kpID)
                    cancelAndIgnoreRemainingEvents()
                }
            }
        }

}