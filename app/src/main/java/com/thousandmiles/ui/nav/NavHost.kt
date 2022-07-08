package com.thousandmiles.ui.nav

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.thousandmiles.ui.authentication.AuthenticationScreen
import com.thousandmiles.ui.overview.OverviewScreen

/**
 * A navigation destination within the app.
 */
enum class NavScreen {
    Auth, Overview
}

/**
 * Navigation for the entire app.
 * @param navController An AnimatedNavHostController.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MNavHost(navController: NavHostController) {
    val context = LocalContext.current

    AnimatedNavHost(
        navController = navController,
        startDestination = NavScreen.Auth.name
    ) {
        composable(route = NavScreen.Auth.name) {
            AuthenticationScreen {
                navController.navigate(NavScreen.Overview.name)
            }
        }

        composable(
            route = NavScreen.Overview.name,
            enterTransition = {
                when (initialState.destination.route) {
                    NavScreen.Auth.name ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Up)
                    else -> null
                }
            },
        ) {
            OverviewScreen()
        }
    }
}