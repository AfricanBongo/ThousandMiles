package com.thousandmiles.viewmodel.auth.signin

data class SignInInfo(
    val email: String = "",
    val password: String = ""
)

/**
 * Validate that the email provided has the correct email format, e.g. 'email@yahoo.com'
 */
fun SignInInfo.isCorrectEmailFormat(): Boolean {
    // Credit to [https://regexr.com/3e48o] for Reg-Ex.
    return email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))
}