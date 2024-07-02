package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.movies.MovieDto
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Content
import javax.inject.Inject

class MovieResponseListMapper @Inject constructor(): MovieDetectiveListMapper<MovieDto, Content> {
    override fun map(input: List<MovieDto>): List<Content> {
       return input.map { movie ->
           Content(
                id = movie.id,
                title = movie.title,
                description = movie.overview,
                genres = movie.genresID,
                imdb = movie.voteAverage,
                releaseDate = movie.releaseDate,
                image = movie.image ?: ""
            )
        }.toSet().toList()
    }
}

/*
{
        val movieList = mutableListOf<Movie>()
        input.forEach { movie ->
            val mappedMovie = Movie(
                movieID = movie.id,
                movieName = movie.title,
                description = movie.overview,
                genres = movie.genresID,
                imdb = movie.voteAverage,
                releaseDate = movie.releaseDate,
                movieImage = movie.image ?: ""
            )
            if (!movieList.contains(mappedMovie)) movieList.add(mappedMovie)
        }
        return movieList
    }
 */