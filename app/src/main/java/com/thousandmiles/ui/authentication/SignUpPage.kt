package com.thousandmiles.ui.authentication

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.thousandmiles.R
import com.thousandmiles.ui.components.PrimaryButton

private const val emailF = "email"
private const val userF = "username"
private const val passF = "password"
private const val confirmPassF = "confirm"
private const val signB = "sign_btn"
private const val signP = "sign_phrase"

@Composable
fun SignUpPage(modifier: Modifier = Modifier) {
    // Resources from res folder
    val signUpPhrase = stringResource(id = R.string.sign_up_phrase)
    val emailLabel = stringResource(id = R.string.email)
    val usernameLabel = stringResource(id = R.string.username)
    val passwordLabel = stringResource(id = R.string.password)
    val confirmPasswordLabel = stringResource(id = R.string.confirm_password)
    val signInButtonLabel = stringResource(id = R.string.sign_up)

    var emailText by remember { mutableStateOf("") }
    var usernameText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var confirmPasswordText by remember { mutableStateOf("") }

    ConstraintLayout(
        constraintSet = signUpPageConstraints(),
        modifier = modifier
    ) {
        Text(
            text = signUpPhrase,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.layoutId(signP)
        )

        // Email
        AuthenticationTextField(
            value = emailText,
            labelText = emailLabel,
            onValueChange = { emailText = it},
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(emailF)

        )

        // Username
        AuthenticationTextField(
            value = usernameText,
            labelText = usernameLabel,
            onValueChange = { usernameText = it},
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(userF)

        )

        // Password
        AuthenticationTextField(
            value = passwordText,
            labelText = passwordLabel,
            isPassword = true,
            onValueChange = { passwordText = it},
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(passF)

        )

        // Confirm password
        AuthenticationTextField(
            value = confirmPasswordText,
            labelText = confirmPasswordLabel,
            isPassword = true,
            onValueChange = { confirmPasswordText = it},
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(confirmPassF)

        )

        // Sign up button
        PrimaryButton(
            text = signInButtonLabel,
            modifier = Modifier
                .layoutId(signB)
                .fillMaxWidth()
                .height(52.dp)
        )
    }
}

private fun signUpPageConstraints(): ConstraintSet {
    return ConstraintSet {
        val signPhrase = createRefFor(signP)
        val signButton = createRefFor(signB)
        val emailField = createRefFor(emailF)
        val usernameField = createRefFor(userF)
        val passwordField = createRefFor(passF)
        val confirmPasswordField = createRefFor(confirmPassF)

        constrain(signPhrase) {
            top.linkTo(parent.top, 48.dp)
            start.linkTo(parent.start)
        }

        constrain(emailField) {
            top.linkTo(signPhrase.bottom, 32.dp)
            start.linkTo(parent.start)
        }

        constrain(usernameField) {
            top.linkTo(emailField.bottom, 12.dp)
            start.linkTo(parent.start)
        }

        constrain(passwordField) {
            top.linkTo(usernameField.bottom, 12.dp)
            start.linkTo(parent.start)
        }

        constrain(confirmPasswordField) {
            top.linkTo(passwordField.bottom, 12.dp)
            start.linkTo(parent.start)
        }

        constrain(signButton) {
            top.linkTo(confirmPasswordField.bottom, 52.dp)
        }
    }
}