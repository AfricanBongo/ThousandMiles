package com.thousandmiles.model.profile.vehicle

data class Vehicle(
    val brandName: String,
    val modelName: String,
    val vehicleType: VehicleType,
    val vehiclePricing: VehiclePricing
)