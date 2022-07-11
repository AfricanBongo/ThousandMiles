package com.thousandmiles.ui.nav

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.thousandmiles.service.auth.AuthService
import com.thousandmiles.ui.auth.AuthenticationScreen
import com.thousandmiles.ui.overview.OverviewScreen
import com.thousandmiles.ui.onboarding.OnboardingNavigationScreen
import com.thousandmiles.ui.onboarding.WelcomePage

/**
 * A navigation destination within the app.
 */
enum class NavScreen {
    Auth, Overview, Onboarding, Welcome
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
        composable(route = NavScreen.Auth.name) {
            AuthenticationScreen {
                navController.navigate(NavScreen.Onboarding.name)
            }
        }

        composable(
            route = NavScreen.Onboarding.name,
            enterTransition = {
                when (initialState.destination.route) {
                    NavScreen.Auth.name ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Up)
                    else -> null
                }
            },
        ) {
            OnboardingNavigationScreen(
                onFinishedOnboarding = {
                    navController.navigate(NavScreen.Welcome.name)
                },
                onShouldNotOnboard = {
                    navController.navigate(NavScreen.Overview.name)
                }
            )
        }

        composable(
            route = NavScreen.Welcome.name,
            enterTransition = {
                when (initialState.destination.route) {
                    NavScreen.Onboarding.name ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Up)
                    else -> null
                }
            }
        ) {
            WelcomePage(Modifier.fillMaxSize()) {
                navController.navigate(NavScreen.Overview.name)
            }
        }

        composable(
            route = NavScreen.Overview.name,
            enterTransition = {
                when (initialState.destination.route) {
                    NavScreen.Onboarding.name ->
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Up)
                    else -> null
                }
            },
        ) {
            OverviewScreen()
        }

    }
}
