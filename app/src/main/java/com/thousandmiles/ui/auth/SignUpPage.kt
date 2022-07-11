package com.thousandmiles.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thousandmiles.R
import com.thousandmiles.ui.components.AppErrorDialog
import com.thousandmiles.ui.components.LoadingDialog
import com.thousandmiles.ui.components.PrimaryButton
import com.thousandmiles.ui.components.TextFieldErrorMessage
import com.thousandmiles.viewmodel.auth.signup.SignUpViewModel

private const val emailF = "email"
private const val passF = "password"
private const val confirmPassF = "confirm"
private const val signB = "sign_btn"
private const val signP = "sign_phrase"

@Composable
fun SignUpPage(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = viewModel(),
    onSignUpSuccess: () -> Unit,
) {
    // Resources from res folder
    val signUpPhrase = stringResource(id = R.string.sign_up_phrase)
    val emailLabel = stringResource(id = R.string.email)
    val passwordLabel = stringResource(id = R.string.password)
    val confirmPasswordLabel = stringResource(id = R.string.confirm_password)
    val signUpButtonLabel = stringResource(id = R.string.sign_up)

    // State that contains all values for the fields.
    val signUpInfo = viewModel.signUpInfo

    val authState = viewModel.authenticationState

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
        TextFieldWithErrorOption(
            value = signUpInfo.email,
            labelText = emailLabel,
            onValueChange = viewModel::onEmailChange,
            isError = viewModel.validEmailFormatError || viewModel.blankEmailError,
            onError = {
                val errorMessage: String = if (viewModel.validEmailFormatError) {
                    stringResource(id = R.string.invalid_email)
                } else {
                    stringResource(id = R.string.blank_email)
                }
                TextFieldErrorMessage(errorMessage = errorMessage)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(emailF)

        )

        // Password
        TextFieldWithErrorOption(
            value = signUpInfo.password,
            labelText = passwordLabel,
            isPassword = true,
            onValueChange = viewModel::onPasswordChange,
            isError = viewModel.passwordTooShortError || viewModel.blankPasswordError,
            onError = {
                val errorMessage = if (viewModel.passwordTooShortError) {
                    stringResource(id = R.string.password_too_short)
                } else {
                    stringResource(id = R.string.blank_password)
                }
                TextFieldErrorMessage(errorMessage = errorMessage)
            },
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(passF)

        )

        // Confirm password
        TextFieldWithErrorOption(
            value = signUpInfo.confirmPassword,
            labelText = confirmPasswordLabel,
            isPassword = true,
            onValueChange = viewModel::onConfirmPasswordChange,
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(confirmPassF),
            isError = viewModel.passwordMatchingError || viewModel.blankConfirmPasswordError,
            onError = {
                val errorMessage: String =
                    if (viewModel.passwordMatchingError) {
                        stringResource(id = R.string.passwords_dont_match)
                    } else {
                        stringResource(id = R.string.blank_confirm_password)
                    }
                TextFieldErrorMessage(errorMessage = errorMessage)
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


    when (authState) {
        is AuthenticationState.AuthenticationSuccess -> onSignUpSuccess()
        is AuthenticationState.UserInput -> { /* Do Nothing*/ }
        is AuthenticationState.AuthenticationError -> {
            AppErrorDialog(
                errorTitle = stringResource(id = R.string.childish_error_title),
                errorMessage = authState.errorMessage,
                onDismissRequest = viewModel::acknowledgeSignInError,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = MaterialTheme.colors.background)
                    .padding(top = 32.dp)
            )
        }
        else -> {
            LoadingDialog(
                loadingText = stringResource(id = R.string.signing_up),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = MaterialTheme.colors.background)
                    .padding(vertical = 32.dp, horizontal = 16.dp)
            )
        }
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