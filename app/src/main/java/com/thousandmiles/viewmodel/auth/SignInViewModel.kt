package com.thousandmiles.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignInViewModel: ViewModel() {

    private val accountService: AccountService = AccountServiceImpl()

    var signInState by mutableStateOf(SignInUiState())
        private set

    var signInError by mutableStateOf(false)
        private set

    var signInSuccess by mutableStateOf(false)
        private set

    var validEmailFormatError by mutableStateOf(false)

    fun onEmailChange(newValue: String) {
        signInState = signInState.copy(email = newValue)
        validEmailFormatError = if (signInState.email.isNotBlank()) {
            !signInState.isCorrectEmailFormat()
        } else false
    }

    fun onPasswordChange(newValue: String) {
        signInState = signInState.copy(password = newValue)
    }

    fun signIn() {
        if (!validEmailFormatError) {
            accountService.signIn(signInState.email, signInState.password) { error ->
                if (error == null) onSignInSuccess() else onSignInError()
            }
        }
    }

    private fun onSignInError() {
        signInError = true
        signInSuccess = false
    }

    fun acknowledgeSignInError() {
        signInError = false
    }

    private fun onSignInSuccess() {
        signInSuccess = true
        signInError = false
    }
}

data class SignInUiState(
    val email: String = "",
    val password: String = ""
)

/**
 * Validate that the email provided has the correct email format, e.g. 'email@yahoo.com'
 */
private fun SignInUiState.isCorrectEmailFormat(): Boolean {
    // Credit to [https://regexr.com/3e48o] for Reg-Ex.
    return email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))
}