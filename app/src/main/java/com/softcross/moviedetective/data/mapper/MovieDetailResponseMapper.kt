package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.movieDetail.MovieDetailDto
import com.softcross.moviedetective.domain.mapper.MovieDetectiveBaseMapper
import com.softcross.moviedetective.domain.model.Movie
import javax.inject.Inject

class MovieDetailResponseMapper @Inject constructor() :
    MovieDetectiveBaseMapper<MovieDetailDto, Movie> {
    override fun map(input: MovieDetailDto): Movie {
        return Movie(
            input.id,
            input.title,
            input.overview,
            input.genres.map { it.id },
            input.voteAverage,
            input.releaseDate,
            input.image ?: ""
        )
    }
}