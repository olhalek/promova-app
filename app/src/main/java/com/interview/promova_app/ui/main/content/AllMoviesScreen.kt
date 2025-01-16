package com.interview.promova_app.ui.main.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.interview.promova_app.R
import com.interview.promova_app.data.remote.MovieResponse
import com.interview.promova_app.ui.main.viewModel.HomeViewModel
import com.interview.promova_app.ui.theme.PromovaTypography

@Composable
fun AllMoviesScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val movieState = viewModel.movieState.value

    when {
        !movieState.isInternetOn -> ErrorContent(errorType = ErrorType.NO_INTERNET)
        movieState.isLoading -> LoadingContent()
        movieState.error.isNotEmpty() -> ErrorContent(errorType = ErrorType.API_ERROR)
        else -> {
            ListContent(list = movieState.list, onAddToFavouriteClick = {
                viewModel.addToFavourites(it)
            })
        }
    }
}

@Composable
private fun ListContent(
    list: List<MovieResponse>,
    onAddToFavouriteClick: (MovieResponse) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        items(list) {
            DateMovieCard(
                title = it.title ?: "",
                overview = it.overview ?: "",
                rate = it.vote_average ?: 0.0,
                date = it.release_date ?: "",
                poster = it.poster_path ?: "",
                isFavourite = false,
                onAddToFavouriteClick = { onAddToFavouriteClick(it) }
            )
        }
    }
}

@Preview
@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.loading),
            style = PromovaTypography.labelLarge
        )
    }
}

@Composable
fun ErrorContent(errorType: ErrorType) {
    val strRes =
        if (errorType == ErrorType.NO_INTERNET) R.string.no_internet_connection
        else R.string.error_message

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(100.dp),
            imageVector = Icons.Rounded.Warning,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = strRes),
            style = PromovaTypography.labelLarge
        )
    }
}