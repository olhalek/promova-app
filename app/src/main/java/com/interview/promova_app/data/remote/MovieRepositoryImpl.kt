package com.interview.promova_app.data.remote

import com.interview.promova_app.common.API_KEY
import com.interview.promova_app.common.SORT_BY_API_REQUEST
import com.interview.promova_app.common.VOTE_AVERAGE_API_REQUEST
import com.interview.promova_app.common.VOTE_COUNT_API_REQUEST
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private var movieApi: ApiService,
    private val movieDatabase: MovieDatabase
) : MovieRepository {

    override suspend fun getMovies(page: Int): Flow<ApiState<MoviesResponseItem>> {
        return flow {
            emit(ApiState.Loading)

            val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            val currentDate = format.format(Calendar.getInstance().time)

            val response = movieApi.getMovies(
                page = page,
                apiKey = API_KEY,
                sortBy = SORT_BY_API_REQUEST,
                voteCount = VOTE_COUNT_API_REQUEST,
                voteAverage = VOTE_AVERAGE_API_REQUEST,
                currentDate = currentDate
            )

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