package com.thousandmiles.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.thousandmiles.ui.authentication.AuthenticationState

class SignUpViewModel: ViewModel() {
    private val accountService: AccountService = AccountServiceImpl()

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

                accountService.signUp(signUpInfo.email, signUpInfo.password) { error ->
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

data class SignUpInfo(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

/**
 * Whether the passwords in this state are the same.
 */
private fun SignUpInfo.passwordsMatch(): Boolean {
    return password.compareTo(confirmPassword) == 0
}

/**
 * Verify that the password isn't too short.
 */
private fun SignUpInfo.isPasswordTooShort(): Boolean {
    return password.length < 6
}

/**
 * Verify that the email provided has the correct email format, e.g. 'email@yahoo.com'
 */
private fun SignUpInfo.isCorrectEmailFormat(): Boolean {
    // Credit to [https://regexr.com/3e48o] for Reg-Ex.
    return email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))
}