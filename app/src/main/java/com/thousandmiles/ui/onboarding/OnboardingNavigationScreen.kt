package com.thousandmiles.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thousandmiles.viewmodel.onboarding.OnboardingNavigationState
import com.thousandmiles.viewmodel.onboarding.OnboardingNavigationViewModel

@Composable
fun OnboardingNavigationScreen(
    viewModel: OnboardingNavigationViewModel = viewModel(),
    onFinishedOnboarding: () -> Unit,
    onShouldNotOnboard: () -> Unit,
) {

    when (viewModel.onboardingNavigationState) {
        is OnboardingNavigationState.ShouldNotOnboard -> onShouldNotOnboard()
        is OnboardingNavigationState.Onboard -> OnboardingScreen(
            onDone = onFinishedOnboarding,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        )
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