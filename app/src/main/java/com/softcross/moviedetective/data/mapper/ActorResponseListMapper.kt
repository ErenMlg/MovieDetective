package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.actors.ActorDto
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Actor
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