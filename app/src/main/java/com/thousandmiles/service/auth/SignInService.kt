package com.thousandmiles.service.auth

interface SignInService {
    fun signIn(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun forgotPassword(email: String, onResult: (Throwable?) -> Unit)
}