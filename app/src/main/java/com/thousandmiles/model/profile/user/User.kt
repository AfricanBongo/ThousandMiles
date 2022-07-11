package com.thousandmiles.model.profile.user

data class User(
    val firstName: String,
    val lastName: String,
    val profilePhotoUri: String,
    val dateOfBirth: String,
    val nationalID: String,
    val mobilityDisabled: Boolean,
)