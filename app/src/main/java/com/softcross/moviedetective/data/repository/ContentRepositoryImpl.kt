package com.softcross.moviedetective.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.extensions.mapResponse
import com.softcross.moviedetective.data.dto.actors.ActorDto
import com.softcross.moviedetective.data.dto.genre.GenreDto
import com.softcross.moviedetective.data.dto.movies.MovieDto
import com.softcross.moviedetective.data.source.remote.RemoteDataSource
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.Genre
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val movieResponseListMapper: MovieDetectiveListMapper<MovieDto, Movie>,
    private val genreResponseListMapper: MovieDetectiveListMapper<GenreDto, Genre>,
    private val actorResponseListMapper: MovieDetectiveListMapper<ActorDto, Actor>
) : ContentRepository {

    override fun getPopularMovies(page: Int): Flow<NetworkResponseState<List<Movie>>> =
        remoteDataSource.getPopularMovies(page).map {
            it.mapResponse { movieResponseListMapper.map(data) }
        }

    override fun getPopularMoviesByPage(): Pager<Int, Movie> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { remoteDataSource.getPopularMoviesWithPaging() }
    )


    override fun getTrendMovies(): Flow<NetworkResponseState<List<Movie>>> =
        remoteDataSource.getTrendMovies().map {
            it.mapResponse { movieResponseListMapper.map(data) }
        }

    override fun getTrendMoviesByPage(): Pager<Int, Movie> {
        TODO("Not yet implemented")
    }

    override fun getComingSoonMovies(): Flow<NetworkResponseState<List<Movie>>> =
        remoteDataSource.getComingSoonMovies().map {
            it.mapResponse { movieResponseListMapper.map(data) }
        }

    override fun getComingMoviesByPage(): Pager<Int, Movie> {
        TODO("Not yet implemented")
    }

    override fun getMovieByGenre(genres: String): Flow<NetworkResponseState<List<Movie>>> =
        remoteDataSource.getMovieByGenre(genres).map {
            it.mapResponse { movieResponseListMapper.map(data) }
        }

    override fun getMoviesByGenreByPage(): Pager<Int, Movie> {
        TODO("Not yet implemented")
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

    override fun getMovieGenres(): Flow<NetworkResponseState<List<Genre>>> =
        remoteDataSource.getMovieGenres().map {
            it.mapResponse { genreResponseListMapper.map(genres) }
        }

    //Actors
    override fun getPopularActors(): Flow<NetworkResponseState<List<Actor>>> =
        remoteDataSource.getPopularActors().map {
            it.mapResponse { actorResponseListMapper.map(actors) }
        }
}