package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.series.SeasonDto
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Season
import javax.inject.Inject

class SeasonResponseListMapper @Inject constructor() : MovieDetectiveListMapper<SeasonDto, Season> {
    override fun map(input: List<SeasonDto>): List<Season> {
        return input.map {
            Season(
                id = it.id,
                title = it.title,
                voteAverage = it.voteAverage,
                seasonNumber = it.seasonNumber,
                image = it.image ?: "",
                releaseDate = it.releaseDate,
                overview = it.overview,
                episodeCount = it.episodeCount
            )
        }
    }
}