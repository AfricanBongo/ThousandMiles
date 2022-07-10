package com.thousandmiles.ui.auth

/**
 * States during the user authentication stage.
 */
sealed class AuthenticationState {

    object NotAuthenticated: AuthenticationState()

    /**
     * User is still inputting information into the fields.
     */
    object UserInput: AuthenticationState()

    /**
     * The user's information is now being authenticated.
     */
    object Authenticating: AuthenticationState()

    /**
     * The authentication process was completed successfully.
     */
    object AuthenticationSuccess : AuthenticationState()

    /**
     * An error occurred during authentication
     * @param errorMessage Error message returned from the authentication system.
     */
    class AuthenticationError(val errorMessage: String): AuthenticationState()
}