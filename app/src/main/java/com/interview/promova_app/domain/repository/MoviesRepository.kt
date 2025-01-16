package com.interview.promova_app.domain.repository

import com.interview.promova_app.data.local.FavouriteMovieEntity
import com.interview.promova_app.data.local.MovieEntity
import com.interview.promova_app.data.remote.ApiState
import com.interview.promova_app.data.remote.MoviesResponseItem
import kotlinx.coroutines.flow.Flow


interface MovieRepository {

    suspend fun getMovies(): Flow<ApiState<MoviesResponseItem>>

    suspend fun addMoviesToDd(movieEntities: List<MovieEntity>)

    suspend fun addMovieToFavourites(movieEntity: FavouriteMovieEntity)

    suspend fun deleteMovieFromFavourites(movieEntity: FavouriteMovieEntity)

    suspend fun getFavouriteMovies(): List<FavouriteMovieEntity>
}