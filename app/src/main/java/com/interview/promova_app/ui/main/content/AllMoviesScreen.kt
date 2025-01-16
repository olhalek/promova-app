package com.interview.promova_app.ui.main.content

import androidx.compose.foundation.Image
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.interview.promova_app.R
import com.interview.promova_app.ui.main.model.MovieState
import com.interview.promova_app.ui.main.viewModel.HomeViewModel
import com.interview.promova_app.ui.theme.PromovaTypography

@Composable
fun AllMoviesScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val movieState = viewModel.movieState.value

    if (movieState.isLoading) {
        LoadingContent()
    }

    if (movieState.error.isNotEmpty()) {
        ErrorContent()
    }

    AllMoviesContent(movieState = movieState)
}

@Composable
private fun AllMoviesContent(movieState: MovieState) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
    ) {
        items(movieState.list) {
            MovieCard(
                title = it.title ?: "",
                overview = it.overview ?: "",
                rate = it.vote_average ?: 0.0,
                date = it.release_date ?: "",
                poster = it.poster_path ?: ""
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

@Preview
@Composable
fun ErrorContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.loading),
            style = PromovaTypography.labelLarge
        )
    }
}
