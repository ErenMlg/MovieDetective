package com.softcross.moviedetective.core.data.source.remote

interface RemoteDataSource {

    //Movies
    fun getTop20Movie()
    fun getTrendMovies()
    fun getComingSoonMovies()
    fun getMovieByGenre()
    fun getMoviesByActor()

    //Series
    fun getTop20Series()
    fun getTrendSeries()
    fun getComingSoonSeries()
    fun getSeriesByGenre()
    fun getSeriesByActor()

    //Actors
    fun getPopularActors()

}