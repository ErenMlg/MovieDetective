package com.softcross.moviedetective.core.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class User(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val fullName: String = ""
)
