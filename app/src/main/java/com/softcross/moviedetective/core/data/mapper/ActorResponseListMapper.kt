package com.softcross.moviedetective.core.data.mapper

import com.softcross.moviedetective.core.data.dto.actors.ActorDto
import com.softcross.moviedetective.core.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.core.domain.model.Actor
import javax.inject.Inject

class ActorResponseListMapper @Inject constructor() : MovieDetectiveListMapper<ActorDto, Actor> {
    override fun map(input: List<ActorDto>): List<Actor> {
        return input.map {
            Actor(
                it.id,
                it.name,
                it.gender,
                it.image
            )
        }
    }
}