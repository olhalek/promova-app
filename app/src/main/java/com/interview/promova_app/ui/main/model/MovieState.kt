package com.interview.promova_app.ui.main.model

import com.interview.promova_app.data.remote.MovieResponse

data class MovieState(
    val list: List<MovieResponse> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false,
    val listPage: Int = 1,
    val isCurrentHomeScreen: Boolean = true
)