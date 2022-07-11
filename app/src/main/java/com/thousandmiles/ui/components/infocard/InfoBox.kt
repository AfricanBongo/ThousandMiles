package com.thousandmiles.ui.components.infocard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun InfoBox(
    title: String,
    body: String,
    titleColor: Color,
    bodyColor: Color,
    backgroundColor: Color,
    boxWidth: Dp,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
        .width(boxWidth)
        .clip(MaterialTheme.shapes.medium)
        .background(color = backgroundColor)
        .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
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

