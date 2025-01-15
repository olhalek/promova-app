package com.interview.promova_app.di

import com.interview.promova_app.data.remote.MovieRepositoryImpl
import com.interview.promova_app.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(
        repo: MovieRepositoryImpl
    ): MovieRepository
}