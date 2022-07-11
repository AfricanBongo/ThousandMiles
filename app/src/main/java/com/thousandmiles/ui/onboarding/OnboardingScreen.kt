package com.thousandmiles.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.thousandmiles.R
import com.thousandmiles.ui.components.AppErrorDialog
import com.thousandmiles.ui.components.LoadingDialog
import com.thousandmiles.ui.onboarding.user.UserDetailsPage
import com.thousandmiles.ui.onboarding.vehicle.VehicleDetailsPage
import com.thousandmiles.viewmodel.onboarding.OnboardingState
import com.thousandmiles.viewmodel.onboarding.OnboardingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = viewModel(),
    onDone: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val onboardingState = viewModel.onboardingState
    val pagerChildModifier = Modifier
        .fillMaxSize()
        .padding(all = 16.dp)
        .background(color = Color.Transparent)

    HorizontalPager(
        count = 2,
        state = pagerState,
        userScrollEnabled = false,
        modifier = modifier
    ) { page ->
        if (page == 0) {
            UserDetailsPage(
                viewModel = viewModel,
                modifier = pagerChildModifier
            ) {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            }
        } else {
            VehicleDetailsPage(
                viewModel = viewModel,
                modifier = pagerChildModifier
            ) {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            }
        }
    }

    when (onboardingState) {
        is OnboardingState.SaveSuccess -> onDone()
        is OnboardingState.UserInput -> { /* Do nothing */ }
        is OnboardingState.Saving -> {
            LoadingDialog(
                loadingText = stringResource(id = R.string.saving_profile),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = MaterialTheme.colors.background)
                    .padding(vertical = 32.dp, horizontal = 16.dp)
            )
        }
        is OnboardingState.SaveFailure -> {
            AppErrorDialog(
                errorTitle = stringResource(id = R.string.childish_error_title),
                errorMessage = onboardingState.errorMessage,
                onDismissRequest = viewModel::acknowledgeSaveFailure,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = MaterialTheme.colors.background)
                    .padding(top = 32.dp)
            )
        }
    }
}