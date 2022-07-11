package com.thousandmiles.ui.onboarding.vehicle

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.thousandmiles.model.profile.vehicle.VehiclePricing

@Composable
fun VehiclePricingMenuItem(
    item: VehiclePricing,
    onItemSelected: () -> Unit,
) {
    DropdownMenuItem(
        onClick = onItemSelected
    ) {
        Text(
            text = item.toString(),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.secondaryVariant
        )
    }
}