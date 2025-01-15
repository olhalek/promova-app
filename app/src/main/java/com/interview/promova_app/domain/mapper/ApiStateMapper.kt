package com.interview.promova_app.domain.mapper

import com.interview.promova_app.data.remote.ApiState

fun <T, R> ApiState<T>.map(transform: (T) -> R): ApiState<R> {
    return when (this) {
        is ApiState.Success -> ApiState.Success(transform(data))
        is ApiState.Failure -> ApiState.Failure(msg)
        ApiState.Loading -> ApiState.Loading
    }
}