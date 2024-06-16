package com.softcross.moviedetective.remote

import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.data.dto.MovieDetailDto
import com.softcross.moviedetective.data.dto.actors.ActorResponse
import com.softcross.moviedetective.data.dto.genre.GenreResponse
import com.softcross.moviedetective.data.dto.movies.MoviesResponse
import com.softcross.moviedetective.data.source.remote.RemoteDataSource
import com.softcross.moviedetective.moviesResult
import com.softcross.moviedetective.singleMovieResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class FakeRemoteDataSource : RemoteDataSource {

    private var showErrorForTopMovies = false
    private var showErrorForTrendMovies = false
    private var showErrorForComingMovies = false
    private var showErrorForSingleMovie = false

    fun updateShowErrorForTopMovies(showError: Boolean) {
        this.showErrorForTopMovies = showError
    }

    fun updateShowErrorForTrendMovies(showError: Boolean) {
        this.showErrorForTrendMovies = showError
    }

    fun updateShowErrorForComingMovies(showError: Boolean) {
        this.showErrorForComingMovies = showError
    }

    fun updateShowErrorForSingleMovie(showError: Boolean) {
        this.showErrorForSingleMovie = showError
    }

    override fun getTop20Movie(): Flow<NetworkResponseState<MoviesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            if (showErrorForTopMovies) {
                emit(NetworkResponseState.Error(IOException()))
            } else {
                emit(
                    NetworkResponseState.Success(moviesResult)
                )
            }
        }
    }

    override fun getTrendMovies(): Flow<NetworkResponseState<MoviesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            if (showErrorForTrendMovies) {
                emit(NetworkResponseState.Error(IOException()))
            } else {
                emit(
                    NetworkResponseState.Success(moviesResult)
                )
            }
        }
    }

    override fun getComingSoonMovies(): Flow<NetworkResponseState<MoviesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            if (showErrorForComingMovies) {
                emit(NetworkResponseState.Error(IOException()))
            } else {
                emit(
                    NetworkResponseState.Success(moviesResult)
                )
            }
        }
    }

    override fun getMovieByGenre(genres: String): Flow<NetworkResponseState<MoviesResponse>> {
        TODO("Not yet implemented")
    }

    override fun getSingleMovie(movieID: Int): Flow<NetworkResponseState<MovieDetailDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            if (showErrorForSingleMovie) {
                emit(NetworkResponseState.Error(IOException()))
            } else {
                emit(
                    NetworkResponseState.Success(singleMovieResult)
                )
            }
        }
    }

    override fun getMovieGenres(): Flow<NetworkResponseState<GenreResponse>> {
        TODO("Not yet implemented")
    }

    override fun getPopularActors(): Flow<NetworkResponseState<ActorResponse>> {
        TODO("Not yet implemented")
    }
}