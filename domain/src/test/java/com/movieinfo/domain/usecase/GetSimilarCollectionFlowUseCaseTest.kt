package com.movieinfo.domain.usecase

import com.movieinfo.domain.models.LoadStateUI
import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetSimilarCollectionFlowUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = GetSimilarCollectionFlowUseCase(mainMovieRepository = testRepository)

    @Test
    fun `should return the same data as in repository`() {
        runBlocking {
            Mockito.`when`(testRepository.loadSimilarMovieFlow(1))
                .thenReturn(flowOf(LoadStateUI.Success(listOf( fakeMovieCollection))))
            val actual = useCase(1)
            assertEquals(fakeMovieCollection, (actual.last() as LoadStateUI.Success).data.first())
        }
    }

    @Test
    fun `should return error`() {
        runBlocking {
            Mockito.`when`(testRepository.loadSimilarMovieFlow(0))
                .thenReturn(flowOf(LoadStateUI.Error(Throwable("error"))))

            val actual = useCase(0)
            assertEquals(
                "error",
                (actual.first() as LoadStateUI.Error).throwable.message
            )

        }
    }
    @Test
    fun `should return loading`() {
        runBlocking {
            Mockito.`when`(testRepository.loadSimilarMovieFlow(Mockito.anyInt()))
                .thenReturn(flowOf(LoadStateUI.Loading))
            val actual = useCase(0)
            assertEquals(
                LoadStateUI.Loading,
                (actual.first() as LoadStateUI.Loading)
            )

        }
    }
}