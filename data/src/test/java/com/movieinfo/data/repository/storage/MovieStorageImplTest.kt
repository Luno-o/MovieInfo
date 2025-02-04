package com.movieinfo.data.repository.storage

import com.movieinfo.data.db.FakeMovieCollectionsNameDao
import com.movieinfo.data.db.FakeMovieDBDao
import com.movieinfo.data.db.MyMovieCollectionsDb
import com.movieinfo.data.extensions.toMyMovieDb
import com.movieinfo.data.repository.fakeMovieCollectionWrapperDto
import com.movieinfo.data.repository.movieDb
import com.movieinfo.data.repository.myMovieDb
import com.movieinfo.domain.entity.MovieDb
import com.movieinfo.domain.models.LoadStateUI
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MovieStorageImplTest {

private var movieStorageImpl = MovieStorageImpl(movieCollectionsNameDao = FakeMovieCollectionsNameDao(),
    movieDao = FakeMovieDBDao())
    @BeforeEach
    fun setUp() {
        movieStorageImpl = MovieStorageImpl(movieCollectionsNameDao = FakeMovieCollectionsNameDao(),
            movieDao = FakeMovieDBDao())
    }

    @AfterEach
    fun tearDown() {
    }
    @Test
    fun `should return my collections`() = runTest {
        movieStorageImpl.addCollection("Collection")
        val response = movieStorageImpl.getMyCollections()
        assertEquals("Collection", response.first().collectionName)
    }
    @Test
    fun `test insert movie`() = runTest {
        movieStorageImpl.addMovie(
         myMovieDb
        )
        val response = movieStorageImpl.getAllMyMovies()
        assertEquals(myMovieDb, response.first())
    }
    @Test
    fun `test delete movie`() = runTest {
        movieStorageImpl.addMovie(
            myMovieDb
        )
        val response = movieStorageImpl.getAllMyMovies()
        assertEquals(myMovieDb, response.first())
        movieStorageImpl.removeMovie(myMovieDb)
        assertEquals(emptyList<MovieDb>(),movieStorageImpl.getAllMyMovies())
    }
    @Test
    fun `test insert collection`() = runTest {
        movieStorageImpl.addCollection(
            "Collection"
        )
        val response = movieStorageImpl.getMyCollections()
        assertEquals("Collection", response.first().collectionName)
    }
    @Test
    fun `test delete collection`() = runTest {
        movieStorageImpl.addCollection(
            "Collection"
        )
        val response = movieStorageImpl.getMyCollections()
        assertEquals("Collection", response.first().collectionName)

        movieStorageImpl.removeMyCollections(MyMovieCollectionsDb(0,"Collection"))
        assertEquals(emptyList<MyMovieCollectionsDb>(),movieStorageImpl.getMyCollections())
    }
    @Test
    fun `should return my collection by id`() = runTest {
        movieStorageImpl.addCollection(
            "Collection"
        )
        val response = movieStorageImpl.getCollectionById(0)
        assertEquals(myMovieDb, response.first())
    }
    @Test
    fun `should return my ids for movie`() = runTest {
        movieStorageImpl.addMovie(
            myMovieDb
        )
        val response = movieStorageImpl.getMovieCollectionId(1)
        assertEquals(myMovieDb.collectionId, response)
    }
    @Test
    fun `should return movie from db`() = runTest {
        movieStorageImpl.addMovie(
            myMovieDb
        )
        val response = movieStorageImpl.getMovieFromDB(1)
        assertEquals(myMovieDb, response)
    }

    @Test
    fun `should return my collection by name flow`() = runTest {
        movieStorageImpl.addCollection("Collection")
        val response = movieStorageImpl.getCollectionByNameFlow(0)
        assertEquals(myMovieDb, response.first().first())

    }
    @Test
    fun `should return all my movies`() = runTest {
        movieStorageImpl.addMovie(myMovieDb)
        val response = movieStorageImpl.getAllMyMovies()
        assertEquals(myMovieDb, response.first())

    }

}