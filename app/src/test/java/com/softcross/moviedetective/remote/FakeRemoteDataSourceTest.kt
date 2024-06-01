package com.softcross.moviedetective.remote

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.softcross.moviedetective.core.common.NetworkResponseState
import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class FakeRemoteDataSourceTest {

    private val fakeRemoteDataSource = FakeRemoteDataSource()

    /**
     * Top Movie Response Test
     */
    @Test
    fun topMovieResponse_whenRemoteDataSourceReturnSuccess_returnSuccess(){
        runBlocking {
            fakeRemoteDataSource.getTop20Movie().test {
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Success::class.java)
                awaitComplete()
            }
        }
    }

    @Test
    fun topMovieResponse_whenRemoteDataSourceReturnError_returnError(){
        runBlocking {
            fakeRemoteDataSource.updateShowErrorForTopMovies(true)
            fakeRemoteDataSource.getTop20Movie().test {
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Error::class.java)
                awaitComplete()
            }
        }
    }

    /**
     * Trend Movie Response Test
     */
    @Test
    fun trendMovieResponse_whenRemoteDataSourceReturnSuccess_returnSuccess(){
        runBlocking {
            fakeRemoteDataSource.getTrendMovies().test {
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Success::class.java)
                awaitComplete()
            }
        }
    }

    @Test
    fun trendMovieResponse_whenRemoteDataSourceReturnError_returnError(){
        runBlocking {
            fakeRemoteDataSource.updateShowErrorForTrendMovies(true)
            fakeRemoteDataSource.getTrendMovies().test {
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Error::class.java)
                awaitComplete()
            }
        }
    }

    /**
     * Coming Movie Response Test
     */
    @Test
    fun comingMovieResponse_whenRemoteDataSourceReturnSuccess_returnSuccess(){
        runBlocking {
            fakeRemoteDataSource.getComingSoonMovies().test {
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Success::class.java)
                awaitComplete()
            }
        }
    }

    @Test
    fun comingMovieResponse_whenRemoteDataSourceReturnError_returnError(){
        runBlocking {
            fakeRemoteDataSource.updateShowErrorForComingMovies(true)
            fakeRemoteDataSource.getComingSoonMovies().test {
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Error::class.java)
                awaitComplete()
            }
        }
    }

    /**
     * Single Movie Response Test
     */
    @Test
    fun singleMovieResponse_whenRemoteDataSourceReturnSuccess_returnSuccess(){
        runBlocking {
            fakeRemoteDataSource.getSingleMovie(12).test {
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Success::class.java)
                awaitComplete()
            }
        }
    }

    @Test
    fun singleMovieResponse_whenRemoteDataSourceReturnError_returnError(){
        runBlocking {
            fakeRemoteDataSource.updateShowErrorForSingleMovie(true)
            fakeRemoteDataSource.getSingleMovie(12).test {
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Loading::class.java)
                Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Error::class.java)
                awaitComplete()
            }
        }
    }


}