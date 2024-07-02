package com.softcross.moviedetective.presentation.series

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.moviedetective.common.GenreList
import com.softcross.moviedetective.common.extensions.clickableWithoutIndicator
import com.softcross.moviedetective.common.extensions.startOffsetForPage
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.model.Content
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

enum class SeriesScreenDataType {
    TOP, AIRING, COMING, DISCOVER
}

@Composable
fun SeriesScreen(
    onSeriesClick: (Int) -> Unit
) {
    val viewModel: SeriesViewModel = hiltViewModel()
    val selectedGenreList = rememberSaveable {
        mutableListOf(12, 28)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TitleRowWithSubtitle(
            title = "Popular Content",
            subTitle = "Explore popular series on all time",
            onClick = remember { {} }
        )
        PopularSeriesContent(
            state = viewModel.popularSeriesState.value,
            onClick = remember { { onSeriesClick(it) } }
        )
        TitleRowWithSubtitle(
            title = "Top Rated Content",
            subTitle = "Explore top rated series on all time",
            onClick = remember { {} }
        )
        SeriesContentRow(
            seriesDataType = SeriesScreenDataType.TOP,
            state = viewModel.topSeriesState.value,
            onClick = remember { { onSeriesClick(it) } }
        )
        TitleRowWithSubtitle(
            title = "Airing Today",
            subTitle = "Explore today airing series",
            onClick = remember { {} }
        )
        SeriesContentRow(
            seriesDataType = SeriesScreenDataType.AIRING,
            state = viewModel.airingSeriesState.value,
            onClick = remember { { onSeriesClick(it) } }
        )
        TitleRowWithSubtitle(
            title = "Discover Content",
            subTitle = "Explore series by genre",
            onClick = remember { {} }
        )
        GenreSelectionField(
            onSelect = remember { {} },
            selectedGenreList = selectedGenreList
        )
        SeriesContentRow(
            seriesDataType = SeriesScreenDataType.DISCOVER,
            state = viewModel.discoverSeriesState.value,
            onClick = remember { { onSeriesClick(it) } }
        )
        TitleRowWithSubtitle(
            title = "Coming Soon Content",
            subTitle = "Explore coming series on soon",
            onClick = remember { {} }
        )
        SeriesContentRow(
            seriesDataType = SeriesScreenDataType.COMING,
            state = viewModel.upcomingSeriesState.value,
            onClick = remember { { onSeriesClick(it) } }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularSeriesContent(state: ScreenState<List<Content>>, onClick: (Int) -> Unit) {
    when (state) {
        is ScreenState.Error -> ErrorScreen(message = state.message)
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
    }
}

@Composable
fun SeriesContentRow(
    seriesDataType: SeriesScreenDataType,
    state: ScreenState<List<Content>>,
    onClick: (Int) -> Unit
) {
    when (state) {
        is ScreenState.Error -> ErrorScreen(message = state.message)
        is ScreenState.Loading -> {
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
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
                items(items = state.uiData, key = { it.id }) { series ->
                    when (seriesDataType) {
                        SeriesScreenDataType.TOP -> ContentItem(content = series, onClick = onClick)
                        SeriesScreenDataType.AIRING -> ContentItem(
                            content = series,
                            onClick = onClick
                        )

                        SeriesScreenDataType.COMING -> ComingContentItem(
                            content = series,
                            onClick = onClick
                        )

                        SeriesScreenDataType.DISCOVER -> ContentItemWithGenres(
                            content = series,
                            onClick = onClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GenreSelectionField(
    onSelect: (List<Int>) -> Unit,
    selectedGenreList: MutableList<Int>
) {
    val genreList = remember { GenreList.getSeriesGenreList() }
    var isSelected by remember {
        mutableStateOf(false)
    }
    LazyRow(
        Modifier.padding(top = 8.dp)
    ) {
        items(items = genreList, key = { it.genreID }) { genre ->
            isSelected = selectedGenreList.contains(genre.genreID)
            Card(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clickableWithoutIndicator {
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
