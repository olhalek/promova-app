package com.interview.promova_app.ui.main.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interview.promova_app.ui.theme.PromovaTheme
import com.interview.promova_app.ui.theme.PromovaTypography


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
        modifier = Modifier.padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 10.dp, start = 6.dp),
            text = date,
            style = PromovaTypography.labelLarge
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
            overview = "dfgn sglk s slkfg s fkg  slkg s gn sglk s slkfg s fkg  slkg s gskd ns fglkgn sglk s slkfg s fkg  slkg s gskd ns fglkgn sglk s slkfg s fkg  slkg s gskd ns fglkgn sglk s slkfg s fkg  slkg s gskd ns fglkgn sglk s slkfg s fkg  slkg s gskd ns fglkgskd ns fglk sn sdlg kns lksdf",
            rate = 4.5,
            date = "1 Feb",
            poster = "",
            onFavouriteClick = {},
            isFavourite = false
        )
    }
}