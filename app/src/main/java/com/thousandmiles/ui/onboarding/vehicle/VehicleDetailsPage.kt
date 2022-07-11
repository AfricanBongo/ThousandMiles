package com.thousandmiles.ui.onboarding.vehicle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.thousandmiles.R
import com.thousandmiles.model.profile.vehicle.VehiclePricing
import com.thousandmiles.model.profile.vehicle.VehicleType
import com.thousandmiles.ui.auth.TextFieldWithErrorOption
import com.thousandmiles.ui.components.HeaderText
import com.thousandmiles.ui.components.MDropDownMenu
import com.thousandmiles.ui.components.PrimaryButton
import com.thousandmiles.ui.components.TextFieldErrorMessage
import com.thousandmiles.viewmodel.onboarding.OnboardingViewModel

@Composable
fun VehicleDetailsPage(
    viewModel: OnboardingViewModel,
    modifier: Modifier = Modifier,
    onPrevious: () -> Unit,
) {
    // String resources
    val title = stringResource(id = R.string.vehicle_details_title)
    val subtitle = stringResource(id = R.string.vehicle_details_subtitle)
    val brandLabel = stringResource(id = R.string.brand)
    val modelLabel = stringResource(id = R.string.model)
    val typeLabel = stringResource(id = R.string.type)
    val priceLabel = stringResource(id = R.string.price)
    val continueButtonLabel = stringResource(id = R.string.continue_btn)
    val previousButtonLabel = stringResource(id = R.string.previous_btn)

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {

        val vehicleInfo = viewModel.vehicleInfo

        // Title and subtitle
        HeaderText(title = title, subtitle = subtitle, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(44.dp))

        // Brand name
        TextFieldWithErrorOption(
            value = vehicleInfo.brandName,
            labelText = brandLabel,
            onValueChange = viewModel::onVehicleBrandNameChange,
            lighterBackground = true,
            isError = viewModel.brandNameBlankError,
            onError = {
                TextFieldErrorMessage(errorMessage = stringResource(id = R.string.brand_blank))
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Model name
        TextFieldWithErrorOption(
            value = vehicleInfo.modelName,
            labelText = modelLabel,
            onValueChange = viewModel::onVehicleModelNameChange,
            lighterBackground = true,
            isError = viewModel.modelNameBlankError,
            onError = {
                TextFieldErrorMessage(errorMessage = stringResource(id = R.string.model_blank))
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Dropdown menu of vehicle types.
        MDropDownMenu(
            label = typeLabel,
            value = vehicleInfo.vehicleType,
            isError = viewModel.vehicleTypeBlankError,
            onError = {
                TextFieldErrorMessage(errorMessage = stringResource(id = R.string.type_blank))
            },
            options = viewModel.vehicleTypes,
            onValueChange = viewModel::onVehicleTypeChange,
            menuItem = { item: VehicleType, onItemSelected: () -> Unit ->
                VehicleTypeMenuItem(item = item, onItemSelected = onItemSelected)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Dropdown menu of vehicle pricings.
        MDropDownMenu(
            label = priceLabel,
            value = vehicleInfo.vehiclePricing,
            isError = viewModel.vehiclePricingBlankError,
            onError = {
                TextFieldErrorMessage(errorMessage = stringResource(id = R.string.pricing_blank))
            },
            options = viewModel.vehiclePricings,
            onValueChange = viewModel::onVehiclePricingChange,
            menuItem = { item: VehiclePricing, onItemSelected: () -> Unit ->
                VehiclePricingMenuItem(item = item, onItemSelected = onItemSelected)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Continue button
        PrimaryButton(
            text = continueButtonLabel,
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth(),
            onClick = viewModel::saveProfile
        )


        Spacer(modifier = Modifier.height(4.dp))

        // Previous button
        TextButton(
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Transparent,
                contentColor = MaterialTheme.colors.secondaryVariant
            ),
            onClick = onPrevious,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = previousButtonLabel,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

