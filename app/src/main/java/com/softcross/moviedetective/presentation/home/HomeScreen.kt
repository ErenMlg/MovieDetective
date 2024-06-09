package com.softcross.moviedetective.presentation.home

import android.content.res.Configuration
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.core.common.extensions.bouncingClickable
import com.softcross.moviedetective.core.common.extensions.startOffsetForPage
import kotlinx.coroutines.delay
import com.softcross.moviedetective.core.domain.model.Movie
import com.softcross.moviedetective.presentation.home.components.HeaderContentItem
import com.softcross.moviedetective.presentation.home.components.LoadingHeaderContentItem

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val scrollState = rememberScrollState()
    val topMovieState = viewModel.topMovieState.value


    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderContent(state = topMovieState)

        /*Text(
            text = "Trending",
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
            textAlign = TextAlign.Start,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )
        Text(
            text = "Explore trend movies",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            textAlign = TextAlign.Start,
            maxLines = 1,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 16.dp)
        )
        TrendMovies(movieList = fakeMovies)
        Text(
            text = "Discover",
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
            textAlign = TextAlign.Start,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp)
        )
        Text(
            text = "Explore movies and uncover new favorites",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            textAlign = TextAlign.Start,
            maxLines = 1,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 16.dp)
        )
        DiscoverMovie(movieList = fakeMovies, genres)
        Text(
            text = "Coming Soon",
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
            textAlign = TextAlign.Start,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp)
        )
        Text(
            text = "Unveiling Soon: Anticipated movie releases",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            textAlign = TextAlign.Start,
            maxLines = 1,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 16.dp)
        )
        ComingSoon(movieList = fakeMovies)
        Text(
            text = "Popular People",
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
            textAlign = TextAlign.Start,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Explore popular actors and find your favorite actor",
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                textAlign = TextAlign.Start,
                maxLines = 2,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 16.dp)
                    .weight(0.7f)
            )
            Row(
                Modifier.weight(0.3f)
            ) {
                Text(
                    text = "View More",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    color = Color.Blue,
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 16.dp)
                )
                Image(
                    Icons.Filled.PlayArrow,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
            }
        }
        PopularPeoples(
            listOf(
                "Emma Stone",
                "Marble",
                "Jenna Ortega",
                "Sydney Sweeney",
                "Emma Stone",
                "Marble",
                "Jenna Ortega",
                "Sydney Sweeney",
                "Emma Stone",
                "Marble",
                "Jenna Ortega",
                "Sydney Sweeney",
                "Emma Stone",
                "Marble",
                "Jenna Ortega",
                "Sydney Sweeney",
                "Emma Stone",
                "Marble",
                "Jenna Ortega",
                "Sydney Sweeney",
                "Emma Stone",
                "Marble",
                "Jenna Ortega",
                "Sydney Sweeney"
            )
        )*/
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeaderContent(state: ScreenState<List<Movie>>) {
    when (state) {
        is ScreenState.Loading -> {
            LoadingHeaderContentItem(modifier = Modifier)
        }

        is ScreenState.Success -> {
            val pagerState = rememberPagerState { state.uiData.size }
            LaunchedEffect(Unit) {
                while (true) {
                    delay(3000)
                    val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                    pagerState.animateScrollToPage(
                        nextPage,
                        animationSpec = tween(2000)
                    )
                }
            }
            HorizontalPager(state = pagerState) { page ->
                HeaderContentItem(
                    movie = state.uiData[page],
                    modifier = Modifier.graphicsLayer {
                        val startOffset = pagerState.startOffsetForPage(page)
                        translationX = size.width * (startOffset * 1)
                        alpha = (2f - startOffset) / 2f
                        val blur = (startOffset * 20f).coerceAtLeast(0.1f)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            renderEffect = RenderEffect
                                .createBlurEffect(
                                    blur, blur, Shader.TileMode.DECAL
                                )
                                .asComposeRenderEffect()
                        }
                        val scale = 1f - (startOffset * .1f)
                        scaleX = scale
                        scaleY = scale
                    })
            }
        }

        is ScreenState.Error -> {

        }
    }

}








@RequiresApi(Build.VERSION_CODES.S)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}

