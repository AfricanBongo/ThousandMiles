package com.thousandmiles.service.profile

import com.thousandmiles.model.profile.UserProfile
import com.thousandmiles.model.profile.user.User
import java.io.File

interface UserProfileReadService {
    fun readProfile(
        onDataChange: (User) -> Unit,
        onDataReadUnsuccessful: (Throwable) -> Unit
    )

    fun fetchProfilePhoto(
        saveLocation: File,
        onFetchSuccessful: (File) -> Unit,
        onFetchUnsuccessful: (Throwable) -> Unit
    )
}