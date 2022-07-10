package com.thousandmiles.service.auth

interface SignUpService {
    fun signUp(email: String, password: String, onResult: (Throwable?) -> Unit)
}