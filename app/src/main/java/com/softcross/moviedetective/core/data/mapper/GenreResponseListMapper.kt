package com.softcross.moviedetective.core.data.mapper

import com.softcross.moviedetective.core.data.dto.genre.GenreDto
import com.softcross.moviedetective.core.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.core.domain.model.Genre
import javax.inject.Inject

class GenreResponseListMapper @Inject constructor(): MovieDetectiveListMapper<GenreDto, Genre> {
    override fun map(input: List<GenreDto>): List<Genre> {
        return input.map {
            Genre(it.id, it.name)
        }
    }
}