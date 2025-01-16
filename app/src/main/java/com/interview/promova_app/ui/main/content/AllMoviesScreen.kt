package com.interview.promova_app.ui.main.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.interview.promova_app.R
import com.interview.promova_app.data.remote.MovieResponse
import com.interview.promova_app.ui.main.viewModel.HomeViewModel
import com.interview.promova_app.ui.theme.PromovaTypography

@Composable
fun AllMoviesScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val movieState = viewModel.movieState.collectAsState().value

    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    when {
        !movieState.isInternetOn -> NotificationContent(notificationType = NotificationType.NO_INTERNET)
        movieState.isLoading -> LoadingContent()
        movieState.error.isNotEmpty() -> NotificationContent(notificationType = NotificationType.API_ERROR)
        else -> {
            ListContent(
                list = movieState.list,
                isRefreshing = isRefreshing,
                onPullToRefresh = { viewModel.onPullToRefresh() },
                onAddToFavouriteClick = {
                    viewModel.addToFavourites(it)
                }, onLoadMoreMovies = {
                    viewModel.loadMoreMovies()
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListContent(
    isRefreshing: Boolean,
    list: List<MovieResponse>,
    onAddToFavouriteClick: (MovieResponse) -> Unit,
    onLoadMoreMovies: () -> Unit,
    onPullToRefresh: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onPullToRefresh,
            modifier = Modifier.padding(8.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                itemsIndexed(list) { index, movieResponse ->
                    DateMovieCard(
                        title = movieResponse.title ?: "",
                        overview = movieResponse.overview ?: "",
                        rate = movieResponse.vote_average ?: 0.0,
                        date = movieResponse.release_date ?: "",
                        poster = movieResponse.poster_path ?: "",
                        isFavourite = false,
                        onAddToFavouriteClick = { onAddToFavouriteClick(movieResponse) }
                    )
                    if (index >= list.size - 1) {
                        onLoadMoreMovies()
                    }
                }
            }
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
