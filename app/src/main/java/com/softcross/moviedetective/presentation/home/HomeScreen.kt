package com.softcross.moviedetective.presentation.home

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.GenreList
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.core.common.components.CustomSnackbar
import com.softcross.moviedetective.core.common.components.CustomText
import com.softcross.moviedetective.core.common.components.ErrorScreen
import com.softcross.moviedetective.core.common.extensions.startOffsetForPage
import com.softcross.moviedetective.core.domain.model.Actor
import com.softcross.moviedetective.core.domain.model.Movie
import com.softcross.moviedetective.presentation.home.components.ComingMovieItem
import com.softcross.moviedetective.presentation.home.components.DiscoverMovieItem
import com.softcross.moviedetective.presentation.home.components.HeaderContentItem
import com.softcross.moviedetective.presentation.home.components.LoadingContentItems
import com.softcross.moviedetective.presentation.home.components.LoadingHeaderContentItem
import com.softcross.moviedetective.presentation.home.components.PopularPeopleItem
import com.softcross.moviedetective.presentation.home.components.TrendMovieItem
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val scrollState = rememberScrollState()

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderContent(state = viewModel.topMovieState.value)
        CustomText(
            text = "Trending",
            fontFamilyID = R.font.poppins_semi_bold,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CustomText(
                text = "Explore trend movies",
                line = 2,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .weight(0.6f)
            )
            CustomText(
                text = "View More",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(0.2f)
                    .padding(start = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.icon_left_arrow),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentDescription = "",
                modifier = Modifier
                    .size(12.dp)
                    .weight(0.1f)
                    .padding(end = 8.dp)
            )
        }
        TrendMoviesContent(viewModel.trendMovieState.value)
        CustomText(
            text = "Discover",
            fontFamilyID = R.font.poppins_semi_bold,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            CustomText(
                text = "Explore movies and with category",
                line = 2,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .weight(0.6f)
            )
            CustomText(
                text = "View More",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(0.2f)
                    .padding(start = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.icon_left_arrow),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentDescription = "",
                modifier = Modifier
                    .size(12.dp)
                    .weight(0.1f)
                    .padding(end = 8.dp)
            )
        }
        DiscoverMovieContent(viewModel.discoverMovieState.value)
        CustomText(
            text = "Coming Soon",
            fontFamilyID = R.font.poppins_semi_bold,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CustomText(
                text = "Explore coming movies",
                line = 2,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .weight(0.6f)
            )
            CustomText(
                text = "View More",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(0.2f)
                    .padding(start = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.icon_left_arrow),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentDescription = "",
                modifier = Modifier
                    .size(12.dp)
                    .weight(0.1f)
                    .padding(end = 8.dp)
            )
        }
        ComingSoonMovieContent(viewModel.upcomingMovieState.value)
        CustomText(
            text = "Popular People",
            fontFamilyID = R.font.poppins_semi_bold,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CustomText(
                text = "Explore popular actors",
                line = 2,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .weight(0.6f)
            )
            CustomText(
                text = "View More",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(0.2f)
                    .padding(start = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.icon_left_arrow),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentDescription = "",
                modifier = Modifier
                    .size(12.dp)
                    .weight(0.1f)
                    .padding(end = 8.dp)
            )
        }
        PopularPeoplesContent(viewModel.popularPeopleState.value)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeaderContent(state: ScreenState<List<Movie>>) {
    when (state) {
        is ScreenState.Loading -> {
            LoadingHeaderContentItem()
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
                    }
                )
            }
        }

        is ScreenState.Error -> {
            ErrorScreen(message = state.message)
        }
    }
}

@Composable
fun TrendMoviesContent(state: ScreenState<List<Movie>>) {
    when (state) {
        is ScreenState.Error -> {
            ErrorScreen(message = state.message)
        }

        is ScreenState.Loading -> {
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(3) {
                    LoadingContentItems()
                }
            }
        }

        is ScreenState.Success -> {
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(state.uiData) {
                    TrendMovieItem(movie = it)
                }
            }
        }
    }
}

@Composable
fun DiscoverMovieContent(state: ScreenState<List<Movie>>) {
    when (state) {
        is ScreenState.Error -> {
            ErrorScreen(message = state.message)
        }

        is ScreenState.Loading -> {
            Column {
                Row(
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    LazyRow(
                        modifier = Modifier.weight(0.8f)
                    ) {
                        items(GenreList.getMovieGenreList()) {
                            Card(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                CustomText(
                                    text = it.genreName,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                            }
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.icon_filter),
                        contentDescription = "Filter",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier.weight(0.1f)
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    items(3) {
                        LoadingContentItems()
                    }
                }
            }
        }

        is ScreenState.Success -> {
            Column {
                Row(
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    LazyRow(
                        modifier = Modifier.weight(0.8f)
                    ) {
                        items(GenreList.getMovieGenreList()) {
                            Card(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                CustomText(
                                    text = it.genreName,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                            }
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.icon_filter),
                        contentDescription = "Filter",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier.weight(0.1f)
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    items(state.uiData) { movie ->
                        DiscoverMovieItem(movie = movie)
                    }
                }
            }
        }
    }
}

@Composable
fun ComingSoonMovieContent(state: ScreenState<List<Movie>>) {
    when (state) {
        is ScreenState.Error -> {
            ErrorScreen(message = state.message)
        }

        ScreenState.Loading -> {
            LazyRow {
                items(3) {
                    LoadingContentItems()
                }
            }
        }

        is ScreenState.Success -> {
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(state.uiData) { movie ->
                    ComingMovieItem(movie = movie)
                }
            }
        }
    }
}

@Composable
fun PopularPeoplesContent(state: ScreenState<List<Actor>>) {
    when (state) {
        is ScreenState.Error -> {
            ErrorScreen(message = state.message)
        }

        ScreenState.Loading -> {
            LazyRow {
                items(3) {
                    LoadingContentItems()
                }
            }
        }

        is ScreenState.Success -> {
            LazyRow(
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            ) {
                items(state.uiData) { actor ->
                    PopularPeopleItem(actor)
                }
            }
        }
    }
}