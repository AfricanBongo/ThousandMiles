package com.thousandmiles.viewmodel.overview

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.thousandmiles.model.profile.UserProfile
import com.thousandmiles.model.profile.user.User
import com.thousandmiles.model.profile.vehicle.Vehicle
import com.thousandmiles.model.profile.vehicle.VehiclePricing
import com.thousandmiles.model.profile.vehicle.VehicleType
import com.thousandmiles.service.profile.UserProfileReadService
import com.thousandmiles.service.profile.UserProfileService
import com.thousandmiles.service.steps.StepCountService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class OverviewViewModel(context: Context) : ViewModel() {
    private val sensorService = StepCountService(context)
    private val profileService: UserProfileReadService = UserProfileService

    // Sensor data flows
    val stepCountFlow: Flow<Long> = sensorService.cumulativeStepCount
    val activeTimeFlow: Flow<Duration> = sensorService.cumulativeActiveTime
    val distanceCoveredFlow: Flow<Double> = sensorService.cumulativeDistanceCovered

    // Profile data
    var username by mutableStateOf("")
        private set
    var profilePhotoUri by mutableStateOf("")
        private set

    // Emissions data
    var reducedEmission by mutableStateOf("")
        private set
    var dailyAverageEmission by mutableStateOf("")
        private set
    val populationAverage = "2.4kg"

    // Pricing data
    var discount by mutableStateOf("")
        private set
    val averageFuelPrice = "$1.82 / L"
    val bestOffer = "$1.76 / L"


    private val blankUser = User(
        firstName = "",
        lastName = "",
        profilePhotoUri = "",
        dateOfBirth = "",
        nationalID = "",
        mobilityDisabled = false,
    )

    private var userProfile: User by mutableStateOf(blankUser)

    init {
        // Fetch user profile data
        profileService.readProfile(
            onDataChange = { userProfile ->
                this.userProfile = userProfile
                username = userProfile.firstName
                profilePhotoUri = userProfile.profilePhotoUri
            },
            onDataReadUnsuccessful = { /* Do nothing */ }
        )

        viewModelScope.launch {
            distanceCoveredFlow.collect { distance ->
                // Calculate discount
                val discountCalc = distance * (0.001)
                discount = String.format("$%.2f", discountCalc)

                // Calculate carbon emission reduced
                val carbonEmissionCalc = distance * Random.nextDouble(from = 0.0003, until = 0.0005)
                reducedEmission = String.format("%.3fkg", carbonEmissionCalc)
                dailyAverageEmission = String.format("%.1fkg", carbonEmissionCalc)
            }
        }
    }

    val dateToday = dateTodayAsString()

    private fun dateTodayAsString(): String {
        var dateString = "Today, "
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM")
        dateString += dateTimeFormatter.format(LocalDate.now())
        return dateString
    }

}

/**
 * In hours and minutes, e.g. "15hr 6min"
 */
fun Duration.inHrAndMin(): String {
    return "${toHours()}hr ${toMinutes().mod(60L)}min"
}

/**
 * As a kilometers string.
 */
fun Double.inKM(): String {
    return String.format("%.1fkm", div(1000))
}

class OverviewViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(Context::class.java)
            .newInstance(context)
    }
}