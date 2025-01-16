package com.interview.promova_app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM FavouriteMovieEntity")
    suspend fun getFavouriteMovies(): List<FavouriteMovieEntity>

    @Insert
    suspend fun insertFavourite(movie: FavouriteMovieEntity)

    @Delete
    suspend fun deleteFavourite(movie: FavouriteMovieEntity)

}