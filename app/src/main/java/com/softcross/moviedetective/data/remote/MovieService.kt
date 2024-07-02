package com.softcross.moviedetective.data.remote

import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.data.dto.movieDetail.MovieDetailDto
import com.softcross.moviedetective.data.dto.genre.GenreResponse
import com.softcross.moviedetective.data.dto.credit.CreditResponse
import com.softcross.moviedetective.data.dto.images.ImagesResponse
import com.softcross.moviedetective.data.dto.reviews.ReviewResponse
import com.softcross.moviedetective.data.dto.video.VideoResponse
import com.softcross.moviedetective.data.dto.movies.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getPopularMovies(@Query("page") page: Int = 1): MoviesResponse

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getTrendMovies(@Query("page") page: Int = 1): MoviesResponse

    @GET("movie/upcoming?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getComingSoonMovies(@Query("page") page: Int = 1): MoviesResponse

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getMovieByGenre(
        @Query("with_genres") genreID: String,
        @Query("page") page: Int = 1
    ): MoviesResponse

    @GET("movie/{movieID}?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getSingleMovie(@Path("movieID") movieID: Int): MovieDetailDto

    @GET("genre/movie/list?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getMovieGenres(): GenreResponse

    @GET("movie/{movieID}/videos?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getMovieVideos(@Path("movieID") movieID: Int): VideoResponse

    @GET("movie/{movieID}/credits?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getMovieCredits(@Path("movieID") movieID: Int): CreditResponse

    @GET("movie/{movieID}/images?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getMovieImages(@Path("movieID") movieID: Int): ImagesResponse

    @GET("movie/{movieID}/reviews?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getMovieReviews(@Path("movieID") movieID: Int): ReviewResponse

    @GET("movie/{movieID}/similar?api_key=${BuildConfig.API_KEY}&region=US")
    suspend fun getMovieSimilar(@Path("movieID") movieID: Int): MoviesResponse

}