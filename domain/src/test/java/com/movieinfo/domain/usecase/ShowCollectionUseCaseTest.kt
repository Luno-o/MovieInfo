package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.CollectionType
import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ShowCollectionUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = ShowCollectionUseCase(mainMovieRepository = testRepository)

    @Test
    fun `should return the same data as in repository`() {
        runBlocking {
            Mockito.`when`(testRepository.loadCollection(CollectionType.PREMIERES, 1))
                .thenReturn(listOf(fakeMovieCollection))
            val actual = useCase(CollectionType.PREMIERES, 1)
            assertEquals(listOf(fakeMovieCollection), actual)
        }
    }

    @Test
    fun `should return null`() {
        runBlocking {
            Mockito.`when`(testRepository.loadCollection(CollectionType.PREMIERES, 1))
                .thenReturn(null)
            val actual = useCase(CollectionType.PREMIERES, 1)
            assertEquals(null, actual)
        }
    }
}