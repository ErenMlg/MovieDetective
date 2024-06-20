package com.softcross.moviedetective.data.remote

import androidx.paging.PagingSource
import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.data.dto.MovieDetailDto
import com.softcross.moviedetective.data.dto.genre.GenreResponse
import com.softcross.moviedetective.data.dto.movies.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY}")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): MoviesResponse

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getTrendMovies(): MoviesResponse

    @GET("movie/upcoming?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getComingSoonMovies(): MoviesResponse

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieByGenre(@Query("with_genres") genreID: String): MoviesResponse

    @GET("movie/{movieID}?api_key=${BuildConfig.API_KEY}")
    suspend fun getSingleMovie(@Path("movieID") movieID: Int): MovieDetailDto

    @GET("genre/movie/list?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieGenres(): GenreResponse

    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY}")
    fun pagingSource(): PagingSource<Int, MoviesResponse>
}