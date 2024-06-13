package com.softcross.moviedetective.core.data.source.remote

import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.data.dto.MovieDetailDto
import com.softcross.moviedetective.core.data.dto.actors.ActorResponse
import com.softcross.moviedetective.core.data.dto.genre.GenreDto
import com.softcross.moviedetective.core.data.dto.genre.GenreResponse
import com.softcross.moviedetective.core.data.dto.movies.MoviesResponse
import com.softcross.moviedetective.core.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    //Movies
    fun getTop20Movie(): Flow<NetworkResponseState<MoviesResponse>>
    fun getTrendMovies(): Flow<NetworkResponseState<MoviesResponse>>
    fun getComingSoonMovies(): Flow<NetworkResponseState<MoviesResponse>>
    fun getMovieByGenre(genres: String): Flow<NetworkResponseState<MoviesResponse>>
    fun getSingleMovie(movieID: Int): Flow<NetworkResponseState<MovieDetailDto>>

    fun getMovieGenres(): Flow<NetworkResponseState<GenreResponse>>

    fun getPopularActors(): Flow<NetworkResponseState<ActorResponse>>

    /*
    //Series
    fun getTop20Series()
    fun getTrendSeries()
    fun getComingSoonSeries()
    fun getSeriesByGenre()

    //Actors

    fun getMoviesByActor(): MoviesResponse
    fun getSeriesByActor()
*/
}