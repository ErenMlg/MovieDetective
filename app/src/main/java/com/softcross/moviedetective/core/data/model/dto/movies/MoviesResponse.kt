package com.softcross.moviedetective.core.data.model.dto.movies

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movieResults: List<Movie>
)