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

class GetMovieFromDbUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = GetMovieFromDbUseCase(mainMovieRepository = testRepository)

    @Test
    fun `should return the same data as in repository`() {
        runBlocking {
            Mockito.`when`(testRepository.getMovieFromDB(1))
                .thenReturn(movieDb)
            val actual = useCase(1)
            assertEquals(movieDb, actual)
        }
    }

    @Test
    fun `should return null`() {
        runBlocking {
            Mockito.`when`(testRepository.getMovieFromDB(1))
                .thenReturn(null)
            val actual = useCase(1)
            assertEquals(null, actual)
        }
    }

}
val movieDb = MovieDb(
        listOf(0),
1,
null, "name", "nameEn",
"maneOr", null, null, null, null,
null, null, null, null
)