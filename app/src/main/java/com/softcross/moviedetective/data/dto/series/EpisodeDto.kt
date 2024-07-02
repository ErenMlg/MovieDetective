package com.softcross.moviedetective.data.dto.series

import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("episode_number")
    val episodeNumber: Int,
    @SerializedName("still_path")
    val image: String?,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("overview")
    val overview: String
)