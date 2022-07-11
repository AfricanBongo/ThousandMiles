package com.thousandmiles.ui.permissions

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthDataRequestPermissions
import androidx.health.connect.client.permission.Permission
import androidx.health.connect.client.records.ActivitySession
import androidx.health.connect.client.records.Distance
import androidx.health.connect.client.records.Steps
import com.thousandmiles.ui.theme.lighterBlue


@Composable
fun HealthConnectScreen(
    modifier: Modifier = Modifier,
    onPermissionsGranted: () -> Unit,
) {

    val context = LocalContext.current

    // If Health Connect App is installed.
    if (HealthConnectClient.isAvailable(context)) {

        val healthConnectClient: HealthConnectClient by remember {
            mutableStateOf(HealthConnectClient.getOrCreate(context))
        }


        // Permissions to be used in the application.
        val permissions = setOf(
            Permission.createReadPermission(Steps::class),
            Permission.createReadPermission(Distance::class),
            Permission.createReadPermission(ActivitySession::class)
        )

        // Launch to request for read permissions for the app.
        val requestPermissionsLauncher =
            rememberLauncherForActivityResult(HealthDataRequestPermissions()) { granted ->
                if (granted.containsAll(permissions)) {
                    onPermissionsGranted()
                }
            }

        // Check if the app had been given the appropriate permissions.
        LaunchedEffect(key1 = healthConnectClient) {
            val granted =
                healthConnectClient.permissionController.getGrantedPermissions(permissions)
            if (granted.containsAll(permissions)) {
                onPermissionsGranted()
            } else {
                requestPermissionsLauncher.launch(permissions)
            }
        }

    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = lighterBlue)
                .padding(16.dp)
                .then(modifier),
            contentAlignment = Alignment.Center
        ) {
            PromptForHealthConnect(context)
        }
    }


}
