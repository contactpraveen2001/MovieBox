package com.backbase.assignment.UnitTest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.backbase.assignment.ui.util.NetworkCall
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NetworkCallTest {

    lateinit var networkCall: NetworkCall

    @Before
    fun setupNetworkCall() {
        networkCall = NetworkCall()
    }

    @Test
    fun getJsonString_Success() {
        runBlocking {
            // action
            val result =
                networkCall.getJsonString("https://api.themoviedb.org/3/movie/464052?api_key=55957fcf3ba81b137f8fc01ac5a31fb5&language=en-US")
            // assert
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun getJsonString_Fail() {
        runBlocking {
            //setup
            var error: Exception? = null
            networkCall.onError = { exception -> error = exception }
            // action
            val result =
                networkCall.getJsonString("https://api.themoviedb.org")
            // assert
            assert(result.isEmpty())
            assert(error != null)
        }
    }
}