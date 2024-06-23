package com.softcross.moviedetective.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class Video(
    val id: String,
    val name: String,
    val key: String,
    val type: String
)