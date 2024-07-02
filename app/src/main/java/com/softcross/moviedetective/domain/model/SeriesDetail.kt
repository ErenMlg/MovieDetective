package com.softcross.moviedetective.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class SeriesDetail(
    val series: Content,
    val seriesSeasons: List<Season>,
    val similarSeries: List<Content>,
    val reviews: List<Review>,
    val cast: List<Actor>,
    val backDropImages: List<String>,
    val posterImages: List<String>,
    val videos: List<Video>
)
