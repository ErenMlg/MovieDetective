package com.softcross.moviedetective.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class ActorDetail(
    val id: Int,
    val name: String,
    val gender: Int,
    val biography: String,
    val image: String,
    val birthday: String,
    val birthdayPlace: String,
    val actorMovies: List<Content>,
    val actorSeries: List<Content>,
    val actorImages: List<String>
)
