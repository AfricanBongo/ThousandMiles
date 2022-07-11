package com.thousandmiles.model.profile.vehicle

import androidx.annotation.DrawableRes
import com.thousandmiles.R

/** The type of vehicle that user utilizes.
 *
 * E.g. Sedan
 */
enum class VehicleType(private val description: String, @DrawableRes val imageID: Int) {
    Micro("Micro", R.drawable.ic_micro),
    Sedan("Sedan", R.drawable.ic_sedan),
    CUV("Compact Utility Vehicle", R.drawable.ic_cuv),
    SUV("Sport Utility Vehicle", R.drawable.ic_suv),
    Hatchback("Hatchback", R.drawable.ic_hatchback),
    Roadster("Roadster", R.drawable.ic_roadster),
    Pickup("Pickup truck", R.drawable.ic_pickup),
    Van("Van", R.drawable.ic_van),
    Coupe("Coupe", R.drawable.ic_coupe),
    Minivan("Mini-van", R.drawable.ic_minivan),
    Truck("Truck", R.drawable.ic_truck),
    Blank("", 0);

    override fun toString(): String {
        return description
    }
}