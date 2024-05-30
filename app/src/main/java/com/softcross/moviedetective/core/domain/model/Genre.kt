package com.softcross.moviedetective.core.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class Genre(
    val genreID:String,
    val genreName:String
)
