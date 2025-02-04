package com.movieinfo.domain.usecase

import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class DeleteHistoryUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = DeleteHistoryUseCase(mainMovieRepository = testRepository)

    @Test
    fun `should remove movie`() {
        runBlocking {
            Mockito.`when`(testRepository.getCollectionById(1))
                .thenReturn(listOf(movieDb))
            useCase(1)
            Mockito.verify(testRepository,Mockito.times(1)).removeMovie(movieDb)
        }
    }

    @Test
    fun `should remove movie from collection`() {
        runBlocking {
            Mockito.`when`(testRepository.getCollectionById(1))
                .thenReturn(listOf(movieDb.copy(collectionId = listOf(1,2))))
            useCase(1)
            Mockito.verify(testRepository,Mockito.times(1)).updateMovie(movieDb.copy(listOf(2)))
        }
    }

}