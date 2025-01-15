package com.interview.promova_app.domain.useCase

import com.interview.promova_app.data.remote.ApiState
import com.interview.promova_app.data.remote.MoviesResponse
import com.interview.promova_app.domain.mapper.MovieMapper
import com.interview.promova_app.domain.mapper.map
import com.interview.promova_app.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val repo: MovieRepository,
    private val mapper: MovieMapper
) {

    suspend fun getMovies(): Flow<ApiState<List<MoviesResponse.Movie>?>> {
        return repo.getMovies().map { results ->
            results.map {
                mapper.fromMap(it)
            }
        }
    }
}