package com.softcross.moviedetective.data.remote

import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.data.dto.actors.ActorResponse
import com.softcross.moviedetective.data.dto.movies.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActorService {

    @GET("person/{actorID}/movie_credits?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieByActor(@Path("actorID") actorID: Int): ActorResponse

    @GET("person/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getPopularActors(@Query("page") page: Int = 1): ActorResponse


}