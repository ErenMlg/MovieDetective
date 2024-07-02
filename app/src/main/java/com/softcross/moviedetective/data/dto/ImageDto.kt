package com.softcross.moviedetective.data.dto

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("file_path")
    val file_path: String
)