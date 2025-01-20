package com.interview.promova_app.ui.main.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interview.promova_app.ui.theme.AppPaddings
import com.interview.promova_app.ui.theme.PromovaTheme
import com.interview.promova_app.ui.theme.CustomTypography


@Composable
fun DateMovieCard(
    title: String,
    date: String,
    rate: Double,
    overview: String,
    poster: String,
    onFavouriteClick: () -> Unit,
    isFavourite: Boolean
) {
    Column(
        modifier = Modifier.padding(top = AppPaddings().spacing1_5),
        verticalArrangement = Arrangement.spacedBy(AppPaddings().spacing1_25)
    ) {
        Text(
            modifier = Modifier.padding(top = AppPaddings().spacing1_25, start = AppPaddings().spacing0_75),
            text = date,
            style = CustomTypography.labelLarge
        )
        MovieCard(
            title = title,
            rate = rate,
            overview = overview,
            poster = poster,
            onFavouriteClick = onFavouriteClick,
            isFavourite = isFavourite
        )
    }
}

@Preview
@Composable
private fun DateMovieCardPreview() {
    PromovaTheme {
        DateMovieCard(
            title = "Jame Smith",
            overview = "  s slkfg s fkg  slkg s",
            rate = 4.5,
            date = "1 Feb",
            poster = "",
            onFavouriteClick = {},
            isFavourite = false
        )
    }
}