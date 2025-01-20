package com.interview.promova_app.ui.main.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.interview.promova_app.ui.main.model.NotificationType
import com.interview.promova_app.ui.main.viewModel.HomeViewModel
import com.interview.promova_app.ui.theme.AppPaddings

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
                .padding(AppPaddings().spacing2),
        ) {
            itemsIndexed(favouriteMoviesList) {index, it ->
                Column(
                    modifier = Modifier.animateItem().padding(top = AppPaddings().spacing1_5),
                    verticalArrangement = Arrangement.spacedBy(AppPaddings().spacing1_25)
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