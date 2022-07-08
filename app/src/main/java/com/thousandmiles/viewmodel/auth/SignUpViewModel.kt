package com.thousandmiles.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignUpViewModel: ViewModel() {
    private val accountService: AccountService = AccountServiceImpl()

    var signUpState by mutableStateOf(SignUpUiState())

    var signUpError by mutableStateOf(false)
        private set

    var signUpSuccess by mutableStateOf(false)
        private set

    // Input field errors
    var passwordMatchingError by mutableStateOf(false)
    var validEmailFormatError by mutableStateOf(false)

    fun onEmailChange(newValue: String) {
        signUpState = signUpState.copy(email = newValue)
        validEmailFormatError = if (signUpState.email.isNotBlank()) {
            !signUpState.isCorrectEmailFormat()
        } else false
    }

    fun onPasswordChange(newValue: String) {
        signUpState = signUpState.copy(password = newValue)
    }

    fun onConfirmPasswordChange(newValue: String) {
        signUpState = signUpState.copy(confirmPassword = newValue)
        passwordMatchingError =
            if (signUpState.password.isNotBlank() && signUpState.confirmPassword.isNotBlank()) {
                !signUpState.passwordsMatch()
            } else false
    }

    fun signUp() {
        if (!passwordMatchingError && !validEmailFormatError) {
            accountService.signUp(signUpState.email, signUpState.password) { error ->
                if (error == null) onSignInSuccess() else onSignInError()
            }
        }
    }

    private fun onSignInError() {
        signUpSuccess = false
        signUpError = true
    }

    fun acknowledgeSignInError() {
        signUpError = false
    }

    private fun onSignInSuccess() {
        signUpError = false
        signUpSuccess = true
    }
}

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

/**
 * Whether the passwords in this state are the same.
 */
private fun SignUpUiState.passwordsMatch(): Boolean {
    return password.compareTo(confirmPassword) == 0
}

/**
 * Validate that the email provided has the correct email format, e.g. 'email@yahoo.com'
 */
private fun SignUpUiState.isCorrectEmailFormat(): Boolean {
    // Credit to [https://regexr.com/3e48o] for Reg-Ex.
    return email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))
}