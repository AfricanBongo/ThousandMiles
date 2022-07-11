package com.thousandmiles.model.profile.vehicle

/**
 * Price of the vehicle, used in calculating carbon emission.
 */
enum class VehiclePricing(private val priceRange: String) {
    Cheap("$0 - $9 999"),
    Affordable("$10 000 - $19 999"),
    Expensive("$20 000 - $99 999"),
    VeryExpensive("> $100 000"),
    Blank("");

    override fun toString(): String {
        return priceRange
    }
}