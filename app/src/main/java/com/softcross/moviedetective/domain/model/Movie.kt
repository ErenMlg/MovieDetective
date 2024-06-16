package com.softcross.moviedetective.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class Movie(
    val movieID: Int,
    val movieName: String,
    val description: String,
    val genres: List<Int>,
    val imdb: Float,
    val releaseDate: String,
    val movieImage: String,
)