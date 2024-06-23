package com.softcross.moviedetective.di

import com.softcross.moviedetective.data.dto.actors.ActorDto
import com.softcross.moviedetective.data.dto.genre.GenreDto
import com.softcross.moviedetective.data.dto.movieDetail.MovieDetailDto
import com.softcross.moviedetective.data.dto.movieDetail.images.ImageDto
import com.softcross.moviedetective.data.dto.movieDetail.reviews.ReviewDto
import com.softcross.moviedetective.data.dto.movieDetail.video.MovieVideoDto
import com.softcross.moviedetective.data.dto.movies.MovieDto
import com.softcross.moviedetective.data.mapper.ActorResponseListMapper
import com.softcross.moviedetective.data.mapper.GenreResponseListMapper
import com.softcross.moviedetective.data.mapper.ImageResponseMapper
import com.softcross.moviedetective.data.mapper.MovieDetailResponseMapper
import com.softcross.moviedetective.data.mapper.MovieResponseListMapper
import com.softcross.moviedetective.data.mapper.ReviewResponseMapper
import com.softcross.moviedetective.data.mapper.VideoResponseMapper
import com.softcross.moviedetective.domain.mapper.MovieDetectiveBaseMapper
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.Genre
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.domain.model.Review
import com.softcross.moviedetective.domain.model.Video
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
/**
 * ViewModelComponent -> created at viewModel created, destroyed at viewModel destroyed
 * We use this because this mappers used only repository and repository is used on viewModel so when
 * viewModel created we need repository and repository need mapper.
 */
abstract class MapperModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMovieResponseMapper(movieResponseListMapper: MovieResponseListMapper): MovieDetectiveListMapper<MovieDto, Movie>


    @Binds
    @ViewModelScoped
    abstract fun bindGenreResponseMapper(genreResponseListMapper: GenreResponseListMapper): MovieDetectiveListMapper<GenreDto, Genre>

    @Binds
    @ViewModelScoped
    abstract fun bindActorResponseMapper(actorResponseListMapper: ActorResponseListMapper): MovieDetectiveListMapper<ActorDto, Actor>

    @Binds
    @ViewModelScoped
    abstract fun bindMovieDetailResponseMapper(movieDetailResponseMapper: MovieDetailResponseMapper): MovieDetectiveBaseMapper<MovieDetailDto, Movie>


    @Binds
    @ViewModelScoped
    abstract fun bindReviewResponseListMapper(reviewResponseMapper: ReviewResponseMapper): MovieDetectiveListMapper<ReviewDto, Review>

    @Binds
    @ViewModelScoped
    abstract fun bindImageResponseListMapper(imageResponseMapper: ImageResponseMapper): MovieDetectiveListMapper<ImageDto, String>

    @Binds
    @ViewModelScoped
    abstract fun bindVideoResponseListMapper(videoResponseMapper: VideoResponseMapper): MovieDetectiveListMapper<MovieVideoDto, Video>
}