package com.softcross.moviedetective.data.dto.series

import com.google.gson.annotations.SerializedName

data class SeasonDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("season_number")
    val seasonNumber: Int,
    @SerializedName("poster_path")
    val image: String?,
    @SerializedName("air_date")
    val releaseDate: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("episode_count")
    val episodeCount: Int,
    @SerializedName("episodes")
    val episodes: List<EpisodeDto>
)