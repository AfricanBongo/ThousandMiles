package com.thousandmiles.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.thousandmiles.R
import com.thousandmiles.ui.theme.darkBlue
import com.thousandmiles.ui.theme.lightBlue

@Composable
fun LoadingDialog(
    loadingText: String,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        ) {
            Text(
                text = loadingText,
                style = MaterialTheme.typography.body1.copy(
                    color = darkBlue
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            CircularProgressIndicator(
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier.requiredSize(48.dp)
            )
        }
    }
}

@Composable
fun AppErrorDialog(
    errorTitle: String,
    errorMessage: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )) {
        Column(
            modifier = modifier,
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {

                // Error icon
                Image(
                    painter = painterResource(id = R.drawable.ic_error),
                    contentDescription = stringResource(id = R.string.error_descr),
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Title
                Text(
                    text = errorTitle,
                    style = MaterialTheme.typography.h6.copy(
                        color = darkBlue,
                        fontWeight = FontWeight.Normal
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Text
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.secondaryVariant
                    )
                )
            }


            Spacer(modifier = Modifier.height(32.dp))

            // Close button
            TextButton(
                onClick = onDismissRequest,
                shape = RectangleShape,
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = lightBlue,
                    contentColor = MaterialTheme.colors.secondaryVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.close),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}