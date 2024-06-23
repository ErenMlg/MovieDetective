package com.softcross.moviedetective.presentation.components

import android.provider.CalendarContract.Colors
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.R

@Composable
fun CustomAsyncImage(
    model: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    alignment: Alignment,
    contentScale: ContentScale
) {

    SubcomposeAsyncImage(
        model = "${BuildConfig.POSTER_BASE_PATH}${model}",
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    color = Color.Gray
                )
            }
        },
        error = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
            ){
                Image(
                    painter = painterResource(id = R.drawable.icon_error),
                    contentDescription = "Error",
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

        }
    )
}