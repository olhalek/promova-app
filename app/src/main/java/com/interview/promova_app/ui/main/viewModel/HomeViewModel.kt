package com.interview.promova_app.ui.main.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.promova_app.data.network.NetworkStatus
import com.interview.promova_app.data.remote.ApiState
import com.interview.promova_app.domain.useCase.MovieUseCase
import com.interview.promova_app.domain.useCase.NetworkUseCase
import com.interview.promova_app.ui.main.model.Movie
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
    private val favouriteMovieUseCase: MovieUseCase,
    networkUseCase: NetworkUseCase,
) : ViewModel() {

    private val _movieList = mutableStateListOf<Movie>()
    val movieList : List<Movie>
        get() = _movieList.toList()

    private val _favouriteMoviesList = mutableStateListOf<Movie>()
    val favouriteMoviesList: List<Movie>
        get() = _favouriteMoviesList

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isInternetOn = MutableStateFlow(true)
    val isInternetOn = _isInternetOn.asStateFlow()

    private val _listPage = MutableStateFlow(1)

    private val _moviesError = MutableStateFlow("")
    val moviesError = _moviesError.asStateFlow()

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
        viewModelScope.launch {
            loadFavouriteMovies()

            loadMovies(addToDb = true)
        }
    }

    fun onPullToRefresh() {
        _isRefreshing.update { true }
        _movieList.clear()

        loadMovies(addToDb = false)
    }

    fun handleFavourite(movie: Movie, index: Int) {
        viewModelScope.launch {
            if (_favouriteMoviesList.any { it.id == movie.id }) {
                deleteFromFavourites(movie)

                if (_movieList.any { it.id == movie.id }) {
                    _movieList[index] = movie.copy(isFavourite = false)
                }
            } else {
                movieUseCase.addMovieToFavourites(movie)

                _favouriteMoviesList.add(movie.copy(isFavourite = true))

                _movieList[index] = movie.copy(isFavourite = true)
            }
        }
    }

    fun loadMovies(addToDb: Boolean) {
        viewModelScope.launch {
            if (networkStatus.value != NetworkStatus.Connected) {
                getMovieList()

                if (_listPage.value == 2 && addToDb) {
                    movieUseCase.addMoviesToDd(_movieList)
                }
            } else {
                val moviesFromDb = getMoviesFromDb()

                _isRefreshing.update { false }

                if (moviesFromDb.isNotEmpty()) {
                    _movieList.addAll(moviesFromDb)
                } else {
                    _isInternetOn.update { false }
                }
            }
        }
    }

    private suspend fun loadFavouriteMovies() {
        _favouriteMoviesList.addAll(favouriteMovieUseCase.getFavouriteMovies())
    }

    private suspend fun deleteFromFavourites(movie: Movie) {
        favouriteMovieUseCase.deleteMovieFromFavourites(movie = movie)
        _favouriteMoviesList.removeIf { it.id == movie.id }
    }

    private suspend fun getMoviesFromDb(): List<Movie> {
        return movieUseCase.getMoviesFromDd()
    }

    private suspend fun getMovieList() {
        movieUseCase.getMovies(page = _listPage.value).collectLatest { apiState ->
            when (apiState) {
                is ApiState.Success -> {
                    val listToAdd =  if (_isMoviesFromDb.value)
                        apiState.data!! else _movieList + apiState.data!!

                    _movieList.clear()
                    _movieList.addAll(
                       listToAdd.map { movie ->
                            if (_favouriteMoviesList.any { favouriteMovie ->
                                    favouriteMovie.id == movie.id
                                }) {
                                movie.copy(isFavourite = true)
                            } else movie
                        }
                    )

                    _isLoading.update { false }
                    _listPage.update { it + 1 }
                    _isRefreshing.update { false }
                    _isMoviesFromDb.update { false }
                }

                is ApiState.Loading -> {
                    if (_movieList.isEmpty()) {
                        _isLoading.update { true }
                    }
                }

                is ApiState.Failure -> {
                    _moviesError.update { apiState.msg.message ?: "" }
                }
            }
        }
    }
}