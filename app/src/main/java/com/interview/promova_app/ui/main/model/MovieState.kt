package com.interview.promova_app.ui.main.model

import com.interview.promova_app.data.remote.MovieResponse

data class MovieState(
    val list: List<MovieResponse> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false,
    val isInternetOn: Boolean = true,
    val listPage: Int = 1,
)