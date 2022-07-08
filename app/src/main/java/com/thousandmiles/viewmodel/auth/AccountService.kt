package com.thousandmiles.viewmodel.auth

interface AccountService {
    fun signIn(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun signUp(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun forgotPassword(email: String, onResult: (Throwable?) -> Unit)
}