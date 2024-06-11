package com.softcross.moviedetective.core.common

import com.softcross.moviedetective.core.domain.model.Genre

object GenreList {
    private var movieGenreList: List<Genre> = mutableListOf(
        Genre(genreID = 4695, genreName = "Amos"),
        Genre(genreID = 4635, genreName = "Ayala"),
        Genre(genreID = 4625, genreName = "Amos "),
        Genre(genreID = 4615, genreName = "Ayala"),
        Genre(genreID = 46151, genreName = "Ayala"),
        Genre(genreID = 46135, genreName = "Ayala"),
        Genre(genreID = 46145, genreName = "Ayala"),
        Genre(genreID = 46515, genreName = "Ayala"),
        Genre(genreID = 46165, genreName = "Ayala"),
    )
    private var seriesGenreList: List<Genre> = mutableListOf()

    fun setMovieGenreList(list: List<Genre>) {
        movieGenreList = list
    }

    fun getMovieGenreList(): List<Genre> = movieGenreList

    fun findMovieGenreWithID(genreID: Int) {
        movieGenreList.find { it.genreID == genreID }
    }

    fun setSeriesGenreList(list: List<Genre>) {
        seriesGenreList = list
    }

    fun getSeriesGenreList(): List<Genre> = seriesGenreList

    fun findSeriesGenreWithID(genreID: Int) {
        seriesGenreList.find { it.genreID == genreID }
    }
}