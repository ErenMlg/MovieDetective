package com.softcross.moviedetective.data.source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.data.dto.MovieDetailDto
import com.softcross.moviedetective.data.dto.actors.ActorResponse
import com.softcross.moviedetective.data.dto.genre.GenreResponse
import com.softcross.moviedetective.data.dto.movies.MovieDto
import com.softcross.moviedetective.data.dto.movies.MoviesResponse
import com.softcross.moviedetective.data.remote.ActorService
import com.softcross.moviedetective.data.remote.MovieService
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
    private val actorService: ActorService
) : RemoteDataSource {

    override fun getPopularMovies(page: Int): Flow<NetworkResponseState<MoviesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = movieService.getPopularMovies(page)
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getPopularMoviesWithPaging(): RemoteMovieMediator {
        return RemoteMovieMediator(
            RequestType.POPULAR,
            movieService
        )
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