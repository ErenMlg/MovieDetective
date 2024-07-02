package com.softcross.moviedetective.domain.repository

import androidx.paging.Pager
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.ActorDetail
import com.softcross.moviedetective.domain.model.Genre
import com.softcross.moviedetective.domain.model.Content
import com.softcross.moviedetective.domain.model.MovieDetail
import com.softcross.moviedetective.domain.model.SeriesDetail
import kotlinx.coroutines.flow.Flow

interface ContentRepository {

    fun getPopularMovies(): Flow<NetworkResponseState<List<Content>>>
    fun getPopularMoviesByPage(): Pager<Int, Content>
    fun getTrendMovies(): Flow<NetworkResponseState<List<Content>>>
    fun getTrendMoviesByPage(): Pager<Int, Content>
    fun getComingSoonMovies(): Flow<NetworkResponseState<List<Content>>>
    fun getComingMoviesByPage(): Pager<Int, Content>
    fun getMovieByGenre(genres: String): Flow<NetworkResponseState<List<Content>>>
    fun getMoviesByGenreByPage(genres: String): Pager<Int, Content>
    fun getMovieDetails(movieID: Int): Flow<NetworkResponseState<MovieDetail>>
    fun getActorMovies(actorID: Int): Flow<NetworkResponseState<List<Content>>>

    //Genre
    fun getMovieGenres(): Flow<NetworkResponseState<List<Genre>>>
    fun getSeriesGenres(): Flow<NetworkResponseState<List<Genre>>>

    //Actors
    fun getPopularActors(): Flow<NetworkResponseState<List<Actor>>>
    fun getPopularActorsByPage(): Pager<Int, Actor>
    fun getActorDetails(actorID: Int): Flow<NetworkResponseState<ActorDetail>>

    //Series
    fun getPopularSeries(): Flow<NetworkResponseState<List<Content>>>
    fun getAiringSeries(): Flow<NetworkResponseState<List<Content>>>
    fun getComingSeries(): Flow<NetworkResponseState<List<Content>>>
    fun getTopSeries(): Flow<NetworkResponseState<List<Content>>>
    fun getSeriesByGenre(genres: String): Flow<NetworkResponseState<List<Content>>>
    fun getSeriesDetails(seriesID: Int): Flow<NetworkResponseState<SeriesDetail>>
    fun getActorSeries(actorID: Int): Flow<NetworkResponseState<List<Content>>>

}