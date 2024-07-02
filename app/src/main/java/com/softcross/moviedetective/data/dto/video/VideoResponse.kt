package com.softcross.moviedetective.data.dto.video

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<VideoDto>
)

