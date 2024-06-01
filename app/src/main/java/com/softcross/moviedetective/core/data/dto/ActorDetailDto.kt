package com.softcross.moviedetective.core.data.dto

import com.google.gson.annotations.SerializedName

data class ActorDetailDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("profile_path")
    val image: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("biography")
    val biography: String,
    @SerializedName("also_known_as")
    val knownNames: List<String>
)