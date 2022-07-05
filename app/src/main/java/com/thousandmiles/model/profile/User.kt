package com.thousandmiles.model.profile

import com.thousandmiles.model.profile.vehicle.Vehicle

data class User(
    val userName: String,
    val profilePhotoUrl: String,
    val age: Int,
    val mobilityDisabled: Boolean,
    val privateVehicle: Vehicle
)