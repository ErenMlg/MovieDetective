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
import com.softcross.moviedetective.data.remote.ActorService
import com.softcross.moviedetective.data.remote.MovieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override fun getPopularMoviesWithPaging(): RemoteMovieMediator = RemoteMovieMediator(
        RequestType.POPULAR,
        movieService
    )


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

    override fun getTrendMoviesByPage(): RemoteMovieMediator = RemoteMovieMediator(
        RequestType.TREND,
        movieService
    )

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

    override fun getComingMoviesByPage(): RemoteMovieMediator = RemoteMovieMediator(
        RequestType.COMING_SOON,
        movieService
    )

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

    override fun getMoviesByGenreByPage(genreList: String): RemoteMovieMediator =
        RemoteMovieMediator(
            RequestType.GENRE(genreList),
            movieService
        )

    override suspend fun getSingleMovie(movieID: Int): MovieDetailDto =
        movieService.getSingleMovie(movieID)

    override suspend fun getMovieVideos(movieID: Int): MovieVideoResponse =
        movieService.getMovieVideos(movieID)

    override suspend fun getMovieCredits(movieID: Int): MovieCreditResponse =
        movieService.getMovieCredits(movieID)

    override suspend fun getMovieImages(movieID: Int): MovieImagesResponse =
        movieService.getMovieImages(movieID)

    override suspend fun getMovieReviews(movieID: Int): MovieReviewResponse =
        movieService.getMovieReviews(movieID)


    override suspend fun getMovieSimilar(movieID: Int): MoviesResponse =
        movieService.getMovieSimilar(movieID)


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

    override fun getActorMovies(actorID: Int): Flow<NetworkResponseState<MoviesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                println(actorID)
                val response = movieService.getActorMovies(actorID)
                println(response)
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

    override fun getPopularActorsByPage(): RemoteActorMediator = RemoteActorMediator(
        actorService
    )

}