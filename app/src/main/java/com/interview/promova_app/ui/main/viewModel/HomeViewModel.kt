package com.interview.promova_app.ui.main.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.promova_app.data.remote.ApiState
import com.interview.promova_app.domain.useCase.MovieUseCase
import com.interview.promova_app.ui.main.model.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: MovieUseCase) : ViewModel() {

    private val _res: MutableState<MovieState> = mutableStateOf(MovieState())
    val res: State<MovieState> = _res

    init {
        viewModelScope.launch {
            useCase.getMovies().transform { apiState ->
                when(apiState) {
                    is ApiState.Success -> {
                        _res.value = MovieState(
                            data = apiState.data!!
                        )
                    }
                    is ApiState.Loading -> {
                        _res.value = MovieState(
                            isLoading = true
                        )
                    }
                    is ApiState.Failure -> {
                        _res.value = MovieState(
                            error = apiState.msg.message!!
                        )
                    }
                }
                return@transform emit (apiState)
            }.collect()
        }
    }
}