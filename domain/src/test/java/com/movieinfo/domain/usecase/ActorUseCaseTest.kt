package com.movieinfo.domain.usecase

import com.movieinfo.domain.repository.MainMovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

//class ActorUseCaseTest {
//    @AfterEach
//    fun afterDown(){
//
//    }
//    @BeforeEach
//    fun beforeEach(){
//
//    }
//    val testRepository = mock<MainMovieRepository>()
//    @Test
//     fun `should return the same data as in repository`(){
//         runBlocking {
//        Mockito.`when`(testRepository.loadStaffById(2))
//        val useCase = ActorUseCase(mainMovieRepository = testRepository)
//        val expected =
//            val actual = useCase(2)
//        Assertions.assertEquals()
//         }
//    }
//}