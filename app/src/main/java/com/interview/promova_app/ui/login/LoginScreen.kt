package com.interview.promova_app.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interview.promova_app.R
import com.interview.promova_app.ui.theme.PromovaTheme
import com.interview.promova_app.ui.theme.PromovaTypography

@Composable
fun LoginScreen(navigateToHome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.sizeIn(maxWidth = 200.dp),
            painter = painterResource(id = R.drawable.ic_cinema),
            contentDescription = null
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = navigateToHome
        ) {
            Text(
                text = "Sign in with Facebook",
                style = PromovaTypography.headlineMedium
            )
        }


    }
}

@Composable
@Preview
private fun LoginScreenPreview() {
    PromovaTheme {
        LoginScreen({})
    }
}