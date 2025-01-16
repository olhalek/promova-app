package com.interview.promova_app.domain.mapper

import com.interview.promova_app.data.local.FavouriteMovieEntity
import com.interview.promova_app.data.local.MovieEntity
import com.interview.promova_app.data.remote.MovieResponse
import com.interview.promova_app.data.remote.MoviesResponseItem
import javax.inject.Inject


class MovieMapper @Inject constructor() {

    fun toMovieResponse(from: MoviesResponseItem?): List<MovieResponse>? {
        return from?.results?.map {
            MovieResponse(
                id = it?.id,
                title = it?.title,
                overview = it?.overview,
                poster_path = it?.poster_path,
                vote_average = it?.vote_average,
                release_date = it?.release_date
            )
        }
    }

    fun toMovieEntity(movieResponse: MovieResponse): MovieEntity {
        return MovieEntity(
            id = movieResponse.id ?: -1,
            overview = movieResponse.overview ?: "",
            poster_path = movieResponse.poster_path ?: "",
            release_date = movieResponse.release_date ?: "",
            title = movieResponse.title ?: "",
            vote_average = movieResponse.vote_average ?: 0.0,
        )
    }

    fun toFavouriteMovieEntity(movieResponse: MovieResponse) : FavouriteMovieEntity {
        return FavouriteMovieEntity(
            id = movieResponse.id ?: -1,
            overview = movieResponse.overview ?: "",
            poster_path = movieResponse.poster_path ?: "",
            release_date = movieResponse.release_date ?: "",
            title = movieResponse.title ?: "",
            vote_average = movieResponse.vote_average ?: 0.0,
        )
    }
}