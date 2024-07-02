package com.softcross.moviedetective.data.remote

import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.data.dto.credit.CreditResponse
import com.softcross.moviedetective.data.dto.genre.GenreResponse
import com.softcross.moviedetective.data.dto.images.ImagesResponse
import com.softcross.moviedetective.data.dto.reviews.ReviewResponse
import com.softcross.moviedetective.data.dto.series.SeriesDetailDto
import com.softcross.moviedetective.data.dto.series.SeriesResponse
import com.softcross.moviedetective.data.dto.video.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesService {

    @GET("tv/popular?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getPopularSeries(@Query("page") page: Int = 1): SeriesResponse

    @GET("tv/airing_today?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getAiringSeries(@Query("page") page: Int = 1): SeriesResponse

    @GET("tv/on_the_air?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getComingSeries(@Query("page") page: Int = 1): SeriesResponse

    @GET("tv/top_rated?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getTopSeries(@Query("page") page: Int = 1): SeriesResponse

    @GET("discover/tv?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getSeriesByGenre(
        @Query("with_genres") genreID: String,
        @Query("page") page: Int = 1
    ): SeriesResponse

    @GET("genre/tv/list?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getSeriesGenres(): GenreResponse

    @GET("tv/{series_id}?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getSingleSeries(
        @Path("series_id") seriesID: Int
    ): SeriesDetailDto

    @GET("tv/{series_id}/similar?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getSimilarSeries(
        @Path("series_id") seriesID: Int
    ): SeriesResponse

    @GET("tv/{series_id}/reviews?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getSeriesReviews(
        @Path("series_id") seriesID: Int
    ): ReviewResponse

    @GET("tv/{series_id}/credits?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getSeriesCast(
        @Path("series_id") seriesID: Int
    ): CreditResponse

    @GET("tv/{series_id}/images?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getSeriesImages(
        @Path("series_id") seriesID: Int
    ): ImagesResponse

    @GET("tv/{series_id}/videos?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getSeriesVideos(
        @Path("series_id") seriesID: Int
    ): VideoResponse
}