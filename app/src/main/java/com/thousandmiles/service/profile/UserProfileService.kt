package com.thousandmiles.service.profile

import android.net.Uri
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.thousandmiles.model.profile.UserProfile
import com.thousandmiles.service.auth.AuthService
import java.io.File

object UserProfileService: UserProfileReadService, UserProfileWriteService {
    private val databaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("users")
    }

    private val storageReference: StorageReference by lazy {
        FirebaseStorage.getInstance().getReference("users")
    }

    private val uidErrorMessage by lazy { "Users credentials were not detected, make sure you're signed in." }

    override
    fun saveProfile(
        profile: UserProfile,
        onSaveSuccessful: () -> Unit,
        onSaveUnsuccessful: (Throwable) -> Unit,
    ) {
        val uid = AuthService.uid

        // Attempt to upload user profile information to the Firebase server.
        if (uid != null) {
            databaseReference.child(uid).setValue(profile).addOnCompleteListener {
                val error = it.exception
                if (error == null) {
                    onSaveSuccessful()
                } else {
                    onSaveUnsuccessful(error)
                }
            }

            // Attempt to upload user profile image to the cloud.
            val imageStorageReference = storageReference.child(uid)
            imageStorageReference.putFile(Uri.parse(profile.user.profilePhotoUri)).addOnFailureListener(onSaveUnsuccessful)
        } else {
            onSaveUnsuccessful(Throwable(uidErrorMessage))
        }
    }

    override
    fun readProfile(
        onDataChange: (UserProfile) -> Unit,
        onDataReadUnsuccessful: (Throwable) -> Unit
    ) {

        val uid = AuthService.uid
        if (uid != null) {
            databaseReference.child(uid).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    onDataChange(snapshot.getValue(UserProfile::class.java)!!)
                }

                override fun onCancelled(error: DatabaseError) {
                    onDataReadUnsuccessful(error.toException())
                }

            })
        } else {
            onDataReadUnsuccessful(Throwable(uidErrorMessage))
        }
    }

    override
    fun fetchProfilePhoto(
        saveLocation: File,
        onFetchSuccessful: (File) -> Unit,
        onFetchUnsuccessful: (Throwable) -> Unit
    ) {
        val uid = AuthService.uid

        if (uid != null) {
            val imageStorageReference = storageReference.child(uid)
            imageStorageReference.getFile(saveLocation).addOnSuccessListener {
                onFetchSuccessful(saveLocation)
            }.addOnFailureListener(onFetchUnsuccessful)
        } else {
            onFetchUnsuccessful(Throwable(uidErrorMessage))
        }
    }

    fun doesProfileExist(onExists: (exists: Boolean) -> Unit, onError: (Throwable) -> Unit) {
        val uid = AuthService.uid

        if (uid != null) {
            databaseReference.child(uid).addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    onExists(snapshot.value != null)
                }

                override fun onCancelled(error: DatabaseError) {
                    onError(error.toException())
                }
            })
        }
    }
}