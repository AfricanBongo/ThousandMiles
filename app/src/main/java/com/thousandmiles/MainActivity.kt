package com.thousandmiles

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.thousandmiles.service.auth.AuthService
import com.thousandmiles.ui.nav.MNavHost
import com.thousandmiles.ui.theme.ThousandMilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThousandMilesApp(window)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ThousandMilesApp(window: Window?) {
    ThousandMilesTheme {
        window?.statusBarColor = MaterialTheme.colors.secondary.toArgb()
        val navController = rememberAnimatedNavController()
        MNavHost(navController)
    }
}