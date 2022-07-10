package com.thousandmiles.viewmodel.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.thousandmiles.service.auth.AuthService
import com.thousandmiles.service.auth.SignUpService
import com.thousandmiles.ui.auth.AuthenticationState

class SignUpViewModel: ViewModel() {

    private val signUpService: SignUpService = AuthService

    var signUpInfo by mutableStateOf(SignUpInfo())
        private set

    var authenticationState: AuthenticationState by mutableStateOf(AuthenticationState.UserInput)
        private set

    // Input field errors
    var passwordMatchingError by mutableStateOf(false)
        private set
    var passwordTooShortError by mutableStateOf(false)
        private set
    var validEmailFormatError by mutableStateOf(false)
        private set
    var blankEmailError by mutableStateOf(false)
        private set
    var blankPasswordError by mutableStateOf(false)
        private set
    var blankConfirmPasswordError by mutableStateOf(false)
        private set

    fun onEmailChange(newValue: String) {
        signUpInfo = signUpInfo.copy(email = newValue)
        blankEmailError = false
        validEmailFormatError = if (signUpInfo.email.isNotBlank()) {
            !signUpInfo.isCorrectEmailFormat()
        } else false
    }

    fun onPasswordChange(newValue: String) {
        signUpInfo = signUpInfo.copy(password = newValue)
        blankPasswordError = false
        passwordTooShortError = if (signUpInfo.password.isNotBlank()) {
            signUpInfo.isPasswordTooShort()
        } else false
    }

    fun onConfirmPasswordChange(newValue: String) {
        signUpInfo = signUpInfo.copy(confirmPassword = newValue)
        blankConfirmPasswordError = false
        passwordMatchingError =
            if (signUpInfo.password.isNotBlank() && signUpInfo.confirmPassword.isNotBlank()) {
                !signUpInfo.passwordsMatch()
            } else false
    }

    fun signUp() {
        blankEmailError = signUpInfo.email.isBlank()
        blankPasswordError = signUpInfo.password.isBlank()
        blankConfirmPasswordError = signUpInfo.confirmPassword.isBlank()

        if (!blankEmailError && !blankPasswordError && !blankConfirmPasswordError) {
            if (!passwordMatchingError && !validEmailFormatError && !passwordTooShortError) {
                onSigningUp()

                signUpService.signUp(signUpInfo.email, signUpInfo.password) { error ->
                    if (error == null) {
                        onSignUpSuccess()
                    } else {
                        onSignUpError(error.message ?: "Error unknown")
                    }
                }
            }
        }
    }

    private fun onSignUpError(errorMessage: String) {
        authenticationState = AuthenticationState.AuthenticationError(errorMessage)
    }

    fun acknowledgeSignInError() {
        authenticationState = AuthenticationState.UserInput
    }

    private fun onSigningUp() {
        authenticationState = AuthenticationState.Authenticating
    }

    private fun onSignUpSuccess() {
        authenticationState = AuthenticationState.AuthenticationSuccess
    }
}