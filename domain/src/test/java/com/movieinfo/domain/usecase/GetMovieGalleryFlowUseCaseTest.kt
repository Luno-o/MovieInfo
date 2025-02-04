package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.GalleryType
import com.movieinfo.domain.entity.MovieGallery
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

class GetMovieGalleryFlowUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = GetMovieGalleryFlowUseCase(mainMovieRepository = testRepository)

    @Test
    fun `should return the same data as in repository`() {
        runBlocking {
            Mockito.`when`(testRepository.getMovieGalleryFlow(1,GalleryType.POSTER))
                .thenReturn(flowOf(LoadStateUI.Success(listOf( gallery))))
            val actual = useCase(1,GalleryType.POSTER)
            assertEquals(gallery, (actual.last() as LoadStateUI.Success).data.last())
        }
    }

    @Test
    fun `should return error`() {
        runBlocking {
            Mockito.`when`(testRepository.getMovieGalleryFlow(0,GalleryType.POSTER))
                .thenReturn(flowOf(LoadStateUI.Error(Throwable("error"))))

            val actual = useCase(0,GalleryType.POSTER)
            assertEquals(
                "error",
                (actual.first() as LoadStateUI.Error).throwable.message
            )

        }
    }
    @Test
    fun `should return loading`() {
        runBlocking {
            Mockito.`when`(testRepository.getMovieGalleryFlow(0,GalleryType.POSTER))
                .thenReturn(flowOf(LoadStateUI.Loading))
            val actual = useCase(0,GalleryType.POSTER)
            assertEquals(
                LoadStateUI.Loading,
                (actual.first() as LoadStateUI.Loading)
            )

        }
    }
}
val gallery = MovieGalleryImpl("url","prewvurl")
class MovieGalleryImpl(override val imageUrl: String, override val previewUrl: String) : MovieGallery