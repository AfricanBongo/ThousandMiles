package com.thousandmiles.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.thousandmiles.R

val signikaFontFamily = FontFamily(
    Font(R.font.signika_regular, weight = FontWeight.Normal),
    Font(R.font.signika_bold, weight = FontWeight.Bold),
    Font(R.font.signika_semibold, weight = FontWeight.SemiBold)
)
val latoFontFamily = FontFamily(
    Font(R.font.lato_regular, weight = FontWeight.Normal),
    Font(R.font.lao_bold, weight = FontWeight.Medium)
)

// Set of typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = signikaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 48.sp
    ),
    h2 = TextStyle(
        fontFamily = signikaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 40.sp
    ),
    h3 = TextStyle(
        fontFamily = signikaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp
    ),
    h4 = TextStyle(
        fontFamily = signikaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    h5 = TextStyle(
        fontFamily = signikaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = signikaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    body1 = TextStyle(
        fontFamily = latoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = latoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = latoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)