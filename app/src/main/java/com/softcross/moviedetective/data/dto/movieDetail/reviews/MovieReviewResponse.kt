package com.softcross.moviedetective.data.dto.movieDetail.reviews

import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    @SerializedName("id:")
    val id: Int,
    @SerializedName("results")
    val results: List<ReviewDto>
)

