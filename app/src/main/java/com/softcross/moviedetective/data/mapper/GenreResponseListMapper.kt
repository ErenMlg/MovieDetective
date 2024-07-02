package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.genre.GenreDto
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Genre
import javax.inject.Inject

class GenreResponseListMapper @Inject constructor(): MovieDetectiveListMapper<GenreDto, Genre> {
    override fun map(input: List<GenreDto>): List<Genre> {
        return input.map {
            Genre(it.id, it.name)
        }.toSet().toList()
    }
}