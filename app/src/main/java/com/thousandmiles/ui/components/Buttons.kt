package com.thousandmiles.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thousandmiles.ui.theme.ThousandMilesTheme

/**
 * Primary button according to the app theme.
 */
@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            backgroundColor = MaterialTheme.colors.primaryVariant
        ),
        onClick = onClick,
        modifier = modifier.clip(MaterialTheme.shapes.medium)
    ) {
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}

@DevicePreview
@Composable
fun PrimaryButtonPreview() {
    ThousandMilesTheme {
        PrimaryButton(text = "Button",
        modifier = Modifier.padding(horizontal = 16.dp))
    }
}