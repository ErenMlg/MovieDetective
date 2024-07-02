package com.softcross.moviedetective.di

import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.data.remote.ActorService
import com.softcross.moviedetective.data.remote.MovieService
import com.softcross.moviedetective.data.remote.SeriesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    @ViewModelScoped
    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MOVIE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideActorService(retrofit: Retrofit): ActorService {
        return retrofit.create(ActorService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideSeriesService(retrofit: Retrofit): SeriesService {
        return retrofit.create(SeriesService::class.java)
    }

}