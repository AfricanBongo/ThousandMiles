package com.thousandmiles.viewmodel.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thousandmiles.ui.onboarding.OnboardingScreen

@Composable
fun OnboardingNavigationScreen(
    viewModel: OnboardingNavigationViewModel = viewModel(),
    onDone: () -> Unit
) {

    when (viewModel.onboardingNavigationState) {
        is OnboardingNavigationState.ShouldNotOnboard -> onDone()
        is OnboardingNavigationState.Onboard -> OnboardingScreen()
        else -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}