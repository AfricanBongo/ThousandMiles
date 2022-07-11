package com.thousandmiles.ui.onboarding.vehicle

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.thousandmiles.model.profile.vehicle.VehicleType

@Composable
fun VehicleTypeMenuItem(
    item: VehicleType,
    onItemSelected: () -> Unit,
) {
    DropdownMenuItem(
        onClick = onItemSelected
    ) {

        Text(
            text = item.name,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.secondaryVariant
        )

        Spacer(modifier = Modifier.width(8.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(item.imageID).build(),
            contentDescription = item.toString(),
            modifier = Modifier.requiredSize(24.dp)
        )
    }
}