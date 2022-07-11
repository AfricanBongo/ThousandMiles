package com.thousandmiles.viewmodel.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.thousandmiles.model.profile.UserProfile
import com.thousandmiles.model.profile.user.User
import com.thousandmiles.model.profile.user.toDateString
import com.thousandmiles.model.profile.vehicle.Vehicle
import com.thousandmiles.model.profile.vehicle.VehiclePricing
import com.thousandmiles.model.profile.vehicle.VehicleType
import com.thousandmiles.service.profile.UserProfileService
import com.thousandmiles.service.profile.UserProfileWriteService
import java.time.LocalDate

class OnboardingViewModel: ViewModel() {
    private val service: UserProfileWriteService = UserProfileService

    private val blankProfile = UserProfile(
        user = User(
            firstName = "",
            lastName = "",
            profilePhotoUri = "",
            dateOfBirth = "",
            nationalID = "",
            mobilityDisabled = false,
        ),
        vehicle = Vehicle(
            brandName = "",
            modelName = "",
            vehicleType = VehicleType.Blank,
            vehiclePricing = VehiclePricing.Blank
        )
    )


    var userInfo: User by mutableStateOf(blankProfile.user)
        private set
    var vehicleInfo: Vehicle by mutableStateOf(blankProfile.vehicle)
        private set

    var onboardingState: OnboardingState by mutableStateOf(OnboardingState.UserInput)
        private set

    val vehicleTypes: List<VehicleType> by lazy {
        val values = VehicleType.values().toMutableList()
        values.removeLast()
        values
    }

    val vehiclePricings: List<VehiclePricing> by lazy {
        val values = VehiclePricing.values().toMutableList()
        values.removeLast()
        values
    }

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
        userInfo = userInfo.copy(firstName = newValue)
        firstNameBlankError = false
    }

    fun onLastNameChange(newValue: String) {
        userInfo = userInfo.copy(lastName = newValue)
        lastNameBlankError = false
    }

    fun onDateOfBirthChange(newValue: LocalDate) {
        userInfo = userInfo.copy(dateOfBirth = toDateString(newValue))
        dateOfBirthBlankError = false
    }

    fun onProfilePhotoUriChange(newValue: String) {
        userInfo = userInfo.copy(profilePhotoUri = newValue)
    }

    fun onNationalIDChange(newValue: String) {
        userInfo = userInfo.copy(nationalID = newValue)
        nationalIDBlankError = false
        invalidNationalIDError =
            userInfo.isCorrectNationalIDLength() && !userInfo.isCorrectNationalIDFormat()
    }

    fun onMobilityDisabledChange(newValue: Boolean) {
        userInfo = userInfo.copy(mobilityDisabled = newValue)
    }

    fun onVehicleBrandNameChange(newValue: String) {
        vehicleInfo = vehicleInfo.copy(
            brandName = newValue
        )
        brandNameBlankError = false
    }

    fun onVehicleModelNameChange(newValue: String) {
        vehicleInfo = vehicleInfo.copy(
            modelName = newValue
        )
        modelNameBlankError = false
    }

    fun onVehicleTypeChange(newValue: VehicleType) {
        vehicleInfo = vehicleInfo.copy(
            vehicleType = newValue
        )
        vehicleTypeBlankError = false
    }

    fun onVehiclePricingChange(newValue: VehiclePricing) {
        vehicleInfo = vehicleInfo.copy(
            vehiclePricing = newValue
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
        firstNameBlankError = userInfo.firstName.isBlank()
        lastNameBlankError = userInfo.lastName.isBlank()
        dateOfBirthBlankError = userInfo.dateOfBirth.isBlank()
        nationalIDBlankError = userInfo.nationalID.isBlank()
        invalidNationalIDError = !userInfo.isCorrectNationalIDFormat()

        if (!firstNameBlankError && !lastNameBlankError &&
                !dateOfBirthBlankError && !nationalIDBlankError && !invalidNationalIDError)
            return true

        return false
    }

    fun saveProfile() {
        brandNameBlankError = vehicleInfo.brandName.isBlank()
        modelNameBlankError = vehicleInfo.modelName.isBlank()
        vehicleTypeBlankError = vehicleInfo.vehicleType == VehicleType.Blank
        vehiclePricingBlankError = vehicleInfo.vehiclePricing == VehiclePricing.Blank

        if (!brandNameBlankError && !modelNameBlankError &&
                !vehicleTypeBlankError && !vehiclePricingBlankError) {
            onboardingState = OnboardingState.Saving
            service.saveProfile(
                profile = UserProfile(userInfo, vehicleInfo),
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

private fun User.isCorrectNationalIDLength(): Boolean {
    return nationalID.length > 11
}

private fun User.isCorrectNationalIDFormat(): Boolean {
    return formatNationalID(nationalID).matches(Regex("^\\d{2}-\\d{7}[A-Z]-\\d{2}\$"))
}

fun formatNationalID(value: String): String {
    val formattedIDStringBuilder = StringBuilder()

    if (value.length > 2) {
        val firstPortion = value.take(2)
        val secondPortionEndIndex: Int = if (value.length > 10) 9 else value.length - 1
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