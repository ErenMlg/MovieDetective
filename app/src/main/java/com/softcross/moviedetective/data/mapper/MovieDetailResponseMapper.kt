package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.movieDetail.MovieDetailDto
import com.softcross.moviedetective.domain.mapper.MovieDetectiveBaseMapper
import com.softcross.moviedetective.domain.model.Content
import javax.inject.Inject

class MovieDetailResponseMapper @Inject constructor() :
    MovieDetectiveBaseMapper<MovieDetailDto, Content> {
    override fun map(input: MovieDetailDto): Content {
        return Content(
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