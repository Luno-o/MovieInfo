package com.movieinfo.domain.usecase

import com.movieinfo.domain.entity.MovieForStaff
import com.movieinfo.domain.entity.Spouses
import com.movieinfo.domain.entity.StaffFullInfo
import com.movieinfo.domain.models.LoadStateUI
import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ActorUseCaseTest {
    @AfterEach
    fun afterDown() {

    }

    @BeforeEach
    fun beforeEach() {

    }

    private val testRepository = mock<MainMovieRepository>()

    @Test
    fun `should return the same data as in repository`() {
        runBlocking {
            Mockito.`when`(testRepository.loadStaffById(1))
                .thenReturn(flowOf(LoadStateUI.Success(staffFullInfo)))
            val useCase = ActorUseCase(mainMovieRepository = testRepository)
            val actual = useCase(1)
            Assertions.assertEquals(staffFullInfo, (actual.first() as LoadStateUI.Success).data)
        }
    }

    @Test
    fun `should return error`() {
        runBlocking {
            Mockito.`when`(testRepository.loadStaffById(0))
                .thenReturn(flowOf(LoadStateUI.Error(Throwable("error"))))
            val useCase = ActorUseCase(mainMovieRepository = testRepository)
            val actual = useCase(0)
            Assertions.assertEquals(
                "error",
                (actual.first() as LoadStateUI.Error).throwable.message
            )

        }
    }
    @Test
    fun `should return loading`() {
        runBlocking {
                Mockito.`when`(testRepository.loadStaffById(Mockito.anyInt()))
                    .thenReturn(flowOf(LoadStateUI.Loading))
                val useCase = ActorUseCase(mainMovieRepository = testRepository)
                val actual = useCase(0)
                Assertions.assertEquals(
                    LoadStateUI.Loading,
                    (actual.first() as LoadStateUI.Loading)
                )

        }
    }
}

val staffFullInfo = StaffFullInfoImpl(
    personId = 1,
    webUrl = null,
    age = 10,
    birthPlace = null,
    nameRU = null,
    nameEN = null,
    sex = null,
    growth = 10,
    birthday = null,
    death = null,
    deathPlace = null,
    hasAwards = null,
    profession = null,
    facts = listOf(),
    spouses = listOf(),
    films = listOf(),
    posterUrl = null
)

class StaffFullInfoImpl(
    override val personId: Int,
    override val webUrl: String?,
    override val nameRU: String?,
    override val nameEN: String?,
    override val sex: String?,
    override val posterUrl: String?,
    override val growth: Int,
    override val birthday: String?,
    override val death: String?,
    override val age: Int,
    override val birthPlace: String?,
    override val deathPlace: String?,
    override val hasAwards: Int?,
    override val profession: String?,
    override val facts: List<String?>,
    override val spouses: List<Spouses>,
    override val films: List<MovieForStaff>
) : StaffFullInfo