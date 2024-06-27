package com.softcross.moviedetective.data.mapper

import com.softcross.moviedetective.data.dto.movies.MovieDto
import com.softcross.moviedetective.domain.mapper.MovieDetectiveListMapper
import com.softcross.moviedetective.domain.model.Movie
import javax.inject.Inject

class MovieResponseListMapper @Inject constructor(): MovieDetectiveListMapper<MovieDto, Movie> {
    override fun map(input: List<MovieDto>): List<Movie> {
       return input.map { movie ->
            Movie(
                movieID = movie.id,
                movieName = movie.title,
                description = movie.overview,
                genres = movie.genresID,
                imdb = movie.voteAverage,
                releaseDate = movie.releaseDate,
                movieImage = movie.image ?: ""
            )
        }
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