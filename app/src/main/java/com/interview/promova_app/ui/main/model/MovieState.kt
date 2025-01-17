package com.interview.promova_app.ui.main.model

data class MovieState(
    val list: List<Movie> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false,
    val isInternetOn: Boolean = true,
    val listPage: Int = 1,
)