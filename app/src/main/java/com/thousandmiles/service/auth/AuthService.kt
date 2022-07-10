package com.thousandmiles.service.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AuthService: SignInService, SignUpService {
    private val auth: FirebaseAuth by lazy {Firebase.auth}

    val uid: String?
        get() {
            return if (isSignedIn())
                auth.currentUser?.uid
            else
                null
        }

    /**
     * Check whether or not the current user is signed in.
     */
    fun isSignedIn(): Boolean {
        return auth.currentUser != null
    }

    override fun signIn(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun signUp(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun forgotPassword(
        email: String,
        onResult: (Throwable?) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { onResult(it.exception) }
    }
}