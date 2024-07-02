package com.softcross.moviedetective.data.source.remote

import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.data.dto.ActorDetailDto
import com.softcross.moviedetective.data.dto.movieDetail.MovieDetailDto
import com.softcross.moviedetective.data.dto.actors.ActorResponse
import com.softcross.moviedetective.data.dto.actors.images.ActorImagesResponse
import com.softcross.moviedetective.data.dto.genre.GenreResponse
import com.softcross.moviedetective.data.dto.credit.CreditResponse
import com.softcross.moviedetective.data.dto.images.ImagesResponse
import com.softcross.moviedetective.data.dto.reviews.ReviewResponse
import com.softcross.moviedetective.data.dto.video.VideoResponse
import com.softcross.moviedetective.data.dto.movies.MoviesResponse
import com.softcross.moviedetective.data.dto.series.SeriesDetailDto
import com.softcross.moviedetective.data.dto.series.SeriesResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    //Movies
    //Popular Movies
    fun getPopularMovies(): Flow<NetworkResponseState<MoviesResponse>>
    fun getPopularMoviesWithPaging(): RemoteMovieMediator

    //Trend Movies
    fun getTrendMovies(): Flow<NetworkResponseState<MoviesResponse>>
    fun getTrendMoviesByPage(): RemoteMovieMediator

    //Coming Soon Movies
    fun getComingSoonMovies(): Flow<NetworkResponseState<MoviesResponse>>
    fun getComingMoviesByPage(): RemoteMovieMediator

    //Movie Genre
    fun getMovieByGenre(genres: String): Flow<NetworkResponseState<MoviesResponse>>
    fun getMoviesByGenreByPage(genreList: String): RemoteMovieMediator

    //Single Movie
    suspend fun getSingleMovie(movieID: Int): MovieDetailDto
    suspend fun getSingleMovieVideos(movieID: Int): VideoResponse
    suspend fun getSingleMovieCredits(movieID: Int): CreditResponse
    suspend fun getSingleMovieImages(movieID: Int): ImagesResponse
    suspend fun getSingleMovieReviews(movieID: Int): ReviewResponse
    suspend fun getSingleMovieSimilar(movieID: Int): MoviesResponse

    //Genres
    fun getMovieGenres(): Flow<NetworkResponseState<GenreResponse>>
    fun getSeriesGenres(): Flow<NetworkResponseState<GenreResponse>>

    //Actors
    //Actor Movies
    fun getActorMovies(actorID: Int): Flow<NetworkResponseState<MoviesResponse>>
    fun getActorSeries(actorID: Int): Flow<NetworkResponseState<SeriesResponse>>

    //Popular Actors
    fun getPopularActors(): Flow<NetworkResponseState<ActorResponse>>
    fun getPopularActorsByPage(): RemoteActorMediator

    //Single Actor
    suspend fun getSingleActorImages(actorID: Int): ActorImagesResponse
    suspend fun getSingleActor(actorID: Int): ActorDetailDto
    suspend fun getSingleActorSeries(actorID: Int): SeriesResponse
    suspend fun getSingleActorMovies(actorID: Int): MoviesResponse

    //Series
    //Popular Series
    fun getPopularSeries(): Flow<NetworkResponseState<SeriesResponse>>

    //Airing Series
    fun getAiringSeries(): Flow<NetworkResponseState<SeriesResponse>>

    //Coming Series
    fun getComingSeries(): Flow<NetworkResponseState<SeriesResponse>>

    //Top Series
    fun getTopSeries(): Flow<NetworkResponseState<SeriesResponse>>

    //Series Genre
    fun getSeriesByGenre(genres: String): Flow<NetworkResponseState<SeriesResponse>>

    //Single Series
    suspend fun getSingleSeries(movieID: Int): SeriesDetailDto
    suspend fun getSingleSeriesVideos(movieID: Int): VideoResponse
    suspend fun getSingleSeriesCredits(movieID: Int): CreditResponse
    suspend fun getSingleSeriesImages(movieID: Int): ImagesResponse
    suspend fun getSingleSeriesReviews(movieID: Int): ReviewResponse
    suspend fun getSingleSeriesSimilar(movieID: Int): SeriesResponse
}