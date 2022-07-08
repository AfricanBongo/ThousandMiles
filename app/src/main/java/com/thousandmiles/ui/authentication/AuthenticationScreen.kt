package com.thousandmiles.ui.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.thousandmiles.R
import com.thousandmiles.ui.components.DevicePreview
import com.thousandmiles.ui.theme.ThousandMilesTheme
import com.thousandmiles.ui.theme.darkBlue
import com.thousandmiles.ui.theme.lightBlue
import kotlinx.coroutines.launch

// Tags for constraint layouts.
const val authTag1 = "auth1"
const val authTag2 = "auth2"
const val authTag3 = "auth3"
const val ftLogo = "foot"

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    onAuthenticated: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    // Resources from the res folder
    val footLogo = painterResource(id = R.drawable.foot_logo)
    val footLogoDescription = stringResource(id = R.string.foot_logo_descr)
    val authTagline1 = stringResource(id = R.string.auth_tagline_1)
    val authTagline2 = stringResource(id = R.string.auth_tagline_2)
    val authTagline3 = stringResource(id = R.string.auth_tagline_3)


    Column(modifier = modifier) {
        ConstraintLayout(
            constraintSet = taglineConstraints(),
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
                modifier = Modifier.layoutId(ftLogo)
            )
            Text(
                text = authTagline2,
                color = MaterialTheme.colors.primary,
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
            modifier = Modifier
                .weight(3f)
                .fillMaxSize()
        ) {
            val pagerState = rememberPagerState()
            // Pages for view pager
            val pages: List<String> = listOf(
                // Sign-in page
                stringResource(id = R.string.sign_in),
                // Sign-out page
                stringResource(id = R.string.sign_up)
            )

            // Tabs for Sign In and Sign Up page.
            TabRow(
                backgroundColor = Color.Transparent,
                selectedTabIndex = pagerState.currentPage,
                divider = {},
                modifier = Modifier
                    .weight(1f)
                    .width(240.dp)
                    .padding(start = 16.dp),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        height = 4.dp,
                        modifier = Modifier
                            .pagerTabIndicatorOffset(pagerState, tabPositions)
                            .padding(horizontal = 4.dp)
                            .clip(MaterialTheme.shapes.medium),
                        color = darkBlue
                    )
                }
            ) {
                pages.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = pagerState.currentPage == index,
                        selectedContentColor = darkBlue,
                        unselectedContentColor = MaterialTheme.colors.secondaryVariant,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(if (pagerState.currentPage == 0) 1 else 0)
                            }
                        }
                    )
                }
            }
            
            Spacer(modifier = modifier.weight(0.25f))

            // Pager that switches between the Sign In and Sign Up page.
            HorizontalPager(
                count = pages.size,
                state = pagerState,
                modifier = Modifier.weight(10f)
            ) { page ->
                val pageModifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.large.copy(bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp)))
                    .background(color = lightBlue)
                    .padding(horizontal = 16.dp)
                if (page == 0) {
                    SignInPage(modifier = pageModifier, onSignInSuccess = onAuthenticated)
                } else {
                    SignUpPage(modifier = pageModifier, onSignUpSuccess = onAuthenticated)
                }
            }
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
            bottom.linkTo(authTagline3.top)
        }

        constrain(authTagline3) {
            top.linkTo(footLogo.bottom, 12.dp)
        }
    }
}