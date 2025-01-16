package com.interview.promova_app.ui.main.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.interview.promova_app.R
import com.interview.promova_app.common.IMAGE_BASE_URL
import com.interview.promova_app.ui.theme.PromovaTheme
import com.interview.promova_app.ui.theme.PromovaTypography

@Composable
fun MovieCard(
    title: String,
    rate: Double,
    overview: String,
    poster: String,
    onAddToFavouriteClick: () -> Unit,
    isFavourite: Boolean
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(IMAGE_BASE_URL + poster)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Card(shape = RoundedCornerShape(12.dp)) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ImageContent(imageState = imageState)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Rounded.Star, contentDescription = null)
                        Text(
                            text = rate.toString(),
                            style = PromovaTypography.labelLarge
                        )
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = title,
                        style = PromovaTypography.labelLarge
                    )
                    Text(
                        style = PromovaTypography.labelMedium,
                        text = overview,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                val likeRes = if (isFavourite) R.string.delete_from_favourite else R.string.like
                TextButton(onClick = onAddToFavouriteClick) {
                    Text(text = stringResource(id = likeRes))
                }

                TextButton(onClick = { }) {
                    Text(text = stringResource(id = R.string.share))
                }
            }
        }
    }
}

@Composable
private fun ImageContent(imageState: AsyncImagePainter.State) {
    if (imageState is AsyncImagePainter.State.Error || imageState is AsyncImagePainter.State.Loading) {
        Box(
            modifier = Modifier
                .padding(6.dp)
                .width(150.dp)
                .height(250.dp)
                .clip(RoundedCornerShape(22.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(70.dp),
                imageVector =
                if (imageState is AsyncImagePainter.State.Error) Icons.Rounded.Warning
                else Icons.Rounded.ArrowDropDown,
                contentDescription = null
            )
        }
    }

    if (imageState is AsyncImagePainter.State.Success) {
        Image(
            modifier = Modifier
                .width(150.dp)
                .clip(RoundedCornerShape(22.dp)),
            painter = imageState.painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
private fun MovieCardPreview() {
    PromovaTheme {
        MovieCard(
            title = "Jame Smith",
            overview = "dfgn sglk s slkfg s fkg  slkg s gn sglk s slkfg s fkg  slkg s gskd ns fglkgn sglk s slkfg s fkg  slkg s gskd ns fglkgn sglk s slkfg s fkg  slkg s gskd ns fglkgn sglk s slkfg s fkg  slkg s gskd ns fglkgn sglk s slkfg s fkg  slkg s gskd ns fglkgskd ns fglk sn sdlg kns lksdf",
            rate = 4.5,
            poster = "",
            onAddToFavouriteClick = {},
            isFavourite = false
        )
    }
}