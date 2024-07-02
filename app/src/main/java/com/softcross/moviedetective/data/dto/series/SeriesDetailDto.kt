package com.softcross.moviedetective.data.dto.series

import com.google.gson.annotations.SerializedName
import com.softcross.moviedetective.data.dto.genre.GenreDto

data class SeriesDetailDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("genres")
    val genresID: List<GenreDto>,
    @SerializedName("poster_path")
    val image: String?,
    @SerializedName("first_air_date")
    val releaseDate: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("seasons")
    val seasons: List<SeasonDto>,
    @SerializedName("status")
    val status: String
)