package com.softcross.moviedetective.repository

import com.google.common.truth.Truth
import com.softcross.moviedetective.COMING_SOON_FILE_NAME
import com.softcross.moviedetective.SERVER_PORT
import com.softcross.moviedetective.SINGLE_MOVIE_FILE_NAME
import com.softcross.moviedetective.SINGLE_MOVIE_ID
import com.softcross.moviedetective.TOP_20_MOVIE_FILE_NAME
import com.softcross.moviedetective.TREND_MOVIE_FILE_NAME
import com.softcross.moviedetective.core.data.mapper.MovieResponseListMapper
import com.softcross.moviedetective.core.data.remote.MovieService
import com.softcross.moviedetective.core.domain.model.Movie
import com.softcross.moviedetective.mappedComingMovieListFirstItem
import com.softcross.moviedetective.mappedSingleMovie
import com.softcross.moviedetective.mappedTopMovieListFirstItem
import com.softcross.moviedetective.mappedTrendMovieListFirstItem

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class ContentRepositoryTest {

    private val mapper = MovieResponseListMapper()
    private val mockWebServer: MockWebServer = MockWebServer()
    private lateinit var movieService: MovieService

    @Before
    fun setup() {
        mockWebServer.start(port = SERVER_PORT)
        movieService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @Test
    fun firstMovie_whenTop20MovieRequestedMapped_hasSame() {
        runBlocking {
            enqueueMockResponse(TOP_20_MOVIE_FILE_NAME)
            val response = movieService.getTop20Movie()
            Truth.assertThat(mapper.map(response.data).first()).isEqualTo(
                mappedTopMovieListFirstItem
            )
        }
    }

    @Test
    fun firstMovie_whenTrendMoviesRequestedMapped_hasSame() {
        runBlocking {
            enqueueMockResponse(TREND_MOVIE_FILE_NAME)
            val response = movieService.getTrendMovies()
            Truth.assertThat(mapper.map(response.data).first())
                .isEqualTo(mappedTrendMovieListFirstItem)
        }
    }

    @Test
    fun firstMovie_whenComingMoviesRequestedMapped_hasSame() {
        runBlocking {
            enqueueMockResponse(COMING_SOON_FILE_NAME)
            val response = movieService.getComingSoonMovies()
            Truth.assertThat(mapper.map(response.data).first())
                .isEqualTo(mappedComingMovieListFirstItem)
        }
    }

    @Test
    fun singleMovie_whenSingleMovieRequestedMapped_hasSame() {
        runBlocking {
            enqueueMockResponse(SINGLE_MOVIE_FILE_NAME)
            val response = movieService.getSingleMovie(SINGLE_MOVIE_ID)
            Truth.assertThat(response.run {
                Movie(
                    movieID = id,
                    movieName = title,
                    description = overview,
                    genres = genres.map { it.id },
                    imdb = voteAverage,
                    releaseDate = releaseDate,
                    movieImage = image ?: ""
                )
            }).isEqualTo(mappedSingleMovie)
        }
    }


    private fun enqueueMockResponse(serverResponseFileName: String) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(serverResponseFileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            mockWebServer.enqueue(mockResponse)
        }
    }

    @After
    fun shutDown() {
        mockWebServer.shutdown()
    }


}