package com.softcross.moviedetective.data.dto.video

import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)