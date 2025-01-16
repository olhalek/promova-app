package com.interview.promova_app.ui.main.viewModel

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val networkUseCase: NetworkUseCase,
    private val mapper: MovieMapper
) : ViewModel() {

    private val _movieState: MutableStateFlow<MovieState> = MutableStateFlow(MovieState())
    val movieState = _movieState.asStateFlow()

    init {
        viewModelScope.launch {
            val networkStatus = networkUseCase.getNetworkStatus(viewModelScope).value

            if (networkStatus == NetworkStatus.Connected) {
                getMovieList()

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

    fun loadMoreMovies() {
        viewModelScope.launch {
            getMovieList()
        }
    }

    private suspend fun getMovieList() {
        movieUseCase.getMovies(
            page = movieState.value.listPage
        ).collectLatest { apiState ->
            when (apiState) {
                is ApiState.Success -> {
                    _movieState.update {
                        it.copy(
                            list = movieState.value.list + apiState.data!!.shuffled(),
                            listPage = movieState.value.listPage + 1,
                            isLoading = false
                        )
                    }
                }

                is ApiState.Loading -> {
                    if(_movieState.value.list.isEmpty()) {
                        _movieState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }

                is ApiState.Failure -> {
                    _movieState.update {
                        it.copy(error = apiState.msg.message!!)
                    }
                }
            }
        }
    }
}