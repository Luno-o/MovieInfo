package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetCollectionByNameUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = GetCollectionByNameUseCase(mainMovieRepository = testRepository)

    @Test
    fun `should return my collection by name`() {
        runBlocking {
            Mockito.`when`(testRepository.getMyCollections())
                .thenReturn(listOf(myMovieCollections))
            Mockito.`when`(testRepository.getCollectionById(1))
                .thenReturn(listOf(movieDb))

            assertEquals(listOf(movieDb), useCase("collection"))
        }
    }

    @Test
    fun `should return empty list`() {
        runBlocking {
            Mockito.`when`(testRepository.getMyCollections())
                .thenReturn(listOf())
            assertEquals(listOf<MovieDb>(), useCase("collection"))
        }
    }

}