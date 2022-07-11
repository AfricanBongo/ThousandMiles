package com.thousandmiles.ui.permissions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thousandmiles.R
import com.thousandmiles.ui.components.PrimaryButton
import com.thousandmiles.ui.theme.darkBlue

@Composable
fun PromptForHealthConnect(context: Context) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)) {

        // Salutations
        Text(
            text = "Hey there!",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.secondaryVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Message to install Health Connect
        Text(
            text = stringResource(id = R.string.no_health_connect),
            style = MaterialTheme.typography.body1,
            color = darkBlue,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(36.dp))

        // Button that opens Google Play Store.
        PrimaryButton(
            text = stringResource(id = R.string.health_connect_btn),
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
        ) {
            val healthConnectPackage = "com.google.android.apps.healthdata"

            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        // Link to Health Connect in Google Play Store
                        Uri.parse("market://details?id=$healthConnectPackage")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        // Link to Health Connect in Google Play Store
                        Uri.parse("https://play.google.com/store/apps/details?id=$healthConnectPackage")
                    )
                )
            } finally {
                (context as Activity).finishAffinity()
            }
        }
    }
}