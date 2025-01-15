package com.interview.promova_app.domain.repository

import com.interview.promova_app.data.remote.ApiState
import com.interview.promova_app.data.remote.MoviesResponse
import kotlinx.coroutines.flow.Flow


interface MovieRepository {

    suspend fun getMovies(): Flow<ApiState<MoviesResponse>>
}