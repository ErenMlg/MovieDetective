package com.softcross.moviedetective.presentation.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.moviedetective.R
import com.softcross.moviedetective.common.CurrentUser
import com.softcross.moviedetective.core.common.components.CustomPasswordTextField
import com.softcross.moviedetective.core.common.components.CustomSnackbar
import com.softcross.moviedetective.core.common.components.CustomText
import com.softcross.moviedetective.core.common.components.CustomTextField
import com.softcross.moviedetective.core.common.components.LoadingTextButton
import com.softcross.moviedetective.core.common.extensions.emailRegex
import com.softcross.moviedetective.core.common.extensions.passwordRegex

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    onCreateUser: () -> Unit
) {
    val uiState = viewModel.loginUiState.value
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.user) {
        uiState.user?.let {
            CurrentUser.setCurrentUser(it)
            onSuccess()
        }
    }

    Box(
        modifier.imePadding()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                Modifier
                    .width(350.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = if (isSystemInDarkTheme()) painterResource(id = R.drawable.logo_dark) else painterResource(
                        id = R.drawable.logo_light
                    ),
                    contentDescription = "Logo",
                    modifier = Modifier.size(160.dp)
                )
                CustomText(
                    text = stringResource(id = R.string.app_name),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamilyID = R.font.poppins_semi_bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                CustomText(
                    text = stringResource(id = R.string.welcome_login),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    line = 2,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Column(
                Modifier
                    .width(350.dp)
                    .padding(bottom = 32.dp)
            ) {
                CustomTextField(
                    givenValue = email,
                    placeHolder = stringResource(id = R.string.enter_email),
                    onValueChange = { email = it },
                    errorMessage = stringResource(id = R.string.email_validation_error),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    regex = String::emailRegex
                )
                CustomPasswordTextField(
                    givenValue = password,
                    placeHolder = stringResource(id = R.string.enter_password),
                    onValueChange = { password = it },
                )
                LoadingTextButton(
                    isLoading = isLoading,
                    isEnable = email.emailRegex() && password.passwordRegex(),
                    onClick = {
                        viewModel.loginUser(email.trim(), password.trim())
                        keyboardController?.hide()
                        isLoading = true
                    },
                    buttonText = R.string.sing_in
                )
                CustomText(
                    text = stringResource(id = R.string.dont_have_acc),
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                keyboardController?.hide()
                                onCreateUser()
                            }
                        )
                )
            }
        }
        uiState.errorMessage?.let {
            CustomSnackbar(
                errorMessage = it,
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
            )
        }
    }
}