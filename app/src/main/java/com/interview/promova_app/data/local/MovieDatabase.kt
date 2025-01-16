package com.interview.promova_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class, FavouriteMovieEntity::class],
    version = 2
)
abstract class MovieDatabase: RoomDatabase() {
    abstract val movieDao: MovieDao
}