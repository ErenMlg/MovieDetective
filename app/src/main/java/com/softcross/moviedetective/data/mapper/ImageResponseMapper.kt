package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.movieDetail.images.ImageDto
import com.softcross.moviedetective.data.dto.movieDetail.images.MovieImagesResponse
import com.softcross.moviedetective.domain.mapper.MovieDetectiveBaseMapper
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import javax.inject.Inject

class ImageResponseMapper @Inject constructor() : MovieDetectiveListMapper<ImageDto, String> {
    override fun map(input: List<ImageDto>): List<String> {
        return input.map {
            it.file_path
        }
    }
}