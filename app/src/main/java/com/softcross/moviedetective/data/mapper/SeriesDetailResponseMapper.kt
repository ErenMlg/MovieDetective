package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.series.SeriesDetailDto
import com.softcross.moviedetective.domain.mapper.MovieDetectiveBaseMapper
import com.softcross.moviedetective.domain.model.Content
import javax.inject.Inject

class SeriesDetailResponseMapper @Inject constructor() :
    MovieDetectiveBaseMapper<SeriesDetailDto, Content> {
    override fun map(input: SeriesDetailDto): Content {
        return Content(
            id = input.id,
            title = input.title,
            description = input.overview,
            genres = input.genresID.map { it.id },
            imdb = input.voteAverage,
            releaseDate = input.releaseDate,
            image = input.image ?: ""

        )
    }
}