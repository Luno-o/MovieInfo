package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.Episode
import com.movieinfo.domain.entity.SerialWrapper
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

class GetSeasonsUseCaseTest{
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()
    val useCase = GetSeasonsUseCase(mainMovieRepository = testRepository)

    @Test
    fun `should return the same data as in repository`() {
        runBlocking {
            Mockito.`when`(testRepository.loadSeasons(1))
                .thenReturn(flowOf(LoadStateUI.Success(listOf( serialWrapper))))
            val actual = useCase(1)
            assertEquals(serialWrapper, (actual.last() as LoadStateUI.Success).data.first())
        }
    }

    @Test
    fun `should return error`() {
        runBlocking {
            Mockito.`when`(testRepository.loadSeasons(0))
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
            Mockito.`when`(testRepository.loadSeasons(Mockito.anyInt()))
                .thenReturn(flowOf(LoadStateUI.Loading))
            val actual = useCase(0)
            assertEquals(
                LoadStateUI.Loading,
                (actual.first() as LoadStateUI.Loading)
            )

        }
    }
}
val episode = EpisodesImpl(1, 2, null, null, null, null)
val serialWrapper = SerialWrapperImpl(2, listOf(episode))
class SerialWrapperImpl(override val number: Int, override val episodes: List<Episode>): SerialWrapper
class EpisodesImpl(
    override val seasonNumber: Int,
    override val episodeNumber: Int,
    override val nameRu: String?,
    override val nameEng: String?,
    override val synopsys: String?,
    override val releasedDate: String?
) : Episode