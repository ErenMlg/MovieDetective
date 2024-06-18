package com.softcross.moviedetective.presentation.home

import android.content.Context
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.moviedetective.R
import com.softcross.moviedetective.common.CurrentUser
import com.softcross.moviedetective.common.extensions.clickableWithoutIndicator
import com.softcross.moviedetective.core.common.GenreList
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.core.common.components.CustomText
import com.softcross.moviedetective.core.common.components.ErrorScreen
import com.softcross.moviedetective.core.common.extensions.startOffsetForPage
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.Genre
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.presentation.home.components.ComingMovieItem
import com.softcross.moviedetective.presentation.home.components.DiscoverMovieItem
import com.softcross.moviedetective.presentation.home.components.LoadingContentItems
import com.softcross.moviedetective.presentation.home.components.LoadingHeaderContentItem
import com.softcross.moviedetective.presentation.home.components.PopularContentItem
import com.softcross.moviedetective.presentation.home.components.PopularPeopleItem
import com.softcross.moviedetective.presentation.home.components.TrendMovieItem
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onExit: () -> Unit
) {
    val selectedGenreList = remember { mutableListOf<Int>() }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomText(
                text = "Welcome ${CurrentUser.getCurrentUserName()}",
                fontFamilyID = R.font.poppins_semi_bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.icon_exit),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp, top = 16.dp)
                    .clickableWithoutIndicator {
                        context
                            .getSharedPreferences("logFile", Context.MODE_PRIVATE)
                            .edit()
                            .clear()
                            .apply()
                        onExit()
                    }
            )
        }
        ContentTitleField(
            title = "Popular",
            subTitle = "Explore popular movies on all time"
        )
        PopularContent(viewModel.topMovieState.value)
        ContentTitleField(
            title = "Trend",
            subTitle = "Explore trend movies on close time"
        )
        TrendMoviesContent(viewModel.trendMovieState.value)
        ContentTitleField(
            title = "Discover",
            subTitle = "Explore movies and with category"
        )
        GenreSelectionField(
            onSelect = {
                if (selectedGenreList.contains(it)) {
                    selectedGenreList.remove(it)
                } else {
                    selectedGenreList.add(it)
                }
                if (selectedGenreList.isEmpty()) viewModel.discoverMoviesByGenre(listOf(12, 28))
                else viewModel.discoverMoviesByGenre(selectedGenreList)
            },
            selectedGenreList = selectedGenreList
        )
        DiscoverMovieContent(viewModel.discoverMovieState.value)


        ContentTitleField(
            title = "Coming Soon",
            subTitle = "Explore coming movies on soon"
        )
        ComingSoonMovieContent(viewModel.upcomingMovieState.value)
        ContentTitleField(
            title = "Popular People",
            subTitle = "Explore popular actors on close time"
        )
        PopularPeoplesContent(viewModel.popularPeopleState.value)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularContent(state: ScreenState<List<Movie>>) {
    when (state) {
        is ScreenState.Loading -> LoadingHeaderContentItem()

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
                PopularContentItem(
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

        is ScreenState.Error -> ErrorScreen(message = state.message)

    }
}

@Composable
fun TrendMoviesContent(state: ScreenState<List<Movie>>) {
    when (state) {
        is ScreenState.Error -> ErrorScreen(message = state.message)

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
                items(items = state.uiData, key = { it.movieID }) { movie ->
                    TrendMovieItem(movie = movie)
                }
            }
        }
    }
}

@Composable
fun GenreSelectionField(
    onSelect: (Int) -> Unit,
    genreList: List<Genre> = GenreList.getMovieGenreList(),
    selectedGenreList: List<Int>
) {
    LazyRow(
        Modifier.padding(top = 8.dp)
    ) {
        items(items = genreList, key = { it.genreID }) { genre ->
            val isSelected = selectedGenreList.contains(genre.genreID)
            Card(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clickableWithoutIndicator {
                        onSelect(genre.genreID)
                    },
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else Color.White
                )
            ) {
                CustomText(
                    text = genre.genreName,
                    color = if (isSelected) Color.White else Color.DarkGray,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
fun DiscoverMovieContent(
    state: ScreenState<List<Movie>>
) {
    println("Recreated Discover Movie Content")
    when (state) {
        is ScreenState.Error -> ErrorScreen(message = state.message)

        is ScreenState.Loading -> {
            LazyRow(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                items(3) {
                    LoadingContentItems()
                }
            }
        }

        is ScreenState.Success -> {
            LazyRow(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                items(items = state.uiData, key = { it.movieID }) { movie ->
                    DiscoverMovieItem(movie = movie)
                }
            }
        }

    }
}

@Composable
fun ComingSoonMovieContent(state: ScreenState<List<Movie>>) {
    when (state) {
        is ScreenState.Error -> ErrorScreen(message = state.message)

        is ScreenState.Loading -> {
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
                items(items = state.uiData, key = { it.movieID }) { movie ->
                    ComingMovieItem(movie = movie)
                }
            }
        }
    }
}

@Composable
fun PopularPeoplesContent(state: ScreenState<List<Actor>>) {
    when (state) {
        is ScreenState.Error -> ErrorScreen(message = state.message)

        is ScreenState.Loading -> {
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
                items(
                    items = state.uiData,
                    key = { actor ->
                        actor.id
                    }) { actor ->
                    PopularPeopleItem(actor)
                }
            }
        }
    }
}

@Composable
fun ContentTitleField(title: String, subTitle: String) {
    println("Recreated Content Title Field")
    CustomText(
        text = title,
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
            text = subTitle,
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
}