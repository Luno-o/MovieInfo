package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.MyMovieCollections
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

class ShowMyCollectionFlowUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = ShowMyCollectionFlowUseCase(mainMovieRepository = testRepository)

    @Test
    fun `should return the same data as in repository`() {
        runBlocking {
            Mockito.`when`(testRepository.getMyCollections())
                .thenReturn(listOf(myMovieCollections))
                Mockito.`when`(testRepository.getCollectionByNameFlow(1))
                .thenReturn(flowOf(LoadStateUI.Success(listOf( movieDb))))
            val actual = useCase(myMovieCollections.collectionName)
            assertEquals(movieDb, (actual.last() as LoadStateUI.Success).data.first())
        }
    }

    @Test
    fun `should return error`() {
        runBlocking {
            Mockito.`when`(testRepository.getMyCollections())
                .thenReturn(listOf(myMovieCollections))
            Mockito.`when`(testRepository.getCollectionByNameFlow(1))
                .thenReturn(flowOf(LoadStateUI.Error(Throwable("error"))))

            val actual = useCase(myMovieCollections.collectionName)
            assertEquals(
                "error",
                (actual.first() as LoadStateUI.Error).throwable.message
            )

        }
    }
}
val myMovieCollections = MyMovieCollectionsImpl(1,"collection")
class MyMovieCollectionsImpl(override val id: Int, override val collectionName: String) : MyMovieCollections