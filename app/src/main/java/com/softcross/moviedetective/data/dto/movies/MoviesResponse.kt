package com.softcross.moviedetective.data.dto.movies

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val data: List<MovieDto>
)