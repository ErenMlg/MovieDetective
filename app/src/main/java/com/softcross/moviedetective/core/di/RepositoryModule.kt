package com.softcross.moviedetective.core.di

import com.softcross.moviedetective.core.data.repository.ContentRepositoryImpl
import com.softcross.moviedetective.core.data.repository.FirebaseRepositoryImpl
import com.softcross.moviedetective.core.domain.repository.ContentRepository
import com.softcross.moviedetective.core.domain.repository.FirebaseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun provideFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository

    @Binds
    @ViewModelScoped
    abstract fun provideContentRepository(contentRepositoryImpl: ContentRepositoryImpl): ContentRepository

}