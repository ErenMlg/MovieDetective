package com.softcross.moviedetective.core.data.source.remote

import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.data.dto.MovieDetailDto
import com.softcross.moviedetective.core.data.dto.actors.ActorResponse
import com.softcross.moviedetective.core.data.dto.genre.GenreDto
import com.softcross.moviedetective.core.data.dto.genre.GenreResponse
import com.softcross.moviedetective.core.data.dto.movies.MoviesResponse
import com.softcross.moviedetective.core.data.remote.ActorService
import com.softcross.moviedetective.core.data.remote.MovieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
    private val actorService: ActorService
) : RemoteDataSource {
    override fun getTop20Movie(): Flow<NetworkResponseState<MoviesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = movieService.getTop20Movie()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getTrendMovies(): Flow<NetworkResponseState<MoviesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = movieService.getTrendMovies()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getComingSoonMovies(): Flow<NetworkResponseState<MoviesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = movieService.getComingSoonMovies()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getMovieByGenre(genres: String): Flow<NetworkResponseState<MoviesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = movieService.getMovieByGenre(genres)
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getSingleMovie(movieID: Int): Flow<NetworkResponseState<MovieDetailDto>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = movieService.getSingleMovie(movieID)
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getMovieGenres(): Flow<NetworkResponseState<GenreResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = movieService.getMovieGenres()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    //Actors
    override fun getPopularActors(): Flow<NetworkResponseState<ActorResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = actorService.getPopularActors()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

}