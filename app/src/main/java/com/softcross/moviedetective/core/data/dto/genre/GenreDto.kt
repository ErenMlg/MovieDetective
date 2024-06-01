package com.softcross.moviedetective.core.data.dto.genre

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String
)