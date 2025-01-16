package com.interview.promova_app.di

import android.app.Application
import androidx.room.Room
import com.interview.promova_app.data.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java, "moviedb.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
