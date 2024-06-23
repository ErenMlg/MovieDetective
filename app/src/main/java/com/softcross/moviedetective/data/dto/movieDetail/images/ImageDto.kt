package com.softcross.moviedetective.data.dto.movieDetail.images

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("file_path")
    val file_path: String
)