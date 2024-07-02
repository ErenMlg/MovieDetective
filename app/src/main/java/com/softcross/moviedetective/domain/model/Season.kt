package com.softcross.moviedetective.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.softcross.moviedetective.data.dto.series.EpisodeDto

@Immutable
@Stable
data class Season(
    val id: Int,
    val title: String,
    val voteAverage: Float,
    val seasonNumber: Int,
    val image: String,
    val releaseDate: String,
    val overview: String,
    val episodeCount: Int
)