package com.softcross.moviedetective.data.dto.credit

import com.google.gson.annotations.SerializedName
import com.softcross.moviedetective.data.dto.actors.ActorDto

data class CreditResponse(
    @SerializedName("cast")
    val cast: List<ActorDto>,
    @SerializedName("id")
    val id: Int
)
