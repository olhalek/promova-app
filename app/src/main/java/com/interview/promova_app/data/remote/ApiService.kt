package com.interview.promova_app.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("vote_count.gte") voteCount: Double,
        @Query("vote_average.gte") voteAverage: Double,
        @Query("primary_release_date.lte") currentDate: String,
        @Query("api_key") apiKey: String
    ): Response<MoviesResponseItem>
}