package com.softcross.moviedetective.data.remote

import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.data.dto.movieDetail.MovieDetailDto
import com.softcross.moviedetective.data.dto.genre.GenreResponse
import com.softcross.moviedetective.data.dto.movieDetail.credit.MovieCreditResponse
import com.softcross.moviedetective.data.dto.movieDetail.images.MovieImagesResponse
import com.softcross.moviedetective.data.dto.movieDetail.reviews.MovieReviewResponse
import com.softcross.moviedetective.data.dto.movieDetail.video.MovieVideoResponse
import com.softcross.moviedetective.data.dto.movies.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY}")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): MoviesResponse

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getTrendMovies(@Query("page") page: Int = 1): MoviesResponse

    @GET("movie/upcoming?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getComingSoonMovies(@Query("page") page: Int = 1): MoviesResponse

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieByGenre(
        @Query("with_genres") genreID: String,
        @Query("page") page: Int = 1
    ): MoviesResponse

    @GET("movie/{movieID}?api_key=${BuildConfig.API_KEY}")
    suspend fun getSingleMovie(@Path("movieID") movieID: Int): MovieDetailDto

    @GET("genre/movie/list?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieGenres(): GenreResponse

    @GET("movie/{movieID}/videos?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieVideos(@Path("movieID") movieID: Int): MovieVideoResponse

    @GET("movie/{movieID}/credits?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieCredits(@Path("movieID") movieID: Int): MovieCreditResponse

    @GET("movie/{movieID}/images?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieImages(@Path("movieID") movieID: Int): MovieImagesResponse

    @GET("movie/{movieID}/reviews?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieReviews(@Path("movieID") movieID: Int): MovieReviewResponse

    @GET("movie/{movieID}/similar?api_key=${BuildConfig.API_KEY}")
    suspend fun getMovieSimilar(@Path("movieID") movieID: Int): MoviesResponse
}