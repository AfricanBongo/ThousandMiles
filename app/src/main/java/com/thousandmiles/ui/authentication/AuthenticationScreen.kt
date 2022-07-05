package com.thousandmiles.ui.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.thousandmiles.R
import com.thousandmiles.ui.components.DevicePreview
import com.thousandmiles.ui.theme.ThousandMilesTheme
import com.thousandmiles.ui.theme.lighterBlue

const val authTag1 = "auth1"
const val authTag2 = "auth2"
const val authTag3 = "auth3"
const val ftLogo = "foot"

@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
) {
    val footLogo = painterResource(id = R.drawable.foot_logo)
    val footLogoDescription = stringResource(id = R.string.foot_logo_descr)
    val authTagline1 = stringResource(id = R.string.auth_tagline_1)
    val authTagline2 = stringResource(id = R.string.auth_tagline_2)
    val authTagline3 = stringResource(id = R.string.auth_tagline_3)
    val signInPhrase = stringResource(id = R.string.sign_in_phrase)

    Column {
        ConstraintLayout(
            taglineConstraints(),
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = authTagline1,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.layoutId(authTag1)
            )
            Image(
                painter = footLogo,
                contentDescription = footLogoDescription,
                modifier = Modifier.layoutId(footLogo)
            )
            Text(
                text = authTagline2,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.layoutId(authTag2)
            )
            Text(
                text = authTagline3,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.layoutId(authTag3)
            )
        }

        Column(
            Modifier
                .weight(2f)
                .fillMaxSize()
                .clip(MaterialTheme.shapes.large.copy(bottomEnd = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp)))
                .background(color = lighterBlue)
                .padding(start = 16.dp, top = 48.dp, end = 16.dp)
        ) {
            Text(text = signInPhrase, style = MaterialTheme.typography.h4)
        }
    }
}

private fun taglineConstraints(): ConstraintSet {
    return ConstraintSet {
        val authTagline1 = createRefFor(authTag1)
        val authTagline2 = createRefFor(authTag2)
        val authTagline3 = createRefFor(authTag3)
        val footLogo = createRefFor(ftLogo)

        constrain(authTagline1) {
            top.linkTo(parent.top)
        }

        constrain(footLogo) {
            start.linkTo(parent.start)
            top.linkTo(authTagline1.bottom, 4.dp)
        }

        constrain(authTagline2) {
            start.linkTo(footLogo.end, 12.dp)
        }

        constrain(authTagline3) {
            top.linkTo(footLogo.bottom, 12.dp)
        }
    }
}

@DevicePreview
@Composable
fun AuthenticationScreenPreview() {
    ThousandMilesTheme {
        AuthenticationScreen(modifier = Modifier.fillMaxSize())
    }
}