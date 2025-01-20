package com.interview.promova_app.ui.main.content

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.interview.promova_app.ui.theme.AppPaddings
import com.interview.promova_app.ui.theme.PromovaTheme
import com.interview.promova_app.ui.theme.CustomTypography

@Composable
fun MovieCard(
    title: String,
    rate: Double,
    overview: String,
    poster: String,
    onFavouriteClick: () -> Unit,
    isFavourite: Boolean
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(IMAGE_BASE_URL + poster)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Card(shape = RoundedCornerShape(AppPaddings().spacing1_5)) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(AppPaddings().spacing1_5)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppPaddings().spacing1_5)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(AppPaddings().spacing1)) {
                    ImageContent(imageState = imageState)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppPaddings().spacing0_5)
                    ) {
                        Icon(imageVector = Icons.Rounded.Star, contentDescription = null)
                        Text(
                            text = rate.toString(),
                            style = CustomTypography.labelLarge
                        )
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(AppPaddings().spacing2)) {
                    Text(
                        text = title,
                        style = CustomTypography.labelLarge
                    )
                    Text(
                        style = CustomTypography.labelMedium,
                        text = overview,
                    )
                }
            }

            val sendIntent: Intent = getSendIntent(title = title)
            val shareIntent = Intent.createChooser(sendIntent, null)
            val context = LocalContext.current

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                val likeRes =
                    if (isFavourite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder
                Button(onClick = onFavouriteClick) {
                    Icon(imageVector = likeRes, contentDescription = null)
                }

                Spacer(modifier = Modifier.width(AppPaddings().spacing1))

                Button(onClick = {
                    context.startActivity(shareIntent)
                }) {
                    Icon(imageVector = Icons.Rounded.Share, contentDescription = null)
                }
            }
        }
    }
}

@Composable
private fun getSendIntent(title: String): Intent {
    return Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "${stringResource(id = R.string.check_out_movie)} - $title"
        )
        type = "text/plain"
    }
}

@Composable
private fun ImageContent(imageState: AsyncImagePainter.State) {
    if (imageState is AsyncImagePainter.State.Error || imageState is AsyncImagePainter.State.Loading) {
        Box(
            modifier = Modifier
                .padding(AppPaddings().spacing0_75)
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
            onFavouriteClick = {},
            isFavourite = false
        )
    }
}