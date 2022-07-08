package com.thousandmiles.ui.authentication

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

/**
 * A text-field used to capture data needed for authentication.
 * @param isPassword If the type of text that needs to be captured is a password or not.
 *
 * If it is true then:
 * - [onValueChange] becomes onPasswordChange
 * - [value] becomes password
 */
@Composable
fun AuthenticationTextField(
    value: String,
    labelText: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isError: Boolean = false,
    onErrorShow: @Composable () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit = {}
) {

    // Colors used to style the text fields.
    val colors = TextFieldDefaults.textFieldColors(
        textColor = darkBlue,
        backgroundColor = MaterialTheme.colors.secondary,
        cursorColor = darkerYellow,
        focusedLabelColor = MaterialTheme.colors.secondaryVariant,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )

    // If the text-field needed is to be a password related one then use PasswordTextField.
    // Else use the normal type of text field.
    if (isPassword)
        PasswordTextField(
            password = value,
            labelText = labelText,
            colors = colors,
            isError = isError,
            onErrorShow = onErrorShow,
            onPasswordChange = onValueChange,
            modifier = modifier
        )
    else
        MTextField(
            value = value,
            labelText = labelText,
            placeholderText = "",
            colors = colors,
            onValueChange = onValueChange,
            isError = isError,
            onErrorShow = onErrorShow,
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
        AuthenticationTextField(
            value = "",
            labelText = "Text",
            modifier = Modifier.height(80.dp)
        )
    }
}