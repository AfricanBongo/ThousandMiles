package com.thousandmiles.model.profile

import com.thousandmiles.model.profile.user.User
import com.thousandmiles.model.profile.vehicle.Vehicle

data class UserProfile(
    val user: User,
    val vehicle: Vehicle
)