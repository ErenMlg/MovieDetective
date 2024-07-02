package com.softcross.moviedetective.data.dto.actors.images

import com.google.gson.annotations.SerializedName
import com.softcross.moviedetective.data.dto.ImageDto

data class ActorImagesResponse(
    @SerializedName("profiles")
    val profiles: List<ImageDto>
)