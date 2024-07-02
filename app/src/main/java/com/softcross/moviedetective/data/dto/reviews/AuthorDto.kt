package com.softcross.moviedetective.data.dto.reviews

import com.google.gson.annotations.SerializedName

data class AuthorDto(
    @SerializedName("avatar_path")
    val avatar_path: String?,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("username")
    val username: String
)