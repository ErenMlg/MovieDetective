package com.softcross.moviedetective.data.remote

import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.data.dto.ActorDetailDto
import com.softcross.moviedetective.data.dto.actors.ActorResponse
import com.softcross.moviedetective.data.dto.actors.images.ActorImagesResponse
import com.softcross.moviedetective.data.dto.movies.MoviesResponse
import com.softcross.moviedetective.data.dto.series.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActorService {


    @GET("person/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getPopularActors(@Query("page") page: Int = 1): ActorResponse

    @GET("person/{actorID}?api_key=${BuildConfig.API_KEY}")
    suspend fun getActorDetail(@Path("actorID") actorID: Int): ActorDetailDto

    @GET("person/{actorID}/movie_credits?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getActorMovies(@Path("actorID") actorID: Int): MoviesResponse

    @GET("person/{actorID}/tv_credits?api_key=${BuildConfig.API_KEY}")
    suspend fun getActorSeries(@Path("actorID") actorID: Int): SeriesResponse

    @GET("person/{actorID}/images?api_key=${BuildConfig.API_KEY}")
    suspend fun getActorImages(@Path("actorID") actorID: Int): ActorImagesResponse

}