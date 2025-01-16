package com.interview.promova_app.ui.main.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.promova_app.data.local.FavouriteMovieEntity
import com.interview.promova_app.domain.useCase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val useCase: MovieUseCase) : ViewModel() {

    private val _favouriteMoviesState: MutableState<List<FavouriteMovieEntity>> =
        mutableStateOf(emptyList())
    val favouriteMoviesState: State<List<FavouriteMovieEntity>> = _favouriteMoviesState

    init {
        viewModelScope.launch {
            getMovies()
        }
    }

    suspend fun getMovies() {
        _favouriteMoviesState.value = useCase.getFavouriteMovies()
    }

    fun deleteFromFavourites(favouriteMovieEntity: FavouriteMovieEntity) {
        viewModelScope.launch {
            useCase.deleteMovieFromFavourites(movieEntity = favouriteMovieEntity)
        }
    }
}