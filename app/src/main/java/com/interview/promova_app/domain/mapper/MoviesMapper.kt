package com.interview.promova_app.domain.mapper

import com.interview.promova_app.data.local.FavouriteMovieEntity
import com.interview.promova_app.data.local.MovieEntity
import com.interview.promova_app.data.remote.MoviesResponseItem
import com.interview.promova_app.ui.main.model.Movie
import javax.inject.Inject


class MovieMapper @Inject constructor() {

    fun toMovieEntity(movie: Movie): MovieEntity {
        return MovieEntity(
            id = movie.id,
            overview = movie.overview,
            poster_path = movie.posterPath,
            release_date = movie.releaseDate,
            title = movie.title,
            vote_average = movie.rate,
        )
    }

    fun toFavouriteMovieEntity(movie: Movie): FavouriteMovieEntity {
        return FavouriteMovieEntity(
            id = movie.id,
            overview = movie.overview,
            poster_path = movie.posterPath,
            release_date = movie.releaseDate,
            title = movie.title,
            vote_average = movie.rate,
        )
    }

    fun toMovie(from: MoviesResponseItem?): List<Movie>? {
        return from?.results?.map { movieResponse ->
            Movie(
                id = movieResponse?.id ?: 0,
                title = movieResponse?.title ?: "",
                overview = movieResponse?.overview ?: "",
                posterPath = movieResponse?.poster_path ?: "",
                releaseDate = movieResponse?.release_date ?: "",
                rate = movieResponse?.vote_average ?: 0.0,
                isFavourite = false
            )
        }
    }

    fun toMovie(from: FavouriteMovieEntity): Movie {
        return Movie(
            id = from.id,
            title = from.title,
            overview = from.overview,
            posterPath = from.poster_path,
            releaseDate = from.release_date,
            rate = from.vote_average,
            isFavourite = true
        )
    }

    fun toMovie(from: MovieEntity): Movie {
        return Movie(
            id = from.id,
            title = from.title,
            overview = from.overview,
            posterPath = from.poster_path,
            releaseDate = from.release_date,
            rate = from.vote_average,
            isFavourite = false
        )
    }
}