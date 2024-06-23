package com.softcross.moviedetective.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.extensions.mapResponse
import com.softcross.moviedetective.data.dto.actors.ActorDto
import com.softcross.moviedetective.data.dto.genre.GenreDto
import com.softcross.moviedetective.data.dto.movieDetail.MovieDetailDto
import com.softcross.moviedetective.data.dto.movieDetail.images.ImageDto
import com.softcross.moviedetective.data.dto.movieDetail.reviews.ReviewDto
import com.softcross.moviedetective.data.dto.movieDetail.video.MovieVideoDto
import com.softcross.moviedetective.data.dto.movies.MovieDto
import com.softcross.moviedetective.data.source.remote.RemoteDataSource
import com.softcross.moviedetective.domain.mapper.MovieDetectiveBaseMapper
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.Genre
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.domain.model.MovieDetail
import com.softcross.moviedetective.domain.model.Review
import com.softcross.moviedetective.domain.model.ReviewAuthor
import com.softcross.moviedetective.domain.model.Video
import com.softcross.moviedetective.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val movieResponseListMapper: MovieDetectiveListMapper<MovieDto, Movie>,
    private val movieDetailResponseMapper: MovieDetectiveBaseMapper<MovieDetailDto, Movie>,
    private val genreResponseListMapper: MovieDetectiveListMapper<GenreDto, Genre>,
    private val actorResponseListMapper: MovieDetectiveListMapper<ActorDto, Actor>,
    private val reviewResponseListMapper: MovieDetectiveListMapper<ReviewDto, Review>,
    private val imageResponseListMapper: MovieDetectiveListMapper<ImageDto, String>,
    private val videoResponseListMapper: MovieDetectiveListMapper<MovieVideoDto, Video>
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

    override fun getTrendMoviesByPage(): Pager<Int, Movie> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { remoteDataSource.getTrendMoviesByPage() }
    )

    override fun getComingSoonMovies(): Flow<NetworkResponseState<List<Movie>>> =
        remoteDataSource.getComingSoonMovies().map {
            it.mapResponse { movieResponseListMapper.map(data) }
        }

    override fun getComingMoviesByPage(): Pager<Int, Movie> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { remoteDataSource.getComingMoviesByPage() }
    )

    override fun getMovieByGenre(genres: String): Flow<NetworkResponseState<List<Movie>>> =
        remoteDataSource.getMovieByGenre(genres).map {
            it.mapResponse { movieResponseListMapper.map(data) }
        }

    override fun getMoviesByGenreByPage(genres: String): Pager<Int, Movie> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { remoteDataSource.getMoviesByGenreByPage(genres) }
    )

    override fun getMovieDetails(movieID: Int): Flow<NetworkResponseState<MovieDetail>> {
        return flow {
            try {
                val videosResponse = remoteDataSource.getMovieVideos(movieID)
                val imagesResponse = remoteDataSource.getMovieImages(movieID)
                val reviewsResponse = remoteDataSource.getMovieReviews(movieID)
                val creditsResponse = remoteDataSource.getMovieCredits(movieID)
                val similarResponse = remoteDataSource.getMovieSimilar(movieID)
                val movieResponse = remoteDataSource.getSingleMovie(movieID)
                MovieDetail(
                    movie = movieDetailResponseMapper.map(movieResponse),
                    runtime = movieResponse.runtime ?: 0,
                    similarMovies = movieResponseListMapper.map(similarResponse.data),
                    reviews = reviewResponseListMapper.map(reviewsResponse.results),
                    cast = actorResponseListMapper.map(creditsResponse.cast),
                    backDropImages = imageResponseListMapper.map(imagesResponse.backdrops),
                    posterImages = imageResponseListMapper.map(imagesResponse.posters),
                    videos = videoResponseListMapper.map(videosResponse.results)
                ).let { emit(NetworkResponseState.Success(it)) }
            } catch (exception: Exception) {
                emit(NetworkResponseState.Error(exception))
            }
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

    override fun getPopularActorsByPage(): Pager<Int, Actor> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { remoteDataSource.getPopularActorsByPage() })
}