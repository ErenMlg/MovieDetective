package com.softcross.moviedetective.presentation.components

import android.provider.CalendarContract.Colors
import android.widget.ImageView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import coil.drawable.CrossfadeDrawable
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottiePainter
import com.airbnb.lottie.compose.rememberLottieComposition
import com.softcross.moviedetective.BuildConfig
import com.softcross.moviedetective.R

@Composable
fun CustomAsyncImage(
    model: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop
) {
    SubcomposeAsyncImage(
        model = "${BuildConfig.POSTER_BASE_PATH}$model",
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        loading = {
            Image(
                painter = painterResource(id = R.drawable.icon_loading),
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(128.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentDescription = "Error"
            )
        },
        error = {
            Image(
                painter = painterResource(id = R.drawable.icon_error),
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(128.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentDescription = "Error"
            )
        }
    )
}

@Composable
fun CustomVideoAsyncImage(
    videoModel: String, contentDescription: String,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop
) {
    SubcomposeAsyncImage(
        model = "${BuildConfig.VIDEO_BASE_PATH}$videoModel/0.jpg",
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        loading = {
            Image(
                painter = painterResource(id = R.drawable.icon_loading),
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(128.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentDescription = "Error"
            )
        },
        error = {
            println(it.result.throwable.message)
            Image(
                painter = painterResource(id = R.drawable.icon_error),
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(128.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentDescription = "Error"
            )
        }
    )
}