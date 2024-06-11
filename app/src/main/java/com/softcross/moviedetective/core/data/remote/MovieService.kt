package com.softcross.moviedetective.core.data.remote

import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.core.data.dto.MovieDetailDto
import com.softcross.moviedetective.core.data.dto.movies.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY}")
    suspend fun getTop20Movie(): MoviesResponse

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getTrendMovies(): MoviesResponse

    @GET("movie/upcoming?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getComingSoonMovies(): MoviesResponse

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieByGenre(@Query("with_genres") genreID: String): MoviesResponse

    @GET("movie/{movieID}?api_key=${BuildConfig.API_KEY}")
    suspend fun getSingleMovie(@Path("movieID") movieID: Int) : MovieDetailDto
}