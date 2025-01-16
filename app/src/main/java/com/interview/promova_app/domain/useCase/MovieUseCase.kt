package com.interview.promova_app.domain.useCase

import com.interview.promova_app.data.local.FavouriteMovieEntity
import com.interview.promova_app.data.remote.ApiState
import com.interview.promova_app.data.remote.MovieResponse
import com.interview.promova_app.domain.mapper.MovieMapper
import com.interview.promova_app.domain.mapper.map
import com.interview.promova_app.domain.repository.MovieRepository
import com.interview.promova_app.ui.main.model.MovieState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val mapper: MovieMapper
) {

    suspend fun getMovies(): Flow<ApiState<List<MovieResponse>?>> {
        return movieRepository.getMovies().map { results ->
            results.map {
                mapper.toMovieResponse(it)
            }
        }
    }

    suspend fun addMoviesToDd(movieState: MovieState) {
       val movieEntities = movieState.list.map {
           mapper.toMovieEntity(it)
       }
        movieRepository.addMoviesToDd(movieEntities)
    }

    suspend fun addMovieToFavourites(movieEntity: FavouriteMovieEntity) {
        movieRepository.addMovieToFavourites(movieEntity)
    }

    suspend fun deleteMovieFromFavourites(movieEntity: FavouriteMovieEntity) {
        movieRepository.deleteMovieFromFavourites(movieEntity)
    }

    suspend fun getFavouriteMovies(): List<FavouriteMovieEntity> {
        return movieRepository.getFavouriteMovies()
    }
}