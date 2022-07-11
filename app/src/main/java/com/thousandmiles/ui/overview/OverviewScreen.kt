package com.thousandmiles.ui.overview

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.thousandmiles.R
import com.thousandmiles.ui.components.infocard.InfoCard
import com.thousandmiles.ui.components.infocard.InfoCardColors
import com.thousandmiles.ui.theme.*
import com.thousandmiles.viewmodel.overview.*
import kotlinx.coroutines.flow.Flow
import java.time.Duration
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = viewModel(
        factory = OverviewViewModelFactory(LocalContext.current)
    ),
) {
    Column(Modifier
        .fillMaxSize()
        .background(color = lighterBlue)
        .verticalScroll(rememberScrollState())) {

        // Flows to be observed in the UI.
        val stepCount by viewModel
            .stepCountFlow
            .collectAsStateLifecycleAware(initial = 0L)
        val distanceCovered by viewModel
            .distanceCoveredFlow
            .collectAsStateLifecycleAware(initial = 0.0)
        val activeTime by viewModel
            .activeTimeFlow
            .collectAsStateLifecycleAware(initial = Duration.ZERO)

        // User profile values
        val username = viewModel.username
        val photoString = viewModel.profilePhotoUri

        // String resources
        val stepsTitle = stringResource(id = R.string.steps_title)
        val avgFuelPriceTitle = stringResource(id = R.string.fuel_price_title)
        val bOfferTitle = stringResource(id = R.string.best_offer_title)
        val distanceTitle = stringResource(id = R.string.distance_title)
        val activeTitle = stringResource(id = R.string.active_title)
        val emissionTitle = stringResource(id = R.string.emissions_title)
        val dAvgEmissionTitle = stringResource(id = R.string.emission_d_avg_title)
        val pAvgEmissionTitle = stringResource(id = R.string.emission_p_avg_title)


        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
            ) {
                // Profile photo
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Uri.parse(photoString))
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.photo_placeholder),
                    error = painterResource(id = R.drawable.photo_placeholder),
                    modifier = Modifier
                        .size(60.dp)
                        .clip(MaterialTheme.shapes.medium)
                )

                Spacer(modifier = Modifier.width(32.dp))

                Text(
                    text = "Welcome back, ${username.ifBlank { "user" }}!",
                    style = MaterialTheme.typography.h3,
                    color = darkBlue
                )
            }
        }


        Column(modifier = Modifier
            .weight(9f)
            .clip(MaterialTheme.shapes.large.copy(
                bottomStart = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp)
            ))
            .background(color = MaterialTheme.colors.background)
            .padding(start = 16.dp, top = 32.dp, end = 16.dp)
        ) {

            // Today's date
            Text(
                viewModel.dateToday,
                style = MaterialTheme.typography.h5,
                color = darkBlue
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Discount
            InfoCard(
                cardTitle = "Discount earned",
                cardSubtitle = viewModel.discount,
                childrenTitles = avgFuelPriceTitle to bOfferTitle,
                childrenBodies = viewModel.averageFuelPrice to viewModel.bestOffer,
                colors = InfoCardColors(
                    cardTitleColor = MaterialTheme.colors.secondaryVariant,
                    cardSubtitleColor = darkBlue,
                    childrenTitleColor = darkBlue,
                    childrenBodyColor = MaterialTheme.colors.secondaryVariant,
                    childrenBackgroundColor = lighterBlue
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = lightBlue)
                    .padding(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Steps
            InfoCard(
                cardTitle = stepsTitle,
                cardSubtitle = stepCount.toString(),
                childrenTitles = distanceTitle to activeTitle,
                childrenBodies = distanceCovered.inKM() to activeTime.inHrAndMin(),
                colors = InfoCardColors(
                    cardTitleColor = darkYellow,
                    cardSubtitleColor = darkerYellow,
                    childrenTitleColor = darkerYellow,
                    childrenBodyColor = darkYellow,
                    childrenBackgroundColor = lighterYellow
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = lightYellow)
                    .padding(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Emissions
            InfoCard(
                cardTitle = emissionTitle,
                cardSubtitle = viewModel.reducedEmission,
                childrenTitles = dAvgEmissionTitle to pAvgEmissionTitle,
                childrenBodies = viewModel.dailyAverageEmission to viewModel.populationAverage,
                colors = InfoCardColors(
                    cardTitleColor = MaterialTheme.colors.primaryVariant,
                    cardSubtitleColor = MaterialTheme.colors.onPrimary,
                    childrenTitleColor = MaterialTheme.colors.onPrimary,
                    childrenBodyColor = MaterialTheme.colors.primaryVariant,
                    childrenBackgroundColor = lighterGreen
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = lightGreen)
                    .padding(12.dp)
            )
        }
    }
}

@Composable
fun <T : R, R> Flow<T>.collectAsStateLifecycleAware(
    initial: R,
    context: CoroutineContext = EmptyCoroutineContext,
): State<R> {
    val lifecycleAwareFlow = rememberFlow(flow = this)
    return lifecycleAwareFlow.collectAsState(initial = initial, context = context)
}

@Composable
fun <T> rememberFlow(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
): Flow<T> {
    return remember(key1 = flow,
        key2 = lifecycleOwner) {
        flow.flowWithLifecycle(lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED)
    }
}