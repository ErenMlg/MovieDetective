package com.softcross.moviedetective.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class Review(
    val author: ReviewAuthor,
    val content: String,
    val createdAt: String,
    val id: String,
)