package com.softcross.moviedetective.data.dto.movieDetail.images

import com.google.gson.annotations.SerializedName

data class MovieImagesResponse(
    @SerializedName("backdrops")
    val backdrops: List<ImageDto>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("posters")
    val posters: List<ImageDto>
)

