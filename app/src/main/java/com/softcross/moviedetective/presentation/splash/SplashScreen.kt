package com.softcross.moviedetective.presentation.splash

import android.content.Context
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.core.common.components.CustomSnackbar
import com.softcross.moviedetective.core.common.components.CustomText

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onEntryNavigate: () -> Unit,
    onLogged: () -> Unit
) {
    SplashAnimation()
    val context = LocalContext.current
    val stayLogged by remember {
        mutableStateOf(
            context.getSharedPreferences("logFile", Context.MODE_PRIVATE)
                .getBoolean("stayLogged", false)
        )
    }
    if (stayLogged) {
        val loggedUser = context.getSharedPreferences("logFile", Context.MODE_PRIVATE)
            .getString("userID", "") ?: ""
        viewModel.getUserWithID(loggedUser)
    }

    when (val state = viewModel.state.value) {
        is ScreenState.Loading -> {}

        is ScreenState.Success -> {
            if (stayLogged) {
                LaunchedEffect(key1 = viewModel.userState.value) {
                    if (viewModel.userState.value) onLogged()
                }
            } else {
                LaunchedEffect(key1 = state) {
                    onEntryNavigate()
                }
            }
        }

        is ScreenState.Error -> {
            CustomSnackbar(errorMessage = state.message, modifier = Modifier)
        }
    }
}

@Composable
fun SplashAnimation() {

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = if (isSystemInDarkTheme()) painterResource(id = R.drawable.logo_dark) else painterResource(
                id = R.drawable.logo_light
            ),
            contentDescription = "Logo",
            modifier = Modifier
                .size(160.dp)
                .scale(scale.value)
        )
        CustomText(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.primary,
            fontFamilyID = R.font.poppins_semi_bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}