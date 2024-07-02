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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.moviedetective.R
import com.softcross.moviedetective.common.CurrentUser
import com.softcross.moviedetective.common.GenreList
import com.softcross.moviedetective.common.extensions.clickableWithoutIndicator
import com.softcross.moviedetective.common.extensions.listToString
import com.softcross.moviedetective.common.extensions.startOffsetForPage
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.Content
import com.softcross.moviedetective.presentation.components.ActorItem
import com.softcross.moviedetective.presentation.components.ComingContentItem
import com.softcross.moviedetective.presentation.components.ContentItem
import com.softcross.moviedetective.presentation.components.ContentItemWithGenres
import com.softcross.moviedetective.presentation.components.TitleRowWithSubtitle
import com.softcross.moviedetective.presentation.components.CustomText
import com.softcross.moviedetective.presentation.components.ErrorScreen
import com.softcross.moviedetective.presentation.components.LoadingContentItem
import com.softcross.moviedetective.presentation.components.LoadingHeaderContentItem
import com.softcross.moviedetective.presentation.components.PagerContentItem
import kotlinx.coroutines.delay

enum class HomeMovieDataType{
    TREND, COMING, DISCOVER
}

@Composable
fun HomeScreen(
    onExit: () -> Unit,
    onMoreViewClick: (Int, String?) -> Unit,
    onPopularPeoples: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val selectedGenreList = rememberSaveable {
        mutableListOf(12, 28)
    }
    val onExitClick = remember {
        {
            context
                .getSharedPreferences("logFile", Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply()
            onExit()
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            CustomText(
                text = "Welcome ${CurrentUser.getCurrentUserName()}",
                fontFamilyID = R.font.poppins_semi_bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.icon_exit),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickableWithoutIndicator(onClick = onExitClick)
            )
        }
        TitleRowWithSubtitle(
            title = "Popular Movies",
            subTitle = "Explore popular movies on all time",
            onClick = remember { { onMoreViewClick(2, null) } }
        )
        PopularMoviesContent(state = viewModel.popularMovieState.value, onClick = remember { { onMovieClick(it) } })
        TitleRowWithSubtitle(
            title = "Trend Movies",
            subTitle = "Explore trend movies on close time",
            onClick = remember { { onMoreViewClick(3, null) } }
        )
        MovieContentRow(homeMovieDataType = HomeMovieDataType.TREND, state = viewModel.trendMovieState.value, onClick = remember { { onMovieClick(it) } })
        TitleRowWithSubtitle(
            title = "Discover Content",
            subTitle = "Explore movies by category",
            onClick = remember { { onMoreViewClick(1, selectedGenreList.listToString()) } }
        )
        GenreSelectionField(
            onSelect = remember { { genreList -> viewModel.discoverMovie(genreList) } },
            selectedGenreList = selectedGenreList
        )
        MovieContentRow(HomeMovieDataType.DISCOVER, state = viewModel.discoverMovieState.value, onClick = remember { { onMovieClick(it) } })
        TitleRowWithSubtitle(
            title = "Coming Soon Movies",
            subTitle = "Explore coming movies on soon",
            onClick = remember { { onMoreViewClick(0, null) } }
        )
        MovieContentRow(homeMovieDataType = HomeMovieDataType.COMING, state = viewModel.upcomingMovieState.value, onClick = remember { { onMovieClick(it) } })
        TitleRowWithSubtitle(
            title = "Popular Peoples",
            subTitle = "Explore popular actors on close time",
            onClick = remember { { onPopularPeoples() } }
        )
        PopularPeoplesContent(viewModel.popularPeopleState.value, remember { { onActorClick(it) } })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularMoviesContent(state: ScreenState<List<Content>>, onClick: (Int) -> Unit) {
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
                PagerContentItem(
                    content = state.uiData[page],
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
                    },
                    onClick = onClick
                )
            }
        }

        is ScreenState.Error -> ErrorScreen(message = state.message)

    }
}

@Composable
fun GenreSelectionField(
    onSelect: (List<Int>) -> Unit,
    selectedGenreList: MutableList<Int>
) {
    val genreList = remember { GenreList.getMovieGenreList() }
    LazyRow(
        Modifier.padding(top = 8.dp)
    ) {
        items(items = genreList, key = { it.genreID }) { genre ->
            var isSelected by remember { mutableStateOf(selectedGenreList.contains(genre.genreID)) }
            val onClick: () -> Unit = remember {
                {
                    if (selectedGenreList.contains(genre.genreID)) {
                        selectedGenreList.remove(genre.genreID)
                    } else {
                        selectedGenreList.add(genre.genreID)
                    }
                    isSelected = selectedGenreList.contains(genre.genreID)
                    if (selectedGenreList.isEmpty()) {
                        onSelect(listOf(12, 28))
                    } else {
                        onSelect(selectedGenreList)
                    }
                }
            }
            Card(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clickableWithoutIndicator(onClick = onClick),
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
fun MovieContentRow(homeMovieDataType: HomeMovieDataType, state: ScreenState<List<Content>>, onClick: (Int) -> Unit){
    when (state) {
        is ScreenState.Error -> ErrorScreen(message = state.message)
        is ScreenState.Loading -> {
            LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
                items(3) {
                    LoadingContentItem()
                }
            }
        }
        is ScreenState.Success -> {
            LazyRow(
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                items(items = state.uiData, key = { it.id }) { movie ->
                    when(homeMovieDataType){
                        HomeMovieDataType.TREND -> ContentItem(content = movie, onClick)
                        HomeMovieDataType.COMING -> ComingContentItem(content = movie, onClick)
                        HomeMovieDataType.DISCOVER -> ContentItemWithGenres(content = movie, onClick)
                    }
                }
            }
        }
    }
}

@Composable
fun PopularPeoplesContent(state: ScreenState<List<Actor>>, onClick: (Int) -> Unit) {
    when (state) {
        is ScreenState.Error -> ErrorScreen(message = state.message)

        is ScreenState.Loading -> {
            LazyRow {
                items(3) {
                    LoadingContentItem()
                }
            }
        }

        is ScreenState.Success -> {
            LazyRow(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 24.dp)
            ) {
                items(
                    items = state.uiData,
                    key = { actor ->
                        actor.id
                    }) { actor ->
                    ActorItem(actor, onClick)
                }
            }
        }
    }
}



