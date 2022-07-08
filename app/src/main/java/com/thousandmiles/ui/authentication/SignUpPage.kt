package com.thousandmiles.ui.authentication

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thousandmiles.R
import com.thousandmiles.ui.components.PrimaryButton
import com.thousandmiles.ui.components.TextFieldErrorMessage
import com.thousandmiles.viewmodel.auth.SignUpViewModel

private const val emailF = "email"
private const val passF = "password"
private const val confirmPassF = "confirm"
private const val signB = "sign_btn"
private const val signP = "sign_phrase"

@Composable
fun SignUpPage(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = viewModel(),
) {
    // Resources from res folder
    val signUpPhrase = stringResource(id = R.string.sign_up_phrase)
    val emailLabel = stringResource(id = R.string.email)
    val passwordLabel = stringResource(id = R.string.password)
    val confirmPasswordLabel = stringResource(id = R.string.confirm_password)
    val signUpButtonLabel = stringResource(id = R.string.sign_up)
    val passwordsDontMatchLabel = stringResource(id = R.string.passwords_dont_match)
    val invalidEmailFormatLabel = stringResource(id = R.string.invalid_email)

    // State that contains all values for the fields.
    val signUpState = viewModel.signUpState

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
            value = signUpState.email,
            labelText = emailLabel,
            onValueChange = viewModel::onEmailChange,
            isError = viewModel.validEmailFormatError,
            onErrorShow = {
                TextFieldErrorMessage(errorMessage = invalidEmailFormatLabel)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(emailF)

        )

        // Password
        AuthenticationTextField(
            value = signUpState.password,
            labelText = passwordLabel,
            isPassword = true,
            onValueChange = viewModel::onPasswordChange,
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(passF)

        )

        // Confirm password
        AuthenticationTextField(
            value = signUpState.confirmPassword,
            labelText = confirmPasswordLabel,
            isPassword = true,
            onValueChange = viewModel::onConfirmPasswordChange,
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(confirmPassF),
            isError = viewModel.passwordMatchingError,
            onErrorShow = {
                TextFieldErrorMessage(errorMessage = passwordsDontMatchLabel)
            }
        )

        // Sign up button
        PrimaryButton(
            text = signUpButtonLabel,
            modifier = Modifier
                .layoutId(signB)
                .fillMaxWidth()
                .height(52.dp),
            onClick = viewModel::signUp
        )
    }
}

private fun signUpPageConstraints(): ConstraintSet {
    return ConstraintSet {
        val signPhrase = createRefFor(signP)
        val signButton = createRefFor(signB)
        val emailField = createRefFor(emailF)
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

        constrain(passwordField) {
            top.linkTo(emailField.bottom, 12.dp)
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