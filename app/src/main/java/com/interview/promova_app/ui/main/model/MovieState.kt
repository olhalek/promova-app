package com.interview.promova_app.ui.main.model

import com.interview.promova_app.data.remote.MoviesResponse

data class MovieState(
    val data: List<MoviesResponse.Movie> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)