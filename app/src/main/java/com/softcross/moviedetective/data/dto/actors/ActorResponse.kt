package com.softcross.moviedetective.data.dto.actors

import com.google.gson.annotations.SerializedName

data class ActorResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val actors: List<ActorDto>
)