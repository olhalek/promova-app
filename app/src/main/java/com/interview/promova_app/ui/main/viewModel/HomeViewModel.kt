package com.interview.promova_app.ui.main.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.promova_app.data.network.NetworkStatus
import com.interview.promova_app.data.remote.ApiState
import com.interview.promova_app.data.remote.MovieResponse
import com.interview.promova_app.domain.mapper.MovieMapper
import com.interview.promova_app.domain.useCase.MovieUseCase
import com.interview.promova_app.domain.useCase.NetworkUseCase
import com.interview.promova_app.ui.main.model.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val networkUseCase: NetworkUseCase,
    private val mapper: MovieMapper
) : ViewModel() {

    private val _movieState: MutableState<MovieState> = mutableStateOf(MovieState())
    val movieState: State<MovieState> = _movieState

    init {
        viewModelScope.launch {
            val networkStatus = networkUseCase.getNetworkStatus(viewModelScope).value

            if (networkStatus == NetworkStatus.Connected) {
                movieUseCase.getMovies().transform { apiState ->
                    when (apiState) {
                        is ApiState.Success -> {
                            _movieState.value = MovieState(
                                list = apiState.data!!
                            )
                        }

                        is ApiState.Loading -> {
                            _movieState.value = MovieState(
                                isLoading = true
                            )
                        }

                        is ApiState.Failure -> {
                            _movieState.value = MovieState(
                                error = apiState.msg.message!!
                            )
                        }
                    }
                    return@transform emit(apiState)
                }.collect()

                movieUseCase.addMoviesToDd(movieState.value)
            } else {
                _movieState.value = MovieState(
                    isInternetOn = false
                )
            }
        }
    }

    fun addToFavourites(movieResponse: MovieResponse) {
        viewModelScope.launch {
            movieUseCase.addMovieToFavourites(mapper.toFavouriteMovieEntity(movieResponse))
        }
    }
}