package com.thousandmiles.viewmodel.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.thousandmiles.service.profile.UserProfileService

class OnboardingNavigationViewModel: ViewModel() {
    var onboardingNavigationState: OnboardingNavigationState by mutableStateOf(OnboardingNavigationState.CheckingIfShouldOnboard)
        private set
    var stateCheckError: Throwable? by mutableStateOf(null)
        private set

    init {
        UserProfileService.doesProfileExist(
            onExists = { exists ->
                onboardingNavigationState = if (exists) {
                    OnboardingNavigationState.ShouldNotOnboard
                } else {
                    OnboardingNavigationState.Onboard
                }
            },
            onError = { error -> stateCheckError = error }
        )
    }
}

sealed class OnboardingNavigationState{
    object Onboard: OnboardingNavigationState()
    object ShouldNotOnboard: OnboardingNavigationState()
    object CheckingIfShouldOnboard: OnboardingNavigationState()
}