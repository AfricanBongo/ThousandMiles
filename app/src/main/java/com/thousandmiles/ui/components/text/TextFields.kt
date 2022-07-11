package com.thousandmiles.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Normal text-field
 *
 * @param isError If true then [onErrorShow] is animated into visibility.
 * @param onErrorShow The composable to be shown when an error occurs, a [TextFieldErrorMessage]
 * can also be used here.
 * @see TextField
 */
@Composable
fun MTextField(
    value: String,
    labelText: String,
    placeholderText: String,
    colors: TextFieldColors,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    onErrorShow: @Composable () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {}
) {
    Column(modifier = modifier) {

        // Show only when there's an error
        AnimatedVisibility(visible = isError, modifier = Modifier.fillMaxWidth()) {
            // Error composable
            onErrorShow()
        }

        Spacer(modifier = if (isError) modifier.height(8.dp) else modifier.height(0.dp))

        // Input field
        TextField(
            singleLine = true,
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            enabled = enabled,
            colors = colors,
            shape = MaterialTheme.shapes.medium,
            textStyle = MaterialTheme.typography.body1,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = trailingIcon,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = labelText)
            },
            placeholder = {
                Text(text = placeholderText, style = MaterialTheme.typography.body1)
            }
        )
    }
}

/**
 * Text-field used to write out passwords.
 *
 * Credit to Valeriy Katkov, for password text field code.
 *
 * [https://stackoverflow.com/a/65665564][StackOverflow URL]
 *
 * @see MTextField
 */
@Composable
fun PasswordTextField(
    password: String,
    labelText: String,
    colors: TextFieldColors,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    onErrorShow: @Composable () -> Unit = {},
    onPasswordChange: (String) -> Unit = {}
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    MTextField(
        value = password,
        labelText = labelText,
        placeholderText = "",
        isError = isError,
        onErrorShow = onErrorShow,
        colors = colors,
        onValueChange = onPasswordChange,
        modifier = modifier,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(imageVector  = image, description)
            }
        }
    )
}

/**
 * Displays an error message in a text-field.
 */
@Composable
fun TextFieldErrorMessage(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = errorMessage,
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.error,
        softWrap = true,
        modifier = modifier
    )
}