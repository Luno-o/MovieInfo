package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetMovieGalleryUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = GetMovieGalleryUseCase(mainMovieRepository = testRepository)

    @Test
    fun `should return the same data as in repository`() {
        runBlocking {
            Mockito.`when`(testRepository.getMovieGallery(1,GalleryType.POSTER))
                .thenReturn(listOf(gallery))
            val actual = useCase(1,GalleryType.POSTER)
            assertEquals(listOf(gallery), actual)
        }
    }

    @Test
    fun `should return null`() {
        runBlocking {
            Mockito.`when`(testRepository.getMovieGallery(1,GalleryType.POSTER))
                .thenReturn(null)
            val actual = useCase(1,GalleryType.POSTER)
            assertEquals(null, actual)
        }
    }
}