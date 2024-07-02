package com.softcross.moviedetective.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName

@Immutable
@Stable
data class Episode(
    val id: Int,
    val name: String,
    val voteAverage: Float,
    val episodeNumber: Int,
    val image: String,
    val airDate: String,
    val overview: String
)