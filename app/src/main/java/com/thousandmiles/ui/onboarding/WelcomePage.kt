package com.thousandmiles.viewmodel.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thousandmiles.R
import com.thousandmiles.ui.components.DevicePreview
import com.thousandmiles.ui.components.PrimaryButton
import com.thousandmiles.ui.theme.ThousandMilesTheme

@Composable
fun WelcomePage(
    modifier: Modifier = Modifier,
    onContinue: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Welcome image
        Image(
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = stringResource(id = R.string.welcome_descr))

        Spacer(modifier = Modifier.height(56.dp))

        // Welcome message
        Text(
            text = stringResource(id = R.string.welcome),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Normal,
            )
        )

        Spacer(modifier = Modifier.height(60.dp))

        // "Let's start" button
        PrimaryButton(
            text = stringResource(id = R.string.start),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
        )
    }
}

@DevicePreview
@Composable
private fun WelcomePagePreview() {
    ThousandMilesTheme {
        WelcomePage(Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(vertical = 48.dp, horizontal = 16.dp)
        ) {

        }
    }
}