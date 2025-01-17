package com.interview.promova_app.ui.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.promova_app.data.network.NetworkStatus
import com.interview.promova_app.data.remote.ApiState
import com.interview.promova_app.domain.useCase.MovieUseCase
import com.interview.promova_app.domain.useCase.NetworkUseCase
import com.interview.promova_app.ui.main.model.Movie
import com.interview.promova_app.ui.main.model.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    networkUseCase: NetworkUseCase,
) : ViewModel() {

    private val _movieState: MutableStateFlow<MovieState> = MutableStateFlow(MovieState())
    val movieState = _movieState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _isMoviesFromDb = MutableStateFlow(false)

    private val networkStatus: StateFlow<NetworkStatus> = networkUseCase.getNetworkStatus().stateIn(
        initialValue = NetworkStatus.Unknown,
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )

    init {
        loadMovies(addToDb = true)
    }

    fun onPullToRefresh() {
        _isRefreshing.update { true }
        _movieState.update { it.copy(list = emptyList()) }

        loadMovies(addToDb = false)
    }

    fun addToFavourites(movie: Movie) {
        viewModelScope.launch {
            movieUseCase.addMovieToFavourites(movie)
        }
    }

    fun loadMovies(addToDb: Boolean) {
        viewModelScope.launch {
            if (networkStatus.value == NetworkStatus.Connected) {
                getMovieList()

                if (_movieState.value.listPage == 1 && addToDb) {
                    movieUseCase.addMoviesToDd(movieState.value.list)
                }
            } else {
                val moviesFromDb = getMoviesFromDb()

                _isRefreshing.update { false }

                if (moviesFromDb.isNotEmpty()) {
                    _movieState.update { it.copy(list = moviesFromDb) }
                } else {
                    _movieState.update {
                        it.copy(isInternetOn = false)
                    }
                }
            }
        }
    }


    private suspend fun getMoviesFromDb(): List<Movie> {
        return movieUseCase.getMoviesFromDd()
    }

    private suspend fun getMovieList() {
        movieUseCase.getMovies(page = movieState.value.listPage).collectLatest { apiState ->
            when (apiState) {
                is ApiState.Success -> {
                    _movieState.update {
                        val moviesList = if (_isMoviesFromDb.value)
                            apiState.data!! else movieState.value.list + apiState.data!!

                        it.copy(
                            list = moviesList,
                            listPage = movieState.value.listPage + 1,
                            isLoading = false
                        )
                    }
                    _isRefreshing.update { false }
                    _isMoviesFromDb.update { false }
                }

                is ApiState.Loading -> {
                    if (_movieState.value.list.isEmpty()) {
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