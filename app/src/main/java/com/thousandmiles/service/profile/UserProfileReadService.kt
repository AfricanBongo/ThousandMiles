package com.thousandmiles.service.profile

import com.thousandmiles.model.profile.UserProfile
import java.io.File

interface UserProfileReadService {
    fun readProfile(
        onDataChange: (UserProfile) -> Unit,
        onDataReadUnsuccessful: (Throwable) -> Unit
    )

    fun fetchProfilePhoto(
        saveLocation: File,
        onFetchSuccessful: (File) -> Unit,
        onFetchUnsuccessful: (Throwable) -> Unit
    )
}