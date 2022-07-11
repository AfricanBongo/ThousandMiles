package com.thousandmiles.ui.nav

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.thousandmiles.service.auth.AuthService
import com.thousandmiles.ui.auth.AuthenticationScreen
import com.thousandmiles.ui.overview.OverviewScreen
import com.thousandmiles.ui.onboarding.OnboardingNavigationScreen
import com.thousandmiles.ui.onboarding.WelcomePage
import com.thousandmiles.ui.permissions.HealthConnectScreen

/**
 * A navigation destination within the app.
 */
enum class NavScreen {
    Auth, Overview, Onboarding, Welcome, HealthConnect
}

/**
 * Navigation for the entire app.
 * @param navController An AnimatedNavHostController.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MNavHost(navController: NavHostController) {

    // Whether a user was already signed in or not.
    val authenticated: Boolean = AuthService.isSignedIn()

    AnimatedNavHost(
        navController = navController,
        startDestination = if (authenticated) NavScreen.Onboarding.name else NavScreen.Auth.name
    ) {

        composable(
            route = NavScreen.Auth.name,
        ) {
            AuthenticationScreen {
                navController.navigate(NavScreen.Onboarding.name)
            }
        }

        composable(
            route = NavScreen.Onboarding.name,
        ) {
            OnboardingNavigationScreen(
                onFinishedOnboarding = {
                    navController.navigate(NavScreen.Welcome.name)
                },
                onShouldNotOnboard = {
                    navController.navigate(NavScreen.HealthConnect.name)
                }
            )
        }

        composable(
            route = NavScreen.Welcome.name,
        ) {
            WelcomePage(Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 40.dp, end = 16.dp, bottom = 16.dp)
            ) {
                navController.navigate(NavScreen.HealthConnect.name)
            }
        }

        composable(
            route = NavScreen.HealthConnect.name,
        ) {
            HealthConnectScreen {
                navController.navigate(NavScreen.Overview.name)
            }
        }

        composable(
            route = NavScreen.Overview.name,
        ) {
            OverviewScreen()
        }
    }
}
