package com.softcross.moviedetective.data.dto.series

import com.google.gson.annotations.SerializedName

data class SeriesDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("genre_ids")
    val genresID: List<Int>,
    @SerializedName("poster_path")
    val image: String?,
    @SerializedName("first_air_date")
    val releaseDate: String,
    @SerializedName("overview")
    val overview: String
)