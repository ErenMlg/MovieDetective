package com.softcross.moviedetective.core.data.mapper

import com.softcross.moviedetective.core.data.dto.movies.MovieDto
import com.softcross.moviedetective.core.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.core.domain.model.Movie
import javax.inject.Inject

class MovieResponseListMapper @Inject constructor(): MovieDetectiveListMapper<MovieDto, Movie> {
    override fun map(input: List<MovieDto>): List<Movie> {
        return input.map {
            Movie(
                movieID = it.id,
                movieName = it.title,
                description = it.overview,
                genres = it.genresID,
                imdb = it.voteAverage,
                releaseDate = it.releaseDate,
                movieImage = it.image ?: ""
            )
        }
    }
}