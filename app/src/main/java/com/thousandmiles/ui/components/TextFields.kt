package com.thousandmiles.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

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