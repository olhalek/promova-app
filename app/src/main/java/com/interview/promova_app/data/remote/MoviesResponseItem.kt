package com.interview.promova_app.data.remote


data class MoviesResponseItem(
    val page: Int?,
    val results: List<MovieResponse?>?,
    val total_pages: Int,
    val total_results: Int
)

data class MovieResponse(
    val id:Long?,
    val title: String?,
    val overview:String?,
    val release_date: String?,
    val poster_path:String?,
    val vote_average:Float?
)