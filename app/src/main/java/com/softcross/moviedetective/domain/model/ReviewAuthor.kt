package com.softcross.moviedetective.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class ReviewAuthor(
    val username: String,
    val avatar: String,
    val rating: Int
)