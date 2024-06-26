package com.softcross.moviedetective.data.dto.genre

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String
)