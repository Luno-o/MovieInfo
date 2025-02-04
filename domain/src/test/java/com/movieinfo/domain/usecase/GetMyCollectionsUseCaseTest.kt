package com.movieinfo.domain.usecase

import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetMyCollectionsUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = GetMyCollectionsUseCase(mainMovieRepository = testRepository)

    @Test
    fun `should add movie`() {
        runBlocking {
            useCase()
            Mockito.verify(testRepository, Mockito.times(1)).getMyCollections()
        }
    }


}