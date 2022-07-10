package com.thousandmiles.model.profile

import com.thousandmiles.model.profile.vehicle.Vehicle

data class UserProfile(
    val firstName: String,
    val lastName: String,
    val profilePhotoUri: String,
    val dateOfBirth: String,
    val nationalID: String,
    val mobilityDisabled: Boolean,
    val vehicle: Vehicle
)