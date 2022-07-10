package com.thousandmiles.viewmodel.auth.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.thousandmiles.service.auth.AuthService
import com.thousandmiles.service.auth.SignInService
import com.thousandmiles.ui.auth.AuthenticationState

class SignInViewModel: ViewModel() {

    private val signInService: SignInService = AuthService

    var signInInfo by mutableStateOf(SignInInfo())
        private set

    var authenticationState: AuthenticationState by mutableStateOf(AuthenticationState.UserInput)
        private set

    var validEmailFormatError by mutableStateOf(false)
        private set

    var blankEmailError by mutableStateOf(false)
        private set

    var blankPasswordError by mutableStateOf(false)
        private set

    fun onEmailChange(newValue: String) {
        signInInfo = signInInfo.copy(email = newValue)
        blankEmailError = false
        validEmailFormatError = if (signInInfo.email.isNotBlank()) {
            !signInInfo.isCorrectEmailFormat()
        } else false
    }

    fun onPasswordChange(newValue: String) {
        signInInfo = signInInfo.copy(password = newValue)
        blankPasswordError = false
    }

    fun signIn() {
        blankEmailError = signInInfo.email.isBlank()
        blankPasswordError = signInInfo.password.isBlank()

        if (!blankEmailError && !blankPasswordError) {
            if (!validEmailFormatError) {
                onSigningIn()
                signInService.signIn(signInInfo.email, signInInfo.password) { error ->
                    if (error == null) onSignInSuccess() else onSignInError(error.message ?: "Error unknown")
                }
            }
        }
    }

    private fun onSignInError(errorMessage: String) {
        authenticationState = AuthenticationState.AuthenticationError(errorMessage)
    }

    private fun onSigningIn() {
        authenticationState = AuthenticationState.Authenticating
    }
    fun acknowledgeSignInError() {
        authenticationState = AuthenticationState.UserInput
    }

    private fun onSignInSuccess() {
        authenticationState = AuthenticationState.AuthenticationSuccess
    }
}