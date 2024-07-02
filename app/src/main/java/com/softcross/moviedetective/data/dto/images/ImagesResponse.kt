package com.softcross.moviedetective.data.dto.images

import com.google.gson.annotations.SerializedName
import com.softcross.moviedetective.data.dto.ImageDto

data class ImagesResponse(
    @SerializedName("backdrops")
    val backdrops: List<ImageDto>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("posters")
    val posters: List<ImageDto>
)

