package com.softcross.moviedetective.core.data.source.remote

import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.data.dto.MovieDetailDto
import com.softcross.moviedetective.core.data.dto.movies.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    //Movies
    fun getTop20Movie(): Flow<NetworkResponseState<MoviesResponse>>
    fun getTrendMovies(): Flow<NetworkResponseState<MoviesResponse>>
    fun getComingSoonMovies(): Flow<NetworkResponseState<MoviesResponse>>
    fun getMovieByGenre(genres: String): Flow<NetworkResponseState<MoviesResponse>>
    fun getSingleMovie(movieID: Int): Flow<NetworkResponseState<MovieDetailDto>>

    /*
    //Series
    fun getTop20Series()
    fun getTrendSeries()
    fun getComingSoonSeries()
    fun getSeriesByGenre()

    //Actors
    fun getPopularActors()
    fun getMoviesByActor(): MoviesResponse
    fun getSeriesByActor()
*/
}