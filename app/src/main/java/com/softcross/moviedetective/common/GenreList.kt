package com.softcross.moviedetective.common

import com.softcross.moviedetective.domain.model.Genre

object GenreList {
    private var movieGenreList: List<Genre> = mutableListOf()
    private var seriesGenreList: List<Genre> = mutableListOf()

    fun setMovieGenreList(list: List<Genre>) {
        movieGenreList = list
    }

    fun getMovieGenreList(): List<Genre> {
        return movieGenreList
    }

    fun findMovieGenreWithID(genreID: Int) : Genre {
       return movieGenreList.find { it.genreID == genreID } ?: Genre(0,"")
    }

    fun setSeriesGenreList(list: List<Genre>) {
        seriesGenreList = list
    }

    fun getSeriesGenreList(): List<Genre> = seriesGenreList

    fun findSeriesGenreWithID(genreID: Int) {
        seriesGenreList.find { it.genreID == genreID }
    }
}