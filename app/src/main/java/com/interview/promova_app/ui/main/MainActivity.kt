package com.interview.promova_app.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.interview.promova_app.ui.navigation.AppNavHost
import com.interview.promova_app.ui.navigation.NavigationItem
import com.interview.promova_app.ui.theme.PromovaTheme

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PromovaTheme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    AppNavHost(
                        startDestination = NavigationItem.Login.route,
                        navController = rememberNavController()
                    )
                }
            }
        }
    }
}