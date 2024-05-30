package com.softcross.moviedetective.core.data.model.dto.actors

import com.google.gson.annotations.SerializedName

data class ActorResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Actor>
)