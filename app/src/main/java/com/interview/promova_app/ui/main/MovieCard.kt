package com.interview.promova_app.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interview.promova_app.R
import com.interview.promova_app.ui.theme.PromovaTheme
import com.interview.promova_app.ui.theme.PromovaTypography

@Composable
fun MovieCard(movie: Movie) {
    Card(shape = RoundedCornerShape(12.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.ic_cinema),
                        contentDescription = null
                    )
                    Text(
                        text = movie.rate,
                        style = PromovaTypography.labelMedium
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = movie.name,
                        style = PromovaTypography.labelLarge
                    )
                    Text(
                        style = PromovaTypography.labelMedium,
                        text = movie.description,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { }) {
                    Text(text = stringResource(id = R.string.like))
                }

                TextButton(onClick = { }) {
                    Text(text = stringResource(id = R.string.share))
                }
            }
        }
    }
}

@Preview
@Composable
private fun MovieCardPreview() {
    PromovaTheme {
        MovieCard(
            Movie(
                name = "Jame Smith",
                description = "dfgn sglk s slkfg s fkg  slkg s gn sglk s slkfg s fkg  slkg s gskd ns fglkgn sglk s slkfg s fkg  slkg s gskd ns fglkgn sglk s slkfg s fkg  slkg s gskd ns fglkgn sglk s slkfg s fkg  slkg s gskd ns fglkgn sglk s slkfg s fkg  slkg s gskd ns fglkgskd ns fglk sn sdlg kns lksdf",
                rate = "4.5"
            )
        )
    }
}

data class Movie(
    val name: String,
    val description: String,
    val rate: String,

    )