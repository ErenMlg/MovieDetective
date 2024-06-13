package com.softcross.moviedetective.presentation.signup

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.components.CustomPasswordTextField
import com.softcross.moviedetective.core.common.components.CustomSnackbar
import com.softcross.moviedetective.core.common.components.CustomTextField
import com.softcross.moviedetective.core.common.components.LoadingTextButton
import com.softcross.moviedetective.core.common.extensions.emailRegex
import com.softcross.moviedetective.core.common.extensions.nameSurnameRegex
import com.softcross.moviedetective.core.common.extensions.passwordRegex
import com.softcross.moviedetective.core.common.extensions.rememberImeState
import com.softcross.moviedetective.presentation.theme.MovieDetectiveTheme


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val uiState = viewModel.registerUiState.value
    val scrollState = rememberScrollState()


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }

    LaunchedEffect(key1 = keyboardHeight) {
        scrollState.scrollBy(keyboardHeight.toFloat())
    }

    Box(modifier.imePadding()) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                Modifier.width(350.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = if (isSystemInDarkTheme()) painterResource(id = R.drawable.logo_dark) else painterResource(
                        id = R.drawable.logo_light
                    ),
                    contentDescription = "Logo",
                    modifier = Modifier.size(160.dp)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.register_text),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            Column(
                Modifier.width(350.dp)
            ) {
                CustomTextField(
                    givenValue = email,
                    placeHolder = stringResource(id = R.string.enter_email),
                    errorMessage = stringResource(id = R.string.email_validation_error),
                    onValueChange = { email = it },
                    regex = String::emailRegex
                )
                CustomPasswordTextField(
                    givenValue = password,
                    placeHolder = stringResource(id = R.string.enter_password),
                    onValueChange = { password = it }
                )
                CustomTextField(
                    givenValue = name,
                    placeHolder = stringResource(id = R.string.enter_name),
                    errorMessage = stringResource(id = R.string.name_validation_error),
                    onValueChange = { name = it },
                    regex = String::nameSurnameRegex
                )
                CustomTextField(
                    givenValue = surname,
                    placeHolder = stringResource(id = R.string.enter_surname),
                    errorMessage = stringResource(id = R.string.surname_validation_error),
                    onValueChange = { surname = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    regex = String::nameSurnameRegex
                )
                LoadingTextButton(
                    isLoading = uiState.isLoading,
                    isEnable = surname.nameSurnameRegex() && name.nameSurnameRegex() && password.passwordRegex() && email.emailRegex(),
                    onClick = { viewModel.registerUser(email, password, name, surname) },
                    buttonText = R.string.sing_up
                )
                Text(
                    text = stringResource(id = R.string.already_have_acc),
                    textAlign = TextAlign.End,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
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

@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RegisterScreenPreview() {
    MovieDetectiveTheme {
        RegisterScreen()
    }
}