package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.video.VideoDto
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Video
import javax.inject.Inject

class VideoResponseMapper @Inject constructor() : MovieDetectiveListMapper<VideoDto, Video> {
    override fun map(input: List<VideoDto>): List<Video> {
        return input.map {
            Video(
                id = it.id,
                name = it.name,
                key = it.key,
                type = it.type
            )
        }.toSet().toList()
    }
}