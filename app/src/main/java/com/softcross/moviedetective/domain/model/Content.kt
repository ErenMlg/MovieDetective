package com.softcross.moviedetective.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class Content(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val genres: List<Int> = emptyList(),
    val imdb: Float = 0f,
    val releaseDate: String = "",
    val image: String = "",
)
