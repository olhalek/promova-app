package com.interview.promova_app.ui.main.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.interview.promova_app.R
import com.interview.promova_app.ui.main.model.NotificationType
import com.interview.promova_app.ui.theme.AppPaddings
import com.interview.promova_app.ui.theme.CustomTypography

@Composable
fun NotificationContent(notificationType: NotificationType) {
    val strRes =
        when (notificationType) {
            NotificationType.NO_INTERNET -> R.string.no_internet_connection
            NotificationType.EMPTY -> R.string.list_is_empty
            else -> R.string.error_message
        }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(100.dp),
            imageVector = if (notificationType == NotificationType.EMPTY)
                Icons.Rounded.FavoriteBorder else Icons.Rounded.Warning,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(AppPaddings().spacing1))
        Text(
            text = stringResource(id = strRes),
            style = CustomTypography.labelLarge
        )
    }
}