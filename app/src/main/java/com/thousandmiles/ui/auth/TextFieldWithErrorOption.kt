package com.thousandmiles.ui.auth

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thousandmiles.ui.components.MTextField
import com.thousandmiles.ui.components.PasswordTextField
import com.thousandmiles.ui.theme.ThousandMilesTheme
import com.thousandmiles.ui.theme.darkBlue
import com.thousandmiles.ui.theme.darkerYellow
import com.thousandmiles.ui.theme.lighterBlue

/**
 * A text-field used to capture data needed for authentication.
 * @param isPassword If the type of text that needs to be captured is a password or not.
 *
 * If it is true then:
 * - [onValueChange] becomes onPasswordChange
 * - [value] becomes password
 */
@Composable
fun TextFieldWithErrorOption(
    value: String,
    labelText: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    enabled: Boolean = true,
    isError: Boolean = false,
    onError: @Composable () -> Unit = {},
    lighterBackground: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit = {},
) {

    // Colors used to style the text fields.
    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor = darkBlue,
        backgroundColor = if (lighterBackground) lighterBlue else MaterialTheme.colors.secondary,
        cursorColor = darkerYellow,
        focusedLabelColor = MaterialTheme.colors.secondaryVariant,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        disabledTextColor = darkBlue,
        disabledLabelColor = MaterialTheme.colors.secondaryVariant,
        unfocusedLabelColor = MaterialTheme.colors.secondaryVariant,
    )

    // If the text-field needed is to be a password related one then use PasswordTextField.
    // Else use the normal type of text field.
    if (isPassword)
        PasswordTextField(
            password = value,
            labelText = labelText,
            colors = textFieldColors,
            isError = isError,
            onErrorShow = onError,
            onPasswordChange = onValueChange,
            modifier = modifier
        )
    else
        MTextField(
            value = value,
            labelText = labelText,
            placeholderText = "",
            colors = textFieldColors,
            onValueChange = onValueChange,
            enabled = enabled,
            isError = isError,
            onErrorShow = onError,
            trailingIcon =  trailingIcon,
            visualTransformation = visualTransformation,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            modifier = modifier,
        )
}

@Preview
@Composable
fun AuthenticationTextFieldPreview() {
    ThousandMilesTheme {
        TextFieldWithErrorOption(
            value = "",
            labelText = "Text",
            modifier = Modifier.height(80.dp)
        )
    }
}