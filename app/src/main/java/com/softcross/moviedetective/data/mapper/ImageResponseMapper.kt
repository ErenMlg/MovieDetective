package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.ImageDto
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import javax.inject.Inject

class ImageResponseMapper @Inject constructor() : MovieDetectiveListMapper<ImageDto, String> {
    override fun map(input: List<ImageDto>): List<String> {
        return input.map {
            it.file_path
        }.toSet().toList()
    }
}