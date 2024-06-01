package com.softcross.moviedetective.core.data.dto.genre

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<GenreDto>
)
