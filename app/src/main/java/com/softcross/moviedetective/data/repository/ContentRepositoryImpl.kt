package com.softcross.moviedetective.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.softcross.moviedetective.common.extensions.mapResponse
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.data.dto.ImageDto
import com.softcross.moviedetective.data.dto.actors.ActorDto
import com.softcross.moviedetective.data.dto.genre.GenreDto
import com.softcross.moviedetective.data.dto.movieDetail.MovieDetailDto
import com.softcross.moviedetective.data.dto.reviews.ReviewDto
import com.softcross.moviedetective.data.dto.video.VideoDto
import com.softcross.moviedetective.data.dto.movies.MovieDto
import com.softcross.moviedetective.data.dto.series.SeasonDto
import com.softcross.moviedetective.data.dto.series.SeriesDetailDto
import com.softcross.moviedetective.data.dto.series.SeriesDto
import com.softcross.moviedetective.data.source.remote.RemoteDataSource
import com.softcross.moviedetective.domain.mapper.MovieDetectiveBaseMapper
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.ActorDetail
import com.softcross.moviedetective.domain.model.Content
import com.softcross.moviedetective.domain.model.Genre
import com.softcross.moviedetective.domain.model.MovieDetail
import com.softcross.moviedetective.domain.model.Review
import com.softcross.moviedetective.domain.model.Season
import com.softcross.moviedetective.domain.model.SeriesDetail
import com.softcross.moviedetective.domain.model.Video
import com.softcross.moviedetective.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val movieResponseListMapper: MovieDetectiveListMapper<MovieDto, Content>,
    private val movieDetailResponseMapper: MovieDetectiveBaseMapper<MovieDetailDto, Content>,
    private val genreResponseListMapper: MovieDetectiveListMapper<GenreDto, Genre>,
    private val actorResponseListMapper: MovieDetectiveListMapper<ActorDto, Actor>,
    private val reviewResponseListMapper: MovieDetectiveListMapper<ReviewDto, Review>,
    private val imageResponseListMapper: MovieDetectiveListMapper<ImageDto, String>,
    private val videoResponseListMapper: MovieDetectiveListMapper<VideoDto, Video>,
    private val seriesResponseListMapper: MovieDetectiveListMapper<SeriesDto, Content>,
    private val seriesDetailResponseMapper: MovieDetectiveBaseMapper<SeriesDetailDto, Content>,
    private val seasonResponseListMapper: MovieDetectiveListMapper<SeasonDto, Season>
) : ContentRepository {

    override fun getPopularMovies(): Flow<NetworkResponseState<List<Content>>> =
        remoteDataSource.getPopularMovies().map {
            it.mapResponse { movieResponseListMapper.map(data) }
        }

    override fun getPopularMoviesByPage(): Pager<Int, Content> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { remoteDataSource.getPopularMoviesWithPaging() }
    )


    override fun getTrendMovies(): Flow<NetworkResponseState<List<Content>>> =
        remoteDataSource.getTrendMovies().map {
            it.mapResponse { movieResponseListMapper.map(data) }
        }

    override fun getTrendMoviesByPage(): Pager<Int, Content> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { remoteDataSource.getTrendMoviesByPage() }
    )

    override fun getComingSoonMovies(): Flow<NetworkResponseState<List<Content>>> =
        remoteDataSource.getComingSoonMovies().map {
            it.mapResponse { movieResponseListMapper.map(data) }
        }

    override fun getComingMoviesByPage(): Pager<Int, Content> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { remoteDataSource.getComingMoviesByPage() }
    )

    override fun getMovieByGenre(genres: String): Flow<NetworkResponseState<List<Content>>> =
        remoteDataSource.getMovieByGenre(genres).map {
            it.mapResponse { movieResponseListMapper.map(data) }
        }

    override fun getMoviesByGenreByPage(genres: String): Pager<Int, Content> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { remoteDataSource.getMoviesByGenreByPage(genres) }
    )

    override fun getMovieDetails(movieID: Int): Flow<NetworkResponseState<MovieDetail>> {
        return flow {
            try {
                val videosResponse = remoteDataSource.getSingleMovieVideos(movieID)
                val imagesResponse = remoteDataSource.getSingleMovieImages(movieID)
                val reviewsResponse = remoteDataSource.getSingleMovieReviews(movieID)
                val creditsResponse = remoteDataSource.getSingleMovieCredits(movieID)
                val similarResponse = remoteDataSource.getSingleMovieSimilar(movieID)
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

    override fun getActorMovies(actorID: Int): Flow<NetworkResponseState<List<Content>>> =
        remoteDataSource.getActorMovies(actorID).map {
            it.mapResponse { movieResponseListMapper.map(data) }
        }

    override fun getMovieGenres(): Flow<NetworkResponseState<List<Genre>>> =
        remoteDataSource.getMovieGenres().map {
            it.mapResponse { genreResponseListMapper.map(genres) }
        }

    override fun getSeriesGenres(): Flow<NetworkResponseState<List<Genre>>> =
        remoteDataSource.getSeriesGenres().map {
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

    override fun getActorDetails(actorID: Int): Flow<NetworkResponseState<ActorDetail>> {
        return flow {
            try {
                val imagesResponse = remoteDataSource.getSingleActorImages(actorID)
                val movieResponse =
                    remoteDataSource.getSingleActorMovies(actorID).data.sortedByDescending { it.releaseDate }
                val actorResponse = remoteDataSource.getSingleActor(actorID)
                val seriesResponse =
                    remoteDataSource.getSingleActorSeries(actorID).data.sortedByDescending { it.releaseDate }
                ActorDetail(
                    id = actorResponse.id,
                    name = actorResponse.name,
                    gender = actorResponse.gender,
                    biography = actorResponse.biography,
                    image = actorResponse.image,
                    birthday = actorResponse.birthday,
                    birthdayPlace = actorResponse.placeOfBirth,
                    actorMovies = movieResponseListMapper.map(movieResponse),
                    actorSeries = seriesResponseListMapper.map(seriesResponse),
                    actorImages = imagesResponse.profiles.map { it.file_path }
                ).let { emit(NetworkResponseState.Success(it)) }
            } catch (exception: Exception) {
                emit(NetworkResponseState.Error(exception))
            }
        }
    }

    override fun getPopularSeries(): Flow<NetworkResponseState<List<Content>>> =
        remoteDataSource.getPopularSeries().map {
            it.mapResponse { seriesResponseListMapper.map(data) }
        }

    override fun getAiringSeries(): Flow<NetworkResponseState<List<Content>>> =
        remoteDataSource.getAiringSeries().map {
            it.mapResponse { seriesResponseListMapper.map(data) }
        }

    override fun getComingSeries(): Flow<NetworkResponseState<List<Content>>> =
        remoteDataSource.getComingSeries().map {
            it.mapResponse { seriesResponseListMapper.map(data) }
        }

    override fun getTopSeries(): Flow<NetworkResponseState<List<Content>>> =
        remoteDataSource.getTopSeries().map {
            it.mapResponse { seriesResponseListMapper.map(data) }
        }

    override fun getSeriesByGenre(genres: String): Flow<NetworkResponseState<List<Content>>> =
        remoteDataSource.getSeriesByGenre(genres).map {
            it.mapResponse { seriesResponseListMapper.map(data) }
        }

    override fun getSeriesDetails(seriesID: Int): Flow<NetworkResponseState<SeriesDetail>> {
        return flow {
            try {
                val imagesResponse = remoteDataSource.getSingleSeriesImages(seriesID)
                val similarResponse = remoteDataSource.getSingleSeriesSimilar(seriesID)
                val creditResponse = remoteDataSource.getSingleSeriesCredits(seriesID)
                val videoResponse = remoteDataSource.getSingleSeriesVideos(seriesID)
                val reviewsResponse = remoteDataSource.getSingleSeriesReviews(seriesID)
                val seriesDetailResponse = remoteDataSource.getSingleSeries(seriesID)
                SeriesDetail(
                    series = seriesDetailResponseMapper.map(seriesDetailResponse),
                    seriesSeasons = seasonResponseListMapper.map(seriesDetailResponse.seasons),
                    similarSeries = seriesResponseListMapper.map(similarResponse.data),
                    reviews = reviewResponseListMapper.map(reviewsResponse.results),
                    cast = actorResponseListMapper.map(creditResponse.cast),
                    backDropImages = imageResponseListMapper.map(imagesResponse.backdrops),
                    posterImages = imageResponseListMapper.map(imagesResponse.posters),
                    videos = videoResponseListMapper.map(videoResponse.results)

                ).let { emit(NetworkResponseState.Success(it)) }
            } catch (exception: Exception) {
                emit(NetworkResponseState.Error(exception))
            }
        }
    }

    override fun getActorSeries(actorID: Int): Flow<NetworkResponseState<List<Content>>> =
        remoteDataSource.getActorSeries(actorID).map {
            it.mapResponse { seriesResponseListMapper.map(data) }
        }
}