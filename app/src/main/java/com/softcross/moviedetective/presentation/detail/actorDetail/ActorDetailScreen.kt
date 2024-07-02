package com.softcross.moviedetective.presentation.detail.actorDetail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.moviedetective.R
import com.softcross.moviedetective.common.extensions.clickableWithoutIndicator
import com.softcross.moviedetective.common.extensions.convertToFormattedDate
import com.softcross.moviedetective.common.extensions.convertToFormattedYear
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.model.ActorDetail
import com.softcross.moviedetective.presentation.components.ContentItem
import com.softcross.moviedetective.presentation.components.CustomAsyncImage
import com.softcross.moviedetective.presentation.components.CustomText

@Composable
fun ActorDetailScreen(
    viewModel: ActorDetailViewModel = hiltViewModel(),
    onSeriesClick: (Int) -> Unit,
    onMovieClick: (Int) -> Unit,
    onPhotoClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    when (val detailState = viewModel.detailState.value) {
        is ScreenState.Loading -> {
            // Loading
        }

        is ScreenState.Error -> {
            // Error
        }

        is ScreenState.Success -> SuccessContent(
            detailState.uiData,
            onBackClick = onBackClick,
            onSeriesClick = onSeriesClick,
            onMovieClick = onMovieClick,
            onPhotoClick = onPhotoClick
        )
    }
}

@Composable
fun SuccessContent(
    actorDetail: ActorDetail,
    onBackClick: () -> Unit,
    onSeriesClick: (Int) -> Unit,
    onMovieClick: (Int) -> Unit,
    onPhotoClick: (Int) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val primary = MaterialTheme.colorScheme.background
        Box(
            Modifier.height(300.dp)
        ) {
            CustomAsyncImage(
                model = actorDetail.actorMovies.first().image,
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .drawWithContent {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, primary),
                            startY = size.height * 0.1f,
                            endY = size.height * 0.9f
                        )
                        drawContent()
                        drawRect(gradient)
                    }
            )
            CustomAsyncImage(
                model = actorDetail.image,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .shadow(elevation = 8.dp, CircleShape)
                    .clip(CircleShape)
                    .height(160.dp)
                    .width(160.dp)
                    .border(4.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter)
            )
            IconButton(
                onClick = onBackClick, colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_right_arrow),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = { /*TODO*/ }, colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp, top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_fav),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
        CustomText(
            text = actorDetail.name,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            fontFamilyID = R.font.poppins_semi_bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        )
        CustomText(
            text = "Biography",
            fontSize = 18.sp,
            fontFamilyID = R.font.poppins_medium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
        )
        CustomText(
            text = actorDetail.biography,
            fontSize = 14.sp,
            line = Int.MAX_VALUE,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp)
        )
        CustomText(
            text = "Born",
            fontSize = 18.sp,
            fontFamilyID = R.font.poppins_medium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
        )
        CustomText(
            text = actorDetail.birthday.convertToFormattedDate(),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp)
        )
        CustomText(
            text = actorDetail.birthdayPlace,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(start = 16.dp)
        )
        CustomText(
            text = "Images",
            fontSize = 18.sp,
            fontFamilyID = R.font.poppins_medium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
        )
        LazyRow(
            Modifier.padding(start = 8.dp, top = 8.dp)
        ) {
            items(actorDetail.actorImages.size, key = { actorDetail.actorImages[it] }) {
                CustomAsyncImage(
                    model = actorDetail.actorImages[it],
                    contentDescription = "",
                    modifier = Modifier
                        .padding(4.dp)
                        .size(100.dp)
                        .shadow(2.dp, MaterialTheme.shapes.medium)
                        .clip(MaterialTheme.shapes.medium)
                        .clickableWithoutIndicator { onPhotoClick(it) }

                )
            }
        }
        CustomText(
            text = "Movies",
            fontSize = 18.sp,
            fontFamilyID = R.font.poppins_semi_bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            items(actorDetail.actorMovies.size,
                key = { actorDetail.actorMovies[it].id }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomTimeBar()
                    Text(
                        text = actorDetail.actorMovies[it].releaseDate.convertToFormattedYear(),
                        color = MaterialTheme.colorScheme.primary
                    )
                    ContentItem(
                        content = actorDetail.actorMovies[it],
                        onClick = {
                            onMovieClick(it)
                        }
                    )
                }
            }
        }
        CustomText(
            text = "TV Series",
            fontSize = 18.sp,
            fontFamilyID = R.font.poppins_semi_bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            items(actorDetail.actorSeries.size,
                key = { actorDetail.actorSeries[it].id }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomTimeBar()
                    Text(
                        text = actorDetail.actorSeries[it].releaseDate.convertToFormattedYear(),
                        color = MaterialTheme.colorScheme.primary
                    )
                    ContentItem(
                        content = actorDetail.actorSeries[it],
                        onClick = { onSeriesClick(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTimeBar() {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    Canvas(modifier = Modifier.padding(16.dp), contentDescription = "") {
        drawLine(
            color = primaryColor,
            start = Offset(x = -15f, y = 0f),
            end = Offset(x = -88.dp.toPx(), y = 0f),
            strokeWidth = 1.dp.toPx(),
        )
        drawCircle(
            color = primaryColor,
            radius = 6.dp.toPx(),
            style = Stroke(
                width = 1.dp.toPx()
            )
        )
        drawCircle(
            color = secondaryColor,
            radius = 2.dp.toPx()
        )
        drawLine(
            color = primaryColor,
            start = Offset(x = 15f, y = 0f),
            end = Offset(x = 88.dp.toPx(), y = 0f),
            strokeWidth = 1.dp.toPx()
        )
    }
}