package com.softcross.moviedetective.core.data.repository

import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.extensions.mapResponse
import com.softcross.moviedetective.core.data.dto.movies.MovieDto
import com.softcross.moviedetective.core.data.source.remote.RemoteDataSource
import com.softcross.moviedetective.core.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.core.domain.model.Movie
import com.softcross.moviedetective.core.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val movieOnListResponseMapper: MovieDetectiveListMapper<MovieDto, Movie>,
) : ContentRepository {
    override fun getTop20Movie(): Flow<NetworkResponseState<List<Movie>>> =
        remoteDataSource.getTop20Movie().map {
            it.mapResponse { movieOnListResponseMapper.map(data) }
        }

    override fun getTrendMovies(): Flow<NetworkResponseState<List<Movie>>> =
        remoteDataSource.getTrendMovies().map {
            it.mapResponse { movieOnListResponseMapper.map(data) }
        }

    override fun getComingSoonMovies(): Flow<NetworkResponseState<List<Movie>>> =
        remoteDataSource.getComingSoonMovies().map {
            it.mapResponse { movieOnListResponseMapper.map(data) }
        }

    override fun getMovieByGenre(genres: String): Flow<NetworkResponseState<List<Movie>>> =
        remoteDataSource.getMovieByGenre(genres).map {
            it.mapResponse { movieOnListResponseMapper.map(data) }
        }

    override fun getSingleMovie(movieID: Int): Flow<NetworkResponseState<Movie>> =
        remoteDataSource.getSingleMovie(movieID).map {
            it.mapResponse {
                Movie(
                    movieID = id,
                    movieName = title,
                    description = overview,
                    genres = genres.map { genre -> genre.id },
                    imdb = voteAverage,
                    releaseDate = releaseDate,
                    movieImage = image ?: ""
                )
            }
        }
}