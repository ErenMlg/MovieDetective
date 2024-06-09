package com.softcross.moviedetective.core.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class Actor(
    val id: Int,
    val name: String,
    val gender: Int,
    val image: String
)