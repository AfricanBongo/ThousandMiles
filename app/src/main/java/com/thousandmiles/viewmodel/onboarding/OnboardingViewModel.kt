package com.thousandmiles.viewmodel.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.thousandmiles.model.profile.UserProfile
import com.thousandmiles.model.profile.vehicle.Vehicle
import com.thousandmiles.model.profile.vehicle.VehiclePricing
import com.thousandmiles.model.profile.vehicle.VehicleType
import com.thousandmiles.service.profile.UserProfileService
import com.thousandmiles.service.profile.UserProfileWriteService

class OnboardingViewModel: ViewModel() {
    private val service: UserProfileWriteService = UserProfileService

    private val blankProfile = UserProfile(
        firstName = "",
        lastName = "",
        profilePhotoUri = "",
        dateOfBirth = "",
        nationalID = "",
        mobilityDisabled = false,
        vehicle = Vehicle(
            brandName = "",
            modelName = "",
            vehicleType = VehicleType.Blank,
            vehiclePricing = VehiclePricing.Blank
        )
    )

    var userProfile: UserProfile by mutableStateOf(blankProfile)
        private set
    var onboardingState: OnboardingState by mutableStateOf(OnboardingState.UserInput)

    // Field errors
    var firstNameBlankError by mutableStateOf(false)
        private set
    var lastNameBlankError by mutableStateOf(false)
        private set
    var dateOfBirthBlankError by mutableStateOf(false)
        private set
    var nationalIDBlankError by mutableStateOf(false)
        private set
    var invalidNationalIDError by mutableStateOf(false)
        private set
    var brandNameBlankError by mutableStateOf(false)
        private set
    var modelNameBlankError by mutableStateOf(false)
        private set
    var vehicleTypeBlankError by mutableStateOf(false)
        private set
    var vehiclePricingBlankError by mutableStateOf(false)
        private set


    fun onFirstNameChange(newValue: String) {
        userProfile = userProfile.copy(firstName = newValue)
        firstNameBlankError = false
    }

    fun onLastNameChange(newValue: String) {
        userProfile = userProfile.copy(lastName = newValue)
        lastNameBlankError = false
    }

    fun onDateOfBirthChange(newValue: String) {
        userProfile = userProfile.copy(dateOfBirth = newValue)
        dateOfBirthBlankError = false
    }

    fun onProfilePhotoUriChange(newValue: String) {
        userProfile = userProfile.copy(profilePhotoUri = newValue)
    }

    fun onNationalIDChange(newValue: String) {
        userProfile = userProfile.copy(dateOfBirth = newValue)
        nationalIDBlankError = false
        invalidNationalIDError =
            userProfile.isCorrectNationalIDLength() && !userProfile.isCorrectNationalIDFormat()
    }

    fun onMobilityDisabledChange(newValue: Boolean) {
        userProfile = userProfile.copy(mobilityDisabled = newValue)
    }

    fun onVehicleBrandNameChange(newValue: String) {
        userProfile = userProfile.copy(
            vehicle = userProfile.vehicle.copy(
                brandName = newValue
            )
        )
        brandNameBlankError = false
    }

    fun onVehicleModelNameChange(newValue: String) {
        userProfile = userProfile.copy(
            vehicle = userProfile.vehicle.copy(
                modelName = newValue
            )
        )
        modelNameBlankError = false
    }

    fun onVehicleTypeChange(newValue: VehicleType) {
        userProfile = userProfile.copy(
            vehicle = userProfile.vehicle.copy(
                vehicleType = newValue
            )
        )
        vehicleTypeBlankError = false
    }

    fun onVehiclePricingChange(newValue: VehiclePricing) {
        userProfile = userProfile.copy(
            vehicle = userProfile.vehicle.copy(
                vehiclePricing = newValue
            )
        )
        vehiclePricingBlankError = false
    }

    /**
     * Make sure that the:
     *
     * - firstName
     * - lastName
     * - dateOfBirth
     * - nationalID
     *
     * are not blank and if the nationID format is correct.
     * @return True if all are not blank and format is correct
     * and false if one or more are blank or format is incorrect.
     */
    fun validateUserDetails(): Boolean {
        firstNameBlankError = userProfile.firstName.isBlank()
        lastNameBlankError = userProfile.lastName.isBlank()
        dateOfBirthBlankError = userProfile.dateOfBirth.isBlank()
        nationalIDBlankError = userProfile.nationalID.isBlank()

        if (!firstNameBlankError && !lastNameBlankError &&
                !dateOfBirthBlankError && !nationalIDBlankError && !invalidNationalIDError)
            return true

        return false
    }

    fun saveProfile() {
        brandNameBlankError = userProfile.vehicle.brandName.isBlank()
        modelNameBlankError = userProfile.vehicle.modelName.isBlank()
        vehicleTypeBlankError = userProfile.vehicle.vehicleType == VehicleType.Blank
        vehiclePricingBlankError = userProfile.vehicle.vehiclePricing == VehiclePricing.Blank

        if (!brandNameBlankError && !modelNameBlankError &&
                !vehicleTypeBlankError && !vehiclePricingBlankError) {
            onboardingState = OnboardingState.Saving
            service.saveProfile(
                profile = userProfile,
                onSaveSuccessful = { onboardingState = OnboardingState.SaveSuccess },
                onSaveUnsuccessful = { error ->
                    onboardingState = OnboardingState.SaveFailure(error.message ?: "Unknown error")
                }
            )
        }
    }

    fun acknowledgeSaveFailure() {
        onboardingState = OnboardingState.UserInput
    }
}

private fun UserProfile.isCorrectNationalIDLength(): Boolean {
    return nationalID.length > 11
}

private fun UserProfile.isCorrectNationalIDFormat(): Boolean {
    return nationalID.matches(Regex("^\\d{2}-\\d{7}[A-Z]-\\d{2}\$"))
}

private fun formatNationalID(value: String): String {
    val formattedIDStringBuilder = StringBuilder()

    if (value.length > 2) {
        val firstPortion = value.take(2)
        val secondPortionEndIndex: Int = if (value.length > 10) 9 else value.length
        val secondPortion = value.substring(2..secondPortionEndIndex)

        formattedIDStringBuilder
            .append(firstPortion)
            .append("-")
            .append(secondPortion)

        if (value.length > 10) {
            val thirdPortion = value.substring(10 until value.length)

            formattedIDStringBuilder.append("-").append(thirdPortion)
        }
    } else {
        return value
    }

    return formattedIDStringBuilder.toString()
}

sealed class OnboardingState {
    object UserInput: OnboardingState()
    object Saving: OnboardingState()
    object SaveSuccess: OnboardingState()
    class SaveFailure(val errorMessage: String): OnboardingState()
}