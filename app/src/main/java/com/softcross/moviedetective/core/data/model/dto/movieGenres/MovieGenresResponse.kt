package com.softcross.moviedetective.core.data.model.dto.movieGenres

import com.google.gson.annotations.SerializedName

data class MovieGenresResponse(
    @SerializedName("genres")
    val genres: List<MovieGenres>
)
