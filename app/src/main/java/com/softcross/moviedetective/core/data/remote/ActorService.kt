package com.softcross.moviedetective.core.data.remote

import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.core.data.dto.movies.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ActorService {

    @GET("person/{actorID}/movie_credits?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieByActor(@Path("actorID") actorID: Int) : MoviesResponse


}