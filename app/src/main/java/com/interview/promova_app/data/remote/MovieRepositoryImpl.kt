package com.interview.promova_app.data.remote

import com.interview.promova_app.common.API_KEY
import com.interview.promova_app.data.local.FavouriteMovieEntity
import com.interview.promova_app.data.local.MovieDatabase
import com.interview.promova_app.data.local.MovieEntity
import com.interview.promova_app.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private var movieApi: ApiService,
    private val movieDatabase: MovieDatabase
) : MovieRepository {

    override suspend fun getMovies(page: Int): Flow<ApiState<MoviesResponseItem>> {
        return flow {
            emit(ApiState.Loading)

            val response = movieApi.getMovies(page = page, apiKey = API_KEY)

            if (response.isSuccessful) {
                val data = response.body()

                if (data != null) {
                    emit(ApiState.Success(data))
                } else {
                    val error = response.errorBody()
                    if (error != null) {
                        emit(ApiState.Failure(IOException(error.toString())))
                    } else {
                        emit(ApiState.Failure(IOException("Something went wrong...")))
                    }
                }
            } else {
                emit(ApiState.Failure(Throwable(response.errorBody().toString())))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(ApiState.Failure(Exception(e)))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun addMoviesFromDd(movieEntities: List<MovieEntity>) {
        movieDatabase.movieDao.upsertMovieList(movieEntities)
    }

    override suspend fun getMoviesToDd(): List<MovieEntity> {
        return movieDatabase.movieDao.getMovies()
    }

    override suspend fun addMovieToFavourites(movieEntity: FavouriteMovieEntity) {
        movieDatabase.movieDao.insertFavourite(movieEntity)
    }

    override suspend fun deleteMovieFromFavourites(movieEntity: FavouriteMovieEntity) {
        movieDatabase.movieDao.deleteFavourite(movieEntity)
    }

    override suspend fun getFavouriteMovies(): List<FavouriteMovieEntity> {
        return movieDatabase.movieDao.getFavouriteMovies()
    }
}