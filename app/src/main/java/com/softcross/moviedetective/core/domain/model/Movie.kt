package com.softcross.moviedetective.core.domain.model

data class Movie(
    val movieID: String,
    val movieName: String,
    val description: String,
    val genres: List<Genre>,
    val imdb: Float,
    val releaseDate: String,
    val movieImage: String,
)