package com.softcross.moviedetective.core.domain.repository

import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.domain.model.Actor
import com.softcross.moviedetective.core.domain.model.Genre
import com.softcross.moviedetective.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface ContentRepository {

    fun getTop20Movie(): Flow<NetworkResponseState<List<Movie>>>
    fun getTrendMovies(): Flow<NetworkResponseState<List<Movie>>>
    fun getComingSoonMovies(): Flow<NetworkResponseState<List<Movie>>>
    fun getMovieByGenre(genres: String): Flow<NetworkResponseState<List<Movie>>>
    fun getSingleMovie(movieID: Int): Flow<NetworkResponseState<Movie>>

    //Genre
    fun getMovieGenres(): Flow<NetworkResponseState<List<Genre>>>

    //Actors
    fun getPopularActors(): Flow<NetworkResponseState<List<Actor>>>

}