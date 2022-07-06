package com.thousandmiles.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Normal text-field
 */
@Composable
fun MTextField(
    value: String,
    labelText: String,
    placeholderText: String,
    colors: TextFieldColors,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    TextField(
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        colors = colors,
        modifier = modifier.clip(MaterialTheme.shapes.medium),
        textStyle = MaterialTheme.typography.body1,
        label = {
            Text(text = labelText)
        },
        placeholder = {
            Text(text = placeholderText, style = MaterialTheme.typography.body1)
        }
    )
}

/**
 * Text-field used to write out passwords.
 *
 * Credit to Valeriy Katkov, for password text field code.
 *
 * [https://stackoverflow.com/a/65665564][StackOverflow URL]
 */
@Composable
fun PasswordTextField(
    password: String,
    labelText: String,
    colors: TextFieldColors,
    modifier: Modifier = Modifier,
    onPasswordChange: (String) -> Unit = {}
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        colors = colors,
        modifier = modifier.clip(MaterialTheme.shapes.medium),
        textStyle = MaterialTheme.typography.body1,
        label = { Text(labelText) },
        singleLine = true,
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