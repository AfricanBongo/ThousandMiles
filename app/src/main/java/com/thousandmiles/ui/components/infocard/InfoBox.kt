package com.thousandmiles.ui.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InfoBox(
    title: String,
    body: String,
    titleColor: Color,
    bodyColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier
        .background(color = backgroundColor)
        .then(modifier)) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = titleColor
        )
        
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = body,
            style = MaterialTheme.typography.body1,
            color = bodyColor
        )
    }
}

