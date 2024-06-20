package com.softcross.moviedetective.repository

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.softcross.moviedetective.SINGLE_MOVIE_ID
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.extensions.mapResponse
import com.softcross.moviedetective.firstItemComingMovies
import com.softcross.moviedetective.firstItemTopMovies
import com.softcross.moviedetective.firstItemTrendMovies
import com.softcross.moviedetective.mappedComingMovieListFirstItem
import com.softcross.moviedetective.mappedSingleMovie
import com.softcross.moviedetective.mappedTopMovieListFirstItem
import com.softcross.moviedetective.mappedTrendMovieListFirstItem
import com.softcross.moviedetective.singleMovie
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class FakeContentRepositoryTest {

    private val fakeContentRepository = FakeContentRepository()

    @Test
    fun top20MovieResponse_whenTop20MovieRequestedMapped_hasSame() = runBlocking {
        fakeContentRepository.updateMoviesResultState(data = listOf(firstItemTopMovies))
        fakeContentRepository.getPopularMovies().test {
            val response = awaitItem()
            Assert.assertEquals(
                NetworkResponseState.Success(mappedTopMovieListFirstItem),
                response.mapResponse { this.first() })
        }
    }

    @Test
    fun top20MovieResponse_whenTop20MovieRequestedReturnError_returnError() = runBlocking {
        fakeContentRepository.updateMoviesResultState(isError = true)
        fakeContentRepository.getPopularMovies().test {
            Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Error::class.java)
        }
    }

    @Test
    fun trendMovieResponse_whenTrendMovieRequestedMapped_hasSame() = runBlocking {
        fakeContentRepository.updateMoviesResultState(data = listOf(firstItemTrendMovies))
        fakeContentRepository.getTrendMovies().test {
            val response = awaitItem()
            Assert.assertEquals(
                NetworkResponseState.Success(mappedTrendMovieListFirstItem),
                response.mapResponse { this.first() })
        }
    }

    @Test
    fun trendMovieResponse_whenTrendMovieRequestedError_returnError() = runBlocking {
        fakeContentRepository.updateMoviesResultState(isError = true)
        fakeContentRepository.getTrendMovies().test {
            Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Error::class.java)
        }
    }

    @Test
    fun comingSoonResponse_whenComingSoonRequestedMapped_hasSame() = runBlocking {
        fakeContentRepository.updateMoviesResultState(data = listOf(firstItemComingMovies))
        fakeContentRepository.getComingSoonMovies().test {
            val response = awaitItem()
            Assert.assertEquals(
                NetworkResponseState.Success(mappedComingMovieListFirstItem),
                response.mapResponse { this.first() })
        }
    }

    @Test
    fun comingSoonResponse_whenComingSoonRequestError_returnError() = runBlocking {
        fakeContentRepository.updateMoviesResultState(isError = true)
        fakeContentRepository.getComingSoonMovies().test {
            Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Error::class.java)
        }
    }

    @Test
    fun singleMovieResponse_whenSingleMovieRequestMapped_hasSame() = runBlocking {
        fakeContentRepository.updateSingleMovieResultState(data = singleMovie)
        fakeContentRepository.getSingleMovie(SINGLE_MOVIE_ID).test {
            val response = awaitItem()
            Assert.assertEquals(
                NetworkResponseState.Success(mappedSingleMovie),
                response.mapResponse { this } )
        }
    }

    @Test
    fun singleMovieResponse_whenSingleMovieRequestError_returnError() = runBlocking {
        fakeContentRepository.updateSingleMovieResultState(isError = true)
        fakeContentRepository.getSingleMovie(SINGLE_MOVIE_ID).test {
            Truth.assertThat(awaitItem()).isInstanceOf(NetworkResponseState.Error::class.java)
        }
    }

}