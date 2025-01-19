package com.interview.promova_app.domain.useCase

import com.interview.promova_app.data.local.FavouriteMovieEntity
import com.interview.promova_app.data.remote.ApiState
import com.interview.promova_app.domain.mapper.MovieMapper
import com.interview.promova_app.domain.mapper.map
import com.interview.promova_app.domain.repository.MovieRepository
import com.interview.promova_app.ui.main.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val mapper: MovieMapper
) {

    suspend fun getMovies(page: Int): Flow<ApiState<List<Movie>?>> {
        return movieRepository.getMovies(page).map { results ->
            results.map {
                mapper.toMovie(it)
            }
        }
    }

    suspend fun addMoviesToDd(movieList: List<Movie>) {
        val movieEntities = movieList.map {
            mapper.toMovieEntity(it)
        }
        movieRepository.addMoviesFromDd(movieEntities)
    }

    suspend fun addMovieToFavourites(movie: Movie) {
        movieRepository.addMovieToFavourites(mapper.toFavouriteMovieEntity(movie))
    }

    suspend fun deleteMovieFromFavourites(movie: Movie) {
        movieRepository.deleteMovieFromFavourites(mapper.toFavouriteMovieEntity(movie))
    }

    suspend fun getFavouriteMovies(): List<Movie> {
        return movieRepository.getFavouriteMovies().map {
            mapper.toMovie(it)
        }
    }

    suspend fun getMoviesFromDd(): List<Movie> {
        return movieRepository.getMoviesToDd().map {
            mapper.toMovie(it)
        }
    }
}