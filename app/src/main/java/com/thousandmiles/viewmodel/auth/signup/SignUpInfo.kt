package com.thousandmiles.viewmodel.auth.signup

data class SignUpInfo(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

/**
 * Whether the passwords in this state are the same.
 */
fun SignUpInfo.passwordsMatch(): Boolean {
    return password.compareTo(confirmPassword) == 0
}

/**
 * Verify that the password isn't too short.
 */
fun SignUpInfo.isPasswordTooShort(): Boolean {
    return password.length < 6
}

/**
 * Verify that the email provided has the correct email format, e.g. 'email@yahoo.com'
 */
fun SignUpInfo.isCorrectEmailFormat(): Boolean {
    // Credit to [https://regexr.com/3e48o] for Reg-Ex.
    return email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))
}