package com.interview.promova_app.ui.main.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.interview.promova_app.R
import com.interview.promova_app.ui.main.model.Movie
import com.interview.promova_app.ui.main.viewModel.HomeViewModel
import com.interview.promova_app.ui.theme.CustomTypography

@Composable
fun AllMoviesScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val isInternetOn = viewModel.isInternetOn.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val moviesError = viewModel.moviesError.collectAsState().value

    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    when {
        !isInternetOn -> NotificationContent(notificationType = NotificationType.NO_INTERNET)
        isLoading -> LoadingContent()
        moviesError.isNotEmpty() -> NotificationContent(notificationType = NotificationType.API_ERROR)
        else -> {
            ListContent(
                list = viewModel.filteredList,
                isRefreshing = isRefreshing,
                onPullToRefresh = { viewModel.onPullToRefresh() },
                onFilterClicked = { viewModel.filterList(it) },
                onFavouriteClick = { movie, index ->
                    viewModel.handleFavourite(movie, index)
                },
                onLoadMoreMovies = {
                    viewModel.loadMovies(addToDb = false)
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListContent(
    isRefreshing: Boolean,
    list: List<Movie>,
    onFilterClicked: (FilterSegmentedButtons) -> Unit,
    onFavouriteClick: (Movie, Int) -> Unit,
    onLoadMoreMovies: () -> Unit,
    onPullToRefresh: () -> Unit
) {
    var chosenSegmentedButton by remember { mutableStateOf(FilterSegmentedButtons.NONE) }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            FilterSegmentedButtons.entries.forEachIndexed { index, button ->
                SegmentedButton(
                    selected = chosenSegmentedButton == button,
                    onClick = {
                        chosenSegmentedButton = button
                        onFilterClicked(button)
                    },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = FilterSegmentedButtons.entries.size
                    ),
                ) {
                    Text(
                        text = stringResource(id = button.strRes),
                        style = CustomTypography.labelMedium
                    )
                }
            }
        }
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                onPullToRefresh()
                chosenSegmentedButton = FilterSegmentedButtons.NONE
            },
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(list) { index, movie ->
                    DateMovieCard(
                        title = movie.title,
                        overview = movie.overview,
                        rate = movie.rate,
                        date = movie.releaseDate,
                        poster = movie.posterPath,
                        isFavourite = movie.isFavourite,
                        onFavouriteClick = { onFavouriteClick(movie, index) }
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
            style = CustomTypography.labelLarge
        )
    }
}
