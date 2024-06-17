package com.softcross.moviedetective.api

import com.google.common.truth.Truth.assertThat
import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.COMING_SOON_FILE_NAME
import com.softcross.moviedetective.SERVER_PORT
import com.softcross.moviedetective.SINGLE_MOVIE_FILE_NAME
import com.softcross.moviedetective.SINGLE_MOVIE_ID
import com.softcross.moviedetective.TOP_20_MOVIE_FILE_NAME
import com.softcross.moviedetective.TREND_MOVIE_FILE_NAME
import com.softcross.moviedetective.data.remote.MovieService
import com.softcross.moviedetective.firstItemComingMovies
import com.softcross.moviedetective.firstItemTopMovies
import com.softcross.moviedetective.firstItemTrendMovies
import com.softcross.moviedetective.singleMovie
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

internal class MovieServiceTest {

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

    //TOP 20 MOVIE
    @Test
    fun response_whenTop20MovieSearched_isNotNull(){
        runBlocking {
            enqueueMockResponse(TOP_20_MOVIE_FILE_NAME)
            val response = movieService.getTopMovies()
            mockWebServer.takeRequest()
            assertThat(response).isNotNull()
        }
    }

    @Test
    fun requestPath_whenTop20MovieRequested_isSameWithRequest() {
        runBlocking {
            enqueueMockResponse(TOP_20_MOVIE_FILE_NAME)
            movieService.getTopMovies()
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/movie/top_rated?api_key=${BuildConfig.API_KEY}")
        }
    }

    @Test
    fun firstElement_whenTop20MovieRequested_hasSame() {
        runBlocking {
            enqueueMockResponse(TOP_20_MOVIE_FILE_NAME)
            val response = movieService.getTopMovies()
            val firstItem = response.data.first()
            assertThat(firstItem).isEqualTo(firstItemTopMovies)
        }
    }

    //TREND MOVIES
    @Test
    fun response_whenTrendMoviesSearched_isNotNull(){
        runBlocking {
            enqueueMockResponse(TREND_MOVIE_FILE_NAME)
            val response = movieService.getTrendMovies()
            mockWebServer.takeRequest()
            assertThat(response).isNotNull()
        }
    }

    @Test
    fun requestPath_whenTrendMoviesRequested_isSameWithRequest() {
        runBlocking {
            enqueueMockResponse(TREND_MOVIE_FILE_NAME)
            movieService.getTrendMovies()
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/movie/popular?api_key=${BuildConfig.API_KEY}")
        }
    }

    @Test
    fun firstElement_whenTrendMoviesRequested_hasSame() {
        runBlocking {
            enqueueMockResponse(TREND_MOVIE_FILE_NAME)
            val response = movieService.getTrendMovies()
            val firstItem = response.data.first()
            assertThat(firstItem).isEqualTo(firstItemTrendMovies)
        }
    }

    //COMING MOVIES

    @Test
    fun response_whenComingMoviesSearched_isNotNull(){
        runBlocking {
            enqueueMockResponse(COMING_SOON_FILE_NAME)
            val response = movieService.getComingSoonMovies()
            mockWebServer.takeRequest()
            assertThat(response).isNotNull()
        }
    }

    @Test
    fun requestPath_whenComingMoviesRequested_isSameWithRequest() {
        runBlocking {
            enqueueMockResponse(COMING_SOON_FILE_NAME)
            movieService.getComingSoonMovies()
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/movie/upcoming?api_key=${BuildConfig.API_KEY}&region=US")
        }
    }

    @Test
    fun firstElement_whenComingMoviesRequested_hasSame() {
        runBlocking {
            enqueueMockResponse(COMING_SOON_FILE_NAME)
            val response = movieService.getComingSoonMovies()
            val firstItem = response.data.first()
            assertThat(firstItem).isEqualTo(firstItemComingMovies)
        }
    }

    //SINGLE MOVIE
    @Test
    fun response_whenSingleMovieSearched_isNotNull(){
        runBlocking {
            enqueueMockResponse(SINGLE_MOVIE_FILE_NAME)
            val response = movieService.getSingleMovie(SINGLE_MOVIE_ID)
            mockWebServer.takeRequest()
            assertThat(response).isNotNull()
        }
    }

    @Test
    fun requestPath_whenSingleMovieRequested_isSameWithRequest() {
        runBlocking {
            enqueueMockResponse(SINGLE_MOVIE_FILE_NAME)
            movieService.getSingleMovie(SINGLE_MOVIE_ID)
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/movie/$SINGLE_MOVIE_ID?api_key=${BuildConfig.API_KEY}")
        }
    }

    @Test
    fun firstElement_whenSingleMovieRequested_hasSame() {
        runBlocking {
            enqueueMockResponse(SINGLE_MOVIE_FILE_NAME)
            val response = movieService.getSingleMovie(SINGLE_MOVIE_ID)
            assertThat(response).isEqualTo(singleMovie)
        }
    }

    // SETUP

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