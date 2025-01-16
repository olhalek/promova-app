package com.interview.promova_app.ui.main.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.interview.promova_app.ui.main.viewModel.FavouriteViewModel

@Composable
fun FavouriteMoviesScreen(
    viewModel: FavouriteViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getMovies()
    }

    val favouriteMoviesList = viewModel.favouriteMoviesState.value

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        items(favouriteMoviesList) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MovieCard(
                    title = it.title,
                    overview = it.overview,
                    rate = it.vote_average,
                    poster = it.poster_path,
                    isFavourite = true,
                    onAddToFavouriteClick = {
                        viewModel.deleteFromFavourites(it)
                    }
                )
            }
        }
    }
}