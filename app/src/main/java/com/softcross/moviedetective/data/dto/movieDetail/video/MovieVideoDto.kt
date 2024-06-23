package com.softcross.moviedetective.data.dto.movieDetail.video

import com.google.gson.annotations.SerializedName

data class MovieVideoDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String
)