package com.softcross.moviedetective.data.dto.reviews

import com.google.gson.annotations.SerializedName

data class ReviewDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("author_details")
    val author_details: AuthorDto,
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val created_at: String,
)