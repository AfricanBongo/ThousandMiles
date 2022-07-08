package com.thousandmiles.viewmodel.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountServiceImpl: AccountService {
    override fun signIn(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun signUp(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun forgotPassword(
        email: String,
        onResult: (Throwable?) -> Unit
    ) {
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { onResult(it.exception) }
    }
}