package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.movieDetail.reviews.MovieReviewResponse
import com.softcross.moviedetective.data.dto.movieDetail.reviews.ReviewDto
import com.softcross.moviedetective.domain.mapper.MovieDetectiveBaseMapper
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Review
import com.softcross.moviedetective.domain.model.ReviewAuthor
import javax.inject.Inject

class ReviewResponseMapper @Inject constructor() :
    MovieDetectiveListMapper<ReviewDto, Review> {
    override fun map(input: List<ReviewDto>): List<Review> {
        return input.map {
            Review(
                author = ReviewAuthor(
                    username = it.author_details.username,
                    avatar = it.author_details.avatar_path ?: "",
                    rating = it.author_details.rating
                ),
                content = it.content,
                createdAt = it.created_at,
                id = it.id
            )
        }
    }
}