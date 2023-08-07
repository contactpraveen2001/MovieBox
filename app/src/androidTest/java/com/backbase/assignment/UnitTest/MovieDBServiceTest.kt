package com.backbase.assignment.UnitTest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.backbase.assignment.FakeNetworkCall
import com.backbase.assignment.ui.util.MovieDBService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDBServiceTest {
    lateinit var movieDBService: MovieDBService
    lateinit var fakeNetworkCall: FakeNetworkCall
    @Before
    fun setup() {
        fakeNetworkCall = FakeNetworkCall()
        movieDBService = MovieDBService(fakeNetworkCall)
    }

    @Test
    fun getNowPlayingMoviePosters_Success() {
        runBlocking {
            // action
            val result = movieDBService.getNowPlayingMoviePosters()
            // assert
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun getNowPlayingMoviePosters_Fail() {
        runBlocking {
            //setup
            var error: Exception? = null
            fakeNetworkCall.isFail = true
            movieDBService.onError = { exception -> error = exception }
            // action
            val result = movieDBService.getNowPlayingMoviePosters()
            // assert
            assert(result.isEmpty())
            assert(error != null)
        }
    }

    @Test
    fun getPopularMovies_Success() {
        runBlocking {
            // action
            val result = movieDBService.getPopularMovies(1)
            // assert
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun getPopularMovies_Fail() {
        runBlocking {
            //setup
            var error: Exception? = null
            fakeNetworkCall.isFail = true
            movieDBService.onError = { exception -> error = exception }
            // action
            val result = movieDBService.getPopularMovies(1)
            // assert
            assert(result.isEmpty())
            assert(error != null)
        }
    }

    @Test
    fun getMovieDetails_Success() {
        runBlocking {
            // action
            val result = movieDBService.getMovieDetails("464052")
            // assert
            assert(result != null)
        }
    }

    @Test
    fun getMovieDetails_Fail() {
        runBlocking {
            //setup
            var error: Exception? = null
            fakeNetworkCall.isFail = true
            movieDBService.onError = { exception -> error = exception }
            // action
            val result = movieDBService.getMovieDetails("464052")
            // assert
            assert(result == null)
            assert(error != null)
        }
    }
}