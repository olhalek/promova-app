package com.interview.promova_app.ui.main.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.interview.promova_app.ui.main.viewModel.HomeViewModel

@Composable
fun FavouriteMoviesScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val favouriteMoviesList = viewModel.favouriteMoviesList

    if (favouriteMoviesList.isEmpty()) {
        NotificationContent(notificationType = NotificationType.EMPTY)
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            itemsIndexed(favouriteMoviesList) {index, it ->
                Column(
                    modifier = Modifier.animateItem().padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MovieCard(
                        title = it.title,
                        overview = it.overview,
                        rate = it.rate,
                        poster = it.posterPath,
                        isFavourite = it.isFavourite,
                        onFavouriteClick = {
                            viewModel.handleFavourite(it, index)
                        }
                    )
                }
            }
        }
    }
}