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
import com.softcross.moviedetective.data.remote.ActorService
import com.softcross.moviedetective.data.remote.MovieService
import com.softcross.moviedetective.data.remote.SeriesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService,
    private val actorService: ActorService,
    private val seriesService: SeriesService
) : RemoteDataSource {

    override fun getPopularMovies(): Flow<NetworkResponseState<MoviesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = movieService.getPopularMovies()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getPopularMoviesWithPaging(): RemoteMovieMediator = RemoteMovieMediator(
        RemoteMovieMediatorRequestType.POPULAR,
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
        RemoteMovieMediatorRequestType.TREND,
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
        RemoteMovieMediatorRequestType.COMING_SOON,
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
            RemoteMovieMediatorRequestType.GENRE(genreList),
            movieService
        )

    override suspend fun getSingleMovie(movieID: Int): MovieDetailDto =
        movieService.getSingleMovie(movieID)

    override suspend fun getSingleMovieVideos(movieID: Int): VideoResponse =
        movieService.getMovieVideos(movieID)

    override suspend fun getSingleMovieCredits(movieID: Int): CreditResponse =
        movieService.getMovieCredits(movieID)

    override suspend fun getSingleMovieImages(movieID: Int): ImagesResponse =
        movieService.getMovieImages(movieID)

    override suspend fun getSingleMovieReviews(movieID: Int): ReviewResponse =
        movieService.getMovieReviews(movieID)


    override suspend fun getSingleMovieSimilar(movieID: Int): MoviesResponse =
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

    override fun getSeriesGenres(): Flow<NetworkResponseState<GenreResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = seriesService.getSeriesGenres()
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
                val response = actorService.getActorMovies(actorID)
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getActorSeries(actorID: Int): Flow<NetworkResponseState<SeriesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = actorService.getActorSeries(actorID)
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

    override suspend fun getSingleActorImages(actorID: Int): ActorImagesResponse =
        actorService.getActorImages(actorID)

    override suspend fun getSingleActor(actorID: Int): ActorDetailDto =
        actorService.getActorDetail(actorID)

    override suspend fun getSingleActorSeries(actorID: Int): SeriesResponse =
        actorService.getActorSeries(actorID)

    override suspend fun getSingleActorMovies(actorID: Int): MoviesResponse =
        actorService.getActorMovies(actorID)

    override fun getPopularSeries(): Flow<NetworkResponseState<SeriesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = seriesService.getPopularSeries()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getAiringSeries(): Flow<NetworkResponseState<SeriesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = seriesService.getAiringSeries()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getComingSeries(): Flow<NetworkResponseState<SeriesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = seriesService.getComingSeries()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getTopSeries(): Flow<NetworkResponseState<SeriesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = seriesService.getTopSeries()
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override fun getSeriesByGenre(genres: String): Flow<NetworkResponseState<SeriesResponse>> {
        return flow {
            emit(NetworkResponseState.Loading)
            try {
                val response = seriesService.getSeriesByGenre(genres)
                emit(NetworkResponseState.Success(response))
            } catch (e: Exception) {
                emit(NetworkResponseState.Error(e))
            }
        }
    }

    override suspend fun getSingleSeries(movieID: Int): SeriesDetailDto =
        seriesService.getSingleSeries(movieID)

    override suspend fun getSingleSeriesVideos(movieID: Int): VideoResponse =
        seriesService.getSeriesVideos(movieID)

    override suspend fun getSingleSeriesCredits(movieID: Int): CreditResponse =
        seriesService.getSeriesCast(movieID)

    override suspend fun getSingleSeriesImages(movieID: Int): ImagesResponse =
        seriesService.getSeriesImages(movieID)

    override suspend fun getSingleSeriesReviews(movieID: Int): ReviewResponse =
        seriesService.getSeriesReviews(movieID)

    override suspend fun getSingleSeriesSimilar(movieID: Int): SeriesResponse =
        seriesService.getSimilarSeries(movieID)
}