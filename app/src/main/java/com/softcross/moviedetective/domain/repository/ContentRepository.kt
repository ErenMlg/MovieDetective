package com.softcross.moviedetective.domain.repository

import androidx.paging.Pager
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.Genre
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface ContentRepository {

    fun getPopularMovies(page: Int = 1): Flow<NetworkResponseState<List<Movie>>>
    fun getPopularMoviesByPage(): Pager<Int, Movie>
    fun getTrendMovies(): Flow<NetworkResponseState<List<Movie>>>
    fun getTrendMoviesByPage(): Pager<Int, Movie>
    fun getComingSoonMovies(): Flow<NetworkResponseState<List<Movie>>>
    fun getComingMoviesByPage(): Pager<Int, Movie>
    fun getMovieByGenre(genres: String): Flow<NetworkResponseState<List<Movie>>>
    fun getMoviesByGenreByPage(genres: String): Pager<Int, Movie>
    fun getMovieDetails(movieID: Int): Flow<NetworkResponseState<MovieDetail>>
    fun getActorMovies(actorID: Int): Flow<NetworkResponseState<List<Movie>>>

    //Genre
    fun getMovieGenres(): Flow<NetworkResponseState<List<Genre>>>

    //Actors
    fun getPopularActors(): Flow<NetworkResponseState<List<Actor>>>
    fun getPopularActorsByPage(): Pager<Int, Actor>

}