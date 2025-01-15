package com.interview.promova_app.data.remote


data class MoviesResponse(
    val page: Int?,
    val results: List<Movie?>?
) {
    data class Movie(
        val id:Long?,
        val original_title:String?,
        val overview:String?,
        val poster_path:String?,
        val vote_average:Float?
    )
}