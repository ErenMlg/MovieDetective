package com.softcross.moviedetective.core.di

import com.softcross.moviedetective.core.data.dto.actors.ActorDto
import com.softcross.moviedetective.core.data.dto.genre.GenreDto
import com.softcross.moviedetective.core.data.dto.movies.MovieDto
import com.softcross.moviedetective.core.data.mapper.ActorResponseListMapper
import com.softcross.moviedetective.core.data.mapper.GenreResponseListMapper
import com.softcross.moviedetective.core.data.mapper.MovieResponseListMapper
import com.softcross.moviedetective.core.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.core.domain.model.Actor
import com.softcross.moviedetective.core.domain.model.Genre
import com.softcross.moviedetective.core.domain.model.Movie
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

}