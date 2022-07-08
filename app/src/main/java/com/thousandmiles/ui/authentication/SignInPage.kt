package com.thousandmiles.ui.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.thousandmiles.R
import com.thousandmiles.ui.components.PrimaryButton
import com.thousandmiles.viewmodel.auth.SignInViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thousandmiles.ui.components.AppErrorDialog
import com.thousandmiles.ui.components.LoadingDialog
import com.thousandmiles.ui.components.TextFieldErrorMessage
import com.thousandmiles.ui.theme.darkBlue

private const val emailF = "username"
private const val passF = "password"
private const val signB = "sign_btn"
private const val signP = "sign_phrase"
private const val forgot = "forgot"

@Composable
fun SignInPage(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = viewModel(),
    onSignInSuccess: () -> Unit,
) {
    // Resources from res folder
    val signInPhrase = stringResource(id = R.string.sign_in_phrase)
    val emailLabel = stringResource(id = R.string.email)
    val passwordLabel = stringResource(id = R.string.password)
    val forgotPasswordLabel = stringResource(id = R.string.forgot_pass)
    val signInButtonLabel = stringResource(id = R.string.sign_in)

    val signInInfo = viewModel.signInInfo
    val authState = viewModel.authenticationState

    ConstraintLayout(
        constraintSet = authBoxConstraints(),
        modifier = modifier
    ) {
        Text(
            text = signInPhrase,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.layoutId(signP)
        )

        // Email
        AuthenticationTextField(
            value = signInInfo.email,
            labelText = emailLabel,
            onValueChange = viewModel::onEmailChange,
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(emailF),
            isError = viewModel.validEmailFormatError || viewModel.blankEmailError,
            onErrorShow = {
                val errorMessage: String = if (viewModel.validEmailFormatError) {
                    stringResource(id = R.string.invalid_email)
                } else {
                    stringResource(id = R.string.blank_email)
                }
                TextFieldErrorMessage(errorMessage = errorMessage)
            }
        )

        // Password
        AuthenticationTextField(
            value = signInInfo.password,
            labelText = passwordLabel,
            isPassword = true,
            onValueChange = viewModel::onPasswordChange,
            isError = viewModel.blankPasswordError,
            onErrorShow = {
                TextFieldErrorMessage(
                    errorMessage = stringResource(id = R.string.blank_password)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(passF)
        )

        // Forgot your password
        ClickableText(
            text = AnnotatedString(forgotPasswordLabel),
            onClick = {},
            style = MaterialTheme.typography.caption.copy(
                color = MaterialTheme.colors.secondaryVariant,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier.layoutId(forgot)
        )

        // Sign in button
        PrimaryButton(
            text = signInButtonLabel,
            modifier = Modifier
                .layoutId(signB)
                .fillMaxWidth()
                .height(52.dp),
            onClick = viewModel::signIn
        )
    }

    when (authState) {
        is AuthenticationState.AuthenticationSuccess -> onSignInSuccess()
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
                loadingText = stringResource(id = R.string.signing_in),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = MaterialTheme.colors.background)
                    .padding(vertical = 32.dp, horizontal = 16.dp)
            )
        }
    }
}

private fun authBoxConstraints(): ConstraintSet {
    return ConstraintSet {
        val signPhrase = createRefFor(signP)
        val signButton = createRefFor(signB)
        val emailField = createRefFor(emailF)
        val passwordField = createRefFor(passF)
        val forgotText = createRefFor(forgot)

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

        constrain(forgotText) {
            top.linkTo(passwordField.bottom, 12.dp)
            end.linkTo(parent.end)
        }

        constrain(signButton) {
            top.linkTo(forgotText.bottom, 52.dp)
        }
    }
}