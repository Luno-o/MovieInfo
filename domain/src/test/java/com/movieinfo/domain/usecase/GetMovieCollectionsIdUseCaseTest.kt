package com.movieinfo.domain.usecase

import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetMovieCollectionsIdUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = GetMovieCollectionsIdUseCase(mainMovieRepository = testRepository)

    @Test
    fun `should return the same data as in repository`() {
        runBlocking {
            Mockito.`when`(testRepository.getMovieCollectionId(1))
                .thenReturn(listOf(1))
            val actual = useCase(1)
            assertEquals(listOf(1), actual)
        }
    }

    @Test
    fun `should return null`() {
        runBlocking {
            Mockito.`when`(testRepository.getMovieCollectionId(1))
                .thenReturn(null)
            val actual = useCase(1)
            assertEquals(emptyList<Int>(), actual)
        }
    }

}