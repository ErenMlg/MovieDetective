package com.softcross.moviedetective.data.dto.reviews

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("id:")
    val id: Int,
    @SerializedName("results")
    val results: List<ReviewDto>
)

