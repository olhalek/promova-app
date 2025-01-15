package com.interview.promova_app.domain.mapper

import com.interview.promova_app.data.remote.MoviesResponse
import javax.inject.Inject


class MovieMapper @Inject constructor() {

    fun fromMap(from: MoviesResponse?): List<MoviesResponse.Movie>? {
        return from?.results?.map {
            MoviesResponse.Movie(
                id = it?.id,
                original_title = it?.original_title,
                overview = it?.overview,
                poster_path = it?.poster_path,
                vote_average = it?.vote_average
            )
        }
    }
}