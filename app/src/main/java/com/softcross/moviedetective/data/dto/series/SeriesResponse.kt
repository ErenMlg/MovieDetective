package com.softcross.moviedetective.data.dto.series

import com.google.gson.annotations.SerializedName
import com.softcross.moviedetective.data.dto.movies.MovieDto

data class SeriesResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results", alternate = ["cast"])
    val data: List<SeriesDto>
)