package com.thousandmiles.ui.onboarding

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.thousandmiles.R
import com.thousandmiles.model.profile.user.User
import com.thousandmiles.model.profile.user.parseDOB
import com.thousandmiles.ui.auth.TextFieldWithErrorOption
import com.thousandmiles.ui.components.DatePickerDialog
import com.thousandmiles.ui.components.HeaderText
import com.thousandmiles.ui.components.PrimaryButton
import com.thousandmiles.ui.components.TextFieldErrorMessage
import com.thousandmiles.ui.theme.darkBlue
import com.thousandmiles.ui.theme.lighterBlue
import com.thousandmiles.viewmodel.onboarding.OnboardingViewModel
import com.thousandmiles.viewmodel.onboarding.formatNationalID
import java.time.LocalDate

private val idOffsetMapping by lazy {
    object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 10) return offset + 1
            if (offset <= 13) return offset + 2
            return 15
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 12) return offset - 1
            if (offset <= 15) return offset - 2
            return 13
        }
    }
}

@Composable
fun UserDetailsPage(
    viewModel: OnboardingViewModel,
    modifier: Modifier = Modifier,
    onNext: () -> Unit
) {

    // String resources
    val title = stringResource(id = R.string.personal_details_title)
    val subtitle = stringResource(id = R.string.personal_details_subtitle)
    val firstNameLabel = stringResource(id = R.string.firstName)
    val lastNameLabel = stringResource(id = R.string.lastName)
    val nationalIDLabel = stringResource(id = R.string.national_ID)
    val dobLabel = stringResource(id = R.string.dob)
    val dobDialogTitle = stringResource(id = R.string.dob_title)
    val mobilityLabel = stringResource(id = R.string.mobility)
    val nextButtonLabel = stringResource(id = R.string.next_button)

    // User profile information
    val userProfile = viewModel.userInfo

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {

        // Title and subtitle
        HeaderText(title = title, subtitle = subtitle, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(44.dp))

        // Profile photo
        ProfilePhotoPicker(
            photoResource = viewModel.userInfo.profilePhotoUri,
            onPhotoPicked = viewModel::onProfilePhotoUriChange
        )

        Spacer(modifier = Modifier.height(32.dp))

        // First name
        TextFieldWithErrorOption(
            value = userProfile.firstName,
            labelText = firstNameLabel,
            onValueChange = viewModel::onFirstNameChange,
            lighterBackground = true,
            isError = viewModel.firstNameBlankError,
            onError = {
                TextFieldErrorMessage(errorMessage = stringResource(id = R.string.firstname_blank))
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(12.dp))

        // Last name
        TextFieldWithErrorOption(
            value = userProfile.lastName,
            labelText = lastNameLabel,
            onValueChange = viewModel::onLastNameChange,
            lighterBackground = true,
            isError = viewModel.lastNameBlankError,
            onError = {
                TextFieldErrorMessage(errorMessage = stringResource(id = R.string.lastname_blank))
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // National ID
        TextFieldWithErrorOption(
            value = userProfile.nationalID,
            labelText = nationalIDLabel,
            onValueChange = viewModel::onNationalIDChange,
            lighterBackground = true,
            visualTransformation = { text ->
                TransformedText(
                    text = AnnotatedString(formatNationalID(text.toString())),
                    offsetMapping = idOffsetMapping
                )
            },
            isError = viewModel.nationalIDBlankError || viewModel.invalidNationalIDError,
            onError = {
                val errorMessage =
                    if (viewModel.nationalIDBlankError)
                        stringResource(id = R.string.nationalID_blank)
                    else
                        stringResource(id = R.string.invalid_nationalID)

                TextFieldErrorMessage(errorMessage = errorMessage)
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))
        
        // Date of birth
        DateOfBirthField(
            userInfo = userProfile,
            title = dobDialogTitle,
            label = dobLabel,
            isError = viewModel.dateOfBirthBlankError,
            onDateChange = viewModel::onDateOfBirthChange,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = TextFieldDefaults.MinHeight)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mobility disabled
        SwitchBox(
            label = mobilityLabel,
            checked = userProfile.mobilityDisabled,
            onCheckedChange = viewModel::onMobilityDisabledChange,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = TextFieldDefaults.MinHeight)
                .clip(MaterialTheme.shapes.medium)
                .background(color = lighterBlue)
        )

        Spacer(modifier = Modifier.height(32.dp))
        
        // Next button
        PrimaryButton(
            text = nextButtonLabel,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            onClick = {
                if (viewModel.validateUserDetails()) onNext()
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DateOfBirthField(
    userInfo: User,
    title: String,
    label: String,
    isError: Boolean,
    modifier: Modifier = Modifier,
    onDateChange: (LocalDate) -> Unit
) {
    // Whether or not to reveal date picker
    var showDatePicker by remember { mutableStateOf(false) }

    // Date of birth
    Surface(
        modifier = modifier,
        color = Color.Transparent,
        shape = MaterialTheme.shapes.medium,
        onClick = { showDatePicker = !showDatePicker },
    ) {
        // Last name
        TextFieldWithErrorOption(
            value = userInfo.dateOfBirth,
            labelText = label,
            enabled = false,
            lighterBackground = true,
            isError = isError,
            onError = {
                TextFieldErrorMessage(errorMessage = stringResource(id = R.string.dob_blank))
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        DatePickerDialog(
            initialDate = userInfo.parseDOB(),
            showDialog = showDatePicker,
            title = title,
            dismissRequest = {
                showDatePicker = !showDatePicker
            },
            onDateChange = onDateChange
        )
    }
}

@Composable
fun SwitchBox(
    label: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (text, switch) = createRefs()

        Text(
            text = label,
            color = MaterialTheme.colors.secondaryVariant,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.constrainAs(text) {
                start.linkTo(parent.start, margin = 16.dp)
                centerVerticallyTo(parent)
            }
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = darkBlue,
                checkedTrackColor = MaterialTheme.colors.secondaryVariant,
                uncheckedThumbColor = MaterialTheme.colors.secondaryVariant,
                uncheckedTrackColor = MaterialTheme.colors.secondary
            ),
            modifier = Modifier.constrainAs(switch) {
                end.linkTo(parent.end, margin = 16.dp)
                centerVerticallyTo(parent)
            }
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

            // Profile photo2
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photoResource)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.profile_placeholder),
                error = painterResource(id = R.drawable.profile_placeholder),
                modifier = Modifier.size(200.dp)
            )

            // Open the image picker when the surface is clicked.
            if (openPicker) {
                launcher.launch("image/*")
                openPicker = !openPicker
            }
        }
    }
}