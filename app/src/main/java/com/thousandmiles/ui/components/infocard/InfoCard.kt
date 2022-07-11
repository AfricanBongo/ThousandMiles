package com.thousandmiles.ui.components.infocard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Card that fills max width by default
 */
@Composable
fun InfoCard(
    cardTitle: String,
    cardSubtitle: String,
    childrenTitles: Pair<String, String>,
    childrenBodies: Pair<String, String>,
    colors: InfoCardColors,
    modifier: Modifier = Modifier,
    customTitleBox: @Composable ((colors: InfoCardColors) -> Unit)? = null
) {
    Column(modifier) {

        if (customTitleBox == null) {
            Column(Modifier.fillMaxWidth().background(color = Color.Transparent)) {
                Text(
                    text = cardTitle,
                    style = MaterialTheme.typography.h6,
                    color = colors.cardTitleColor
                )
                
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = cardSubtitle,
                    style = MaterialTheme.typography.h4,
                    color = colors.cardSubtitleColor
                )
            }
        } else customTitleBox(colors)


        Spacer(modifier = Modifier.height(16.dp))

        BoxWithConstraints(Modifier.fillMaxWidth().background(Color.Transparent)) {
            val boxWidth = (maxWidth / 2) - 4.dp
            val spacerWidth = 8.dp


            Row {
                InfoBox(
                    title = childrenTitles.first,
                    body = childrenBodies.first,
                    titleColor = colors.childrenTitleColor,
                    bodyColor = colors.childrenBodyColor,
                    backgroundColor = colors.childrenBackgroundColor,
                    boxWidth = boxWidth
                )

                Spacer(modifier = Modifier.width(spacerWidth))

                InfoBox(
                    title = childrenTitles.second,
                    body = childrenBodies.second,
                    titleColor = colors.childrenTitleColor,
                    bodyColor = colors.childrenBodyColor,
                    backgroundColor = colors.childrenBackgroundColor,
                    boxWidth = boxWidth
                )
            }
        }
    }
}



