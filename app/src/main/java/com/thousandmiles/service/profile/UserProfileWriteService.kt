package com.thousandmiles.service.profile

import com.thousandmiles.model.profile.UserProfile

interface UserProfileWriteService {
    fun saveProfile(
        profile: UserProfile,
        onSaveSuccessful: () -> Unit,
        onSaveUnsuccessful: (Throwable) -> Unit,
    )
}