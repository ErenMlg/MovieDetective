package com.softcross.moviedetective.data.dto.actors

import com.google.gson.annotations.SerializedName

data class ActorDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("profile_path")
    val image: String,
)