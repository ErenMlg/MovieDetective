package com.softcross.moviedetective.core.data.dto.actors

import com.google.gson.annotations.SerializedName

data class Actor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("profile_path")
    val image: String,
)