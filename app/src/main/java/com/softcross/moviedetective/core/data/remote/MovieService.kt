package com.softcross.moviedetective.core.data.remote

import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.core.data.model.dto.movies.MoviesResponse
import com.softcross.moviedetective.core.domain.model.Genre
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService{
    @GET("movie/top_rated=${BuildConfig.API_KEY}")
    suspend fun getTop20Movie() : MoviesResponse

    @GET("movie/popular=${BuildConfig.API_KEY}")
    suspend fun getTrendMovies() : MoviesResponse

    @GET("movie/upcoming=${BuildConfig.API_KEY}&region=US")
    suspend fun getComingSoonMovies() : MoviesResponse

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&with_genres={genres}")
    suspend fun getMovieByGenre(@Path("genres") genres: String) : MoviesResponse

    @GET("person/{actorID}/movie_credits?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieByActor(@Path("actorID") actorID: Int) : MoviesResponse

}