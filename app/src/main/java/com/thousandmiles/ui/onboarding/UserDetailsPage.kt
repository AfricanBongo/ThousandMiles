package com.thousandmiles.viewmodel.onboarding

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.thousandmiles.R
import com.thousandmiles.ui.components.DevicePreview
import com.thousandmiles.ui.theme.ThousandMilesTheme
import com.thousandmiles.ui.theme.darkBlue

@Composable
fun UserDetailsPage(
    viewModel: OnboardingViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit
) {
    val title = stringResource(id = R.string.personal_details_title)
    val subtitle = stringResource(id = R.string.personal_details_subtitle)
    val firstNameLabel = stringResource(id = R.string.firstName)
    val lastNameLabel = stringResource(id = R.string.lastName)
    val nationalIDLabel = stringResource(id = R.string.national_ID)
    val dobLabel = stringResource(id = R.string.dob)
    val mobilityLabel = stringResource(id = R.string.mobility)
    val userProfile = viewModel.userProfile

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Column(Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5,
                color = darkBlue
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondaryVariant
            )
        }

        Spacer(modifier = Modifier.height(44.dp))

        ProfilePhotoPicker(
            photoResource = viewModel.userProfile.profilePhotoUri,
            onPhotoPicked = viewModel::onProfilePhotoUriChange
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfilePhotoPicker(
    photoResource: String,
    onPhotoPicked: (String) -> Unit,
) {
    var openPicker by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        onPhotoPicked(uri.toString())
    }



    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Profile photo",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.secondaryVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Surface(
            onClick = { openPicker = !openPicker},
            modifier = Modifier.clip(MaterialTheme.shapes.medium)
        ) {

            // Profile photo
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photoResource)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.profile_placeholder),
                error = painterResource(id = R.drawable.profile_placeholder),
                modifier = Modifier.size(240.dp)
            )

            // Open the image picker when the surface is clicked.
            if (openPicker) {
                launcher.launch("image/*")
                openPicker = !openPicker
            }
        }
    }
}