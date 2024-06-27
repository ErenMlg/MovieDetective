package com.softcross.moviedetective.data.source.remote

import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.data.dto.movieDetail.MovieDetailDto
import com.softcross.moviedetective.data.dto.actors.ActorResponse
import com.softcross.moviedetective.data.dto.genre.GenreResponse
import com.softcross.moviedetective.data.dto.movieDetail.credit.MovieCreditResponse
import com.softcross.moviedetective.data.dto.movieDetail.images.MovieImagesResponse
import com.softcross.moviedetective.data.dto.movieDetail.reviews.MovieReviewResponse
import com.softcross.moviedetective.data.dto.movieDetail.video.MovieVideoResponse
import com.softcross.moviedetective.data.dto.movies.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    //Movies
    //Popular Movies
    fun getPopularMovies(page: Int): Flow<NetworkResponseState<MoviesResponse>>
    fun getPopularMoviesWithPaging(): RemoteMovieMediator

    //Trend Movies
    fun getTrendMovies(): Flow<NetworkResponseState<MoviesResponse>>
    fun getTrendMoviesByPage(): RemoteMovieMediator

    //Coming Soon Movies
    fun getComingSoonMovies(): Flow<NetworkResponseState<MoviesResponse>>
    fun getComingMoviesByPage(): RemoteMovieMediator

    //Genre Movie
    fun getMovieByGenre(genres: String): Flow<NetworkResponseState<MoviesResponse>>
    fun getMoviesByGenreByPage(genreList: String): RemoteMovieMediator

    //Single Movie
    suspend fun getSingleMovie(movieID: Int): MovieDetailDto
    suspend fun getMovieVideos(movieID: Int): MovieVideoResponse
    suspend fun getMovieCredits(movieID: Int): MovieCreditResponse
    suspend fun getMovieImages(movieID: Int): MovieImagesResponse
    suspend fun getMovieReviews(movieID: Int): MovieReviewResponse
    suspend fun getMovieSimilar(movieID: Int): MoviesResponse

    //Movies Genres
    fun getMovieGenres(): Flow<NetworkResponseState<GenreResponse>>

    //Actor Movies
    fun getActorMovies(actorID: Int): Flow<NetworkResponseState<MoviesResponse>>

    //Popular Actors
    fun getPopularActors(): Flow<NetworkResponseState<ActorResponse>>
    fun getPopularActorsByPage(): RemoteActorMediator

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