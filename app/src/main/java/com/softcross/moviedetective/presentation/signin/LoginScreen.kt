package com.softcross.moviedetective.presentation.signin

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.softcross.moviedetective.common.extensions.clickableWithoutIndicator
import com.softcross.moviedetective.presentation.components.LoadingTextButton
import com.softcross.moviedetective.core.common.extensions.emailRegex
import com.softcross.moviedetective.core.common.extensions.passwordRegex
import com.softcross.moviedetective.presentation.components.CustomPasswordTextField
import com.softcross.moviedetective.presentation.components.CustomSnackbar
import com.softcross.moviedetective.presentation.components.CustomText
import com.softcross.moviedetective.presentation.components.CustomTextField

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    onCreateUser: () -> Unit
) {
    val context = LocalContext.current

    val uiState = viewModel.loginUiState.value
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.user) {
        uiState.user?.let {
            CurrentUser.setCurrentUser(it)
            if (checked) {
                context.getSharedPreferences("logFile", Context.MODE_PRIVATE).edit()
                    .putBoolean("stayLogged", true).apply()
                context.getSharedPreferences("logFile", Context.MODE_PRIVATE).edit()
                    .putString("userID", CurrentUser.getCurrentUserID()).apply()
            }
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
                    isLoading = viewModel.loginUiState.value.isLoading,
                    isEnable = email.emailRegex() && password.passwordRegex(),
                    onClick = {
                        viewModel.loginUser(email.trim(), password.trim())
                        keyboardController?.hide()
                    },
                    buttonText = R.string.sing_in
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = {
                            checked = true
                        },
                        modifier = Modifier.size(24.dp),
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.secondary)
                    )
                    CustomText(
                        text = "Stay logged",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    CustomText(
                        text = stringResource(id = R.string.dont_have_acc),
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickableWithoutIndicator {
                                keyboardController?.hide()
                                onCreateUser()
                            }
                    )
                }
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