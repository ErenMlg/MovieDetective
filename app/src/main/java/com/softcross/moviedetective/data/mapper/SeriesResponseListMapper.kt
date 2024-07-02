package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.series.SeriesDto
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Content
import javax.inject.Inject

class SeriesResponseListMapper @Inject constructor() : MovieDetectiveListMapper<SeriesDto, Content> {
    override fun map(input: List<SeriesDto>): List<Content> {
        return input.map {
            Content(
                id = it.id,
                title = it.title,
                description = it.overview,
                genres = it.genresID,
                imdb = it.voteAverage,
                releaseDate = it.releaseDate,
                image = it.image ?: ""
            )
        }.toSet().toList()
    }
}