package com.softcross.moviedetective.data.dto.movieDetail

import com.google.gson.annotations.SerializedName
import com.softcross.moviedetective.data.dto.genre.GenreDto

data class MovieDetailDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("poster_path")
    val image: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("runtime")
    val runtime: Int?
)