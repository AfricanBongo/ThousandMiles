package com.thousandmiles.ui.authentication

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.thousandmiles.R
import com.thousandmiles.ui.components.PrimaryButton

private const val userF = "username"
private const val passF = "password"
private const val signB = "sign_btn"
private const val signP = "sign_phrase"
private const val forgot = "forgot"

@Composable
fun SignInPage(modifier: Modifier = Modifier) {
    // Resources from res folder
    val signInPhrase = stringResource(id = R.string.sign_in_phrase)
    val usernameLabel = stringResource(id = R.string.username)
    val passwordLabel = stringResource(id = R.string.password)
    val forgotPasswordLabel = stringResource(id = R.string.forgot_pass)
    val signInButtonLabel = stringResource(id = R.string.sign_in)

    var text by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    ConstraintLayout(
        constraintSet = authBoxConstraints(),
        modifier = modifier
    ) {
        Text(
            text = signInPhrase,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.layoutId(signP)
        )

        // Username
        AuthenticationTextField(
            value = text,
            labelText = usernameLabel,
            onValueChange = { text = it},
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
                .height(52.dp)
        )
    }
}

private fun authBoxConstraints(): ConstraintSet {
    return ConstraintSet {
        val signPhrase = createRefFor(signP)
        val signButton = createRefFor(signB)
        val usernameField = createRefFor(userF)
        val passwordField = createRefFor(passF)
        val forgotText = createRefFor(forgot)

        constrain(signPhrase) {
            top.linkTo(parent.top, 48.dp)
            start.linkTo(parent.start)
        }

        constrain(usernameField) {
            top.linkTo(signPhrase.bottom, 32.dp)
            start.linkTo(parent.start)
        }

        constrain(passwordField) {
            top.linkTo(usernameField.bottom, 12.dp)
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