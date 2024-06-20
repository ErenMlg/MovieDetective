package com.softcross.moviedetective.repository

import androidx.paging.Pager
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.extensions.mapResponse
import com.softcross.moviedetective.data.dto.MovieDetailDto
import com.softcross.moviedetective.data.dto.movies.MovieDto
import com.softcross.moviedetective.data.mapper.MovieResponseListMapper
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.Genre
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.domain.repository.ContentRepository
import com.softcross.moviedetective.singleMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class FakeContentRepository : ContentRepository {

    private val mapper = MovieResponseListMapper()

    private var moviesResult = MutableStateFlow<NetworkResponseState<List<MovieDto>>>(
        NetworkResponseState.Loading
    )

    private var singleMovieResult = MutableStateFlow<NetworkResponseState<MovieDetailDto>>(
        NetworkResponseState.Loading
    )

    suspend fun updateMoviesResultState(isError: Boolean = false, data: List<MovieDto> = emptyList<MovieDto>()) {
        if (isError) {
            moviesResult.emit(NetworkResponseState.Error(Exception()))
        } else {
            moviesResult.emit(
                NetworkResponseState.Success(
                    data
                )
            )
        }
    }

    suspend fun updateSingleMovieResultState(isError: Boolean = false, data: MovieDetailDto = singleMovie) {
        if (isError) {
            singleMovieResult.emit(NetworkResponseState.Error(Exception("Error")))
        } else {
            singleMovieResult.emit(
                NetworkResponseState.Success(data)
            )
        }
    }

    override fun getPopularMovies(page: Int): Flow<NetworkResponseState<List<Movie>>> =
        moviesResult.map { it.mapResponse { mapper.map(this) } }

    override fun getPopularMoviesByPage(): Pager<Int, Movie> {
        TODO("Not yet implemented")
    }


    override fun getTrendMovies(): Flow<NetworkResponseState<List<Movie>>> =
        moviesResult.map { it.mapResponse { mapper.map(this) } }

    override fun getTrendMoviesByPage(): Pager<Int, Movie> {
        TODO("Not yet implemented")
    }

    override fun getComingSoonMovies(): Flow<NetworkResponseState<List<Movie>>> =
        moviesResult.map { it.mapResponse { mapper.map(this) } }

    override fun getComingMoviesByPage(): Pager<Int, Movie> {
        TODO("Not yet implemented")
    }

    override fun getMovieByGenre(genres: String): Flow<NetworkResponseState<List<Movie>>> =
        moviesResult.map { it.mapResponse { mapper.map(this) } }

    override fun getMoviesByGenreByPage(): Pager<Int, Movie> {
        TODO("Not yet implemented")
    }

    override fun getSingleMovie(movieID: Int): Flow<NetworkResponseState<Movie>> =
        singleMovieResult.map {
            it.mapResponse {
                Movie(
                    movieID = id,
                    movieName = title,
                    description = overview,
                    genres = genres.map { it.id },
                    imdb = voteAverage,
                    releaseDate = releaseDate,
                    movieImage = image ?: ""
                )
            }
        }

    override fun getMovieGenres(): Flow<NetworkResponseState<List<Genre>>> {
        TODO("Not yet implemented")
    }

    override fun getPopularActors(): Flow<NetworkResponseState<List<Actor>>> {
        TODO("Not yet implemented")
    }
}
