package com.softcross.moviedetective.presentation.detail.seriesDetail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.softcross.moviedetective.R
import com.softcross.moviedetective.common.GenreList
import com.softcross.moviedetective.common.extensions.clickableWithoutIndicator
import com.softcross.moviedetective.common.extensions.convertToFormattedDate
import com.softcross.moviedetective.common.extensions.dateTimeToFormattedDate
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.Content
import com.softcross.moviedetective.domain.model.Review
import com.softcross.moviedetective.domain.model.SeriesDetail
import com.softcross.moviedetective.domain.model.Video
import com.softcross.moviedetective.presentation.components.ActorItemWithCharacter
import com.softcross.moviedetective.presentation.components.ContentItem
import com.softcross.moviedetective.presentation.components.CustomAsyncImage
import com.softcross.moviedetective.presentation.components.CustomText
import com.softcross.moviedetective.presentation.components.CustomVideoAsyncImage
import com.softcross.moviedetective.presentation.components.ErrorScreen
import com.softcross.moviedetective.presentation.components.LoadingLazyRow
import com.softcross.moviedetective.presentation.components.TitleRow
import com.softcross.moviedetective.presentation.theme.MovieDetectiveTheme
import kotlinx.coroutines.delay

@Composable
fun SeriesDetailScreen(
    onActorClick: (Int) -> Unit,
    onSeriesClick: (Int) -> Unit,
    onPosterClick: (Int) -> Unit,
    onBackdropClick: (Int) -> Unit,
    onVideoClick: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    val viewModel: SeriesDetailViewModel = hiltViewModel()
    val detailState = viewModel.detailState.value
    val actorSeriesState = viewModel.actorSeriesState
    when (detailState) {
        is ScreenState.Error -> println(detailState.message)
        is ScreenState.Loading -> {}
        is ScreenState.Success -> SeriesDetailSuccessContent(
            seriesDetail = detailState.uiData,
            actorSeriesState = actorSeriesState,
            onActorMoviesClick = remember { { viewModel.onActorClicked(it) } },
            onSeriesClick = remember { { onSeriesClick(it) } },
            onActorClick = remember { { onActorClick(it) } },
            onBackClick = remember { { onBackClick() } }
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeriesDetailSuccessContent(
    seriesDetail: SeriesDetail,
    actorSeriesState: State<ScreenState<List<Content>>>,
    onSeriesClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit,
    onActorMoviesClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val genres = seriesDetail.series.genres.map {
        GenreList.findMovieGenreWithID(it)
    }.joinToString {
        it.genreName
    }
    val tabItems = listOf("Overview", "Seasons", "Episodes", "Photos & Videos", "Reviews")
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LaunchedEffect(key1 = selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
        LaunchedEffect(key1 = pagerState.currentPage, pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                selectedTabIndex = pagerState.currentPage
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            SeriesBannerContent(seriesDetail, {})
            CustomText(
                text = seriesDetail.series.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
                fontFamilyID = R.font.poppins_semi_bold,
                line = 2
            )
            CustomText(
                text = genres,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                line = 3
            )
            SeriesSubDetailRow(seriesDetail)
            ScrollableTabRow(
                edgePadding = 0.dp,
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.background,
                indicator = { tabPositions ->
                    if (selectedTabIndex < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                        )
                    }
                },
                divider = {
                    if (!scrollState.canScrollForward) {
                        HorizontalDivider(modifier = Modifier.shadow(8.dp))
                    }
                }
            ) {
                tabItems.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index },
                        selectedContentColor = MaterialTheme.colorScheme.background,
                        text = {
                            CustomText(
                                text = tabItem,
                                color = MaterialTheme.colorScheme.primary,
                                line = 2,
                                textAlign = TextAlign.Center,
                                fontFamilyID = R.font.poppins_medium,
                                fontSize = 14.sp
                            )
                        }
                    )
                }
            }
            HorizontalPager(state = pagerState) { index ->
                when (index) {
                    0 -> SeriesDetailOverViewTab(
                        seriesDetail = seriesDetail,
                        actorSeriesState = actorSeriesState,
                        onSeriesClick = { },
                        onActorClick = { },
                        onActorSeriesClick = { }
                    )

                    1 -> SeriesDetailPhotosVideosTab(
                        videos = seriesDetail.videos,
                        backDropImages = seriesDetail.backDropImages,
                        posterImages = seriesDetail.posterImages
                    )

                    2 -> SeriesDetailReviewsTab(
                        reviews = seriesDetail.reviews
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeriesBannerContent(seriesDetail: SeriesDetail, onBackClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val pagerState = rememberPagerState { seriesDetail.backDropImages.size }
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height((configuration.screenHeightDp * 0.7).dp)
    ) {
        HorizontalPager(state = pagerState) {
            CustomAsyncImage(
                model = seriesDetail.backDropImages[it],
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = BiasAlignment(0.5f, 0.8f),
                modifier = Modifier
                    .height((configuration.screenHeightDp * 0.6).dp)
                    .clip(GenericShape { size, _ ->
                        moveTo(0f, 0f)
                        lineTo(0f, size.height)
                        lineTo(size.width, size.height * 0.95f)
                        lineTo(size.width, 0f)
                    })
                    .fillMaxWidth()
            )
        }
        Card(
            shape = CircleShape,
            modifier = Modifier
                .align(BiasAlignment(1f, 0.75f))
                .padding(end = 16.dp)
                .shadow(8.dp, CircleShape),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_camera),
                tint = Color.White,
                contentDescription = "",
                modifier = Modifier
                    .padding(24.dp)
                    .size(32.dp)
            )
        }
        CustomAsyncImage(
            model = seriesDetail.series.image,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .padding(start = 16.dp)
                .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                .clip(MaterialTheme.shapes.small)
                .height(212.dp)
                .width(145.dp)
                .align(Alignment.BottomStart)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                onClick = { onBackClick() }, colors = IconButtonDefaults.iconButtonColors(
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
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_reminder),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = { /*TODO*/ }, colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_watchlist),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = { /*TODO*/ }, colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_add_to_list),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = { /*TODO*/ }, colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_done),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun SeriesSubDetailRow(seriesDetail: SeriesDetail) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            Modifier
                .padding(end = 8.dp)
                .background(MaterialTheme.colorScheme.onTertiary, CircleShape),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_star),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 8.dp, start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
            CustomText(
                text = "%.2f".format(seriesDetail.series.imdb),
                fontSize = 14.sp,
                color = Color.White,
                fontFamilyID = R.font.poppins_semi_bold,
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp, end = 16.dp)
            )
        }
        CustomText(
            text = seriesDetail.series.releaseDate.convertToFormattedDate(),
            modifier = Modifier.padding(end = 16.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            fontFamilyID = R.font.poppins_semi_bold,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun SeriesDetailOverViewTab(
    seriesDetail: SeriesDetail,
    actorSeriesState: State<ScreenState<List<Content>>>,
    onSeriesClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit,
    onActorSeriesClick: (Int) -> Unit,
) {
    var lineCount by remember {
        mutableIntStateOf(8)
    }
    var overflow by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .height((LocalConfiguration.current.screenHeightDp * 0.9).dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
    ) {
        CustomText(
            text = "Storyline",
            fontSize = 24.sp,
            fontFamilyID = R.font.poppins_semi_bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        CustomText(
            line = lineCount,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .animateContentSize { initialValue, targetValue ->
                    if (initialValue.height < targetValue.height) {
                        expandVertically()
                    } else {
                        shrinkVertically()
                    }
                },
            textLayoutResult = {
                overflow = it.hasVisualOverflow
            },
            text = seriesDetail.series.description,
        )
        CustomText(
            line = lineCount,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .clickableWithoutIndicator {
                    lineCount = if (overflow) Int.MAX_VALUE else 8
                },
            text = if (overflow) "Read More" else "Read Less",
        )
        TitleRow("Cast", {})
        LazyRow {
            items(seriesDetail.cast.size, key = {
                seriesDetail.cast[it].id
            }) {
                if (seriesDetail.cast.isEmpty()) {
                    ErrorScreen(message = "No Content Found")
                } else {
                    ActorItemWithCharacter(
                        people = seriesDetail.cast[it],
                        onClick = onActorClick
                    )
                }
            }
        }
        TitleRow("More Like This", {})
        LazyRow {
            items(seriesDetail.similarSeries.size, key = {
                seriesDetail.similarSeries[it].id
            }) {
                if (seriesDetail.similarSeries.isEmpty()) {
                    ErrorScreen(message = "No Content Found")
                } else {
                    ContentItem(
                        content = seriesDetail.similarSeries[it],
                        onClick = onSeriesClick
                    )
                }
            }
        }
        TitleRow("Casts More Series", {})
        SeriesDetailOverViewCastSelectionRow(seriesDetail.cast, onActorSeriesClick)
        when (val response = actorSeriesState.value) {
            is ScreenState.Error -> ErrorScreen(message = response.message)
            is ScreenState.Loading -> LoadingLazyRow()
            is ScreenState.Success -> LazyRow {
                items(response.uiData.size, key = {
                    response.uiData[it].id
                }) {
                    if (response.uiData.isEmpty()) {
                        ErrorScreen(message = "No Content Found")
                    } else {
                        ContentItem(
                            content = response.uiData[it],
                            onClick = onSeriesClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SeriesDetailOverViewCastSelectionRow(actors: List<Actor>, onClick: (Int) -> Unit) {
    var selectedItemID by remember {
        mutableIntStateOf(if (actors.isNotEmpty()) actors[0].id else -1)
    }
    LazyRow(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
    ) {
        items(actors.size, key = { actors[it].id }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clickableWithoutIndicator {
                        selectedItemID = actors[it].id
                        onClick(actors[it].id)
                    }
                    .padding(start = 16.dp, end = 16.dp)
                    .shadow(8.dp, CircleShape)
                    .clip(CircleShape)
                    .background(if (selectedItemID == actors[it].id) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant)
            ) {
                CustomAsyncImage(
                    model = actors[it].image,
                    contentDescription = actors[it].name,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(if (selectedItemID == actors[it].id) 4.dp else 0.dp)
                        .size(if (selectedItemID == actors[it].id) 40.dp else 48.dp)
                        .shadow(8.dp, CircleShape)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
                CustomText(
                    text = actors[it].name,
                    fontSize = 14.sp,
                    fontFamilyID = R.font.poppins_medium,
                    color = if (selectedItemID == actors[it].id) Color.White else Color.DarkGray,
                    modifier = Modifier.padding(start = 4.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SeriesDetailPhotosVideosTab(
    videos: List<Video>,
    backDropImages: List<String>,
    posterImages: List<String>,
) {
    Column(
        Modifier
            .height((LocalConfiguration.current.screenHeightDp * 0.9).dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        TitleRow("Posters", {})
        LazyRow(
            Modifier
                .padding(8.dp)
                .height(180.dp)
        ) {
            items(posterImages.size, key = { posterImages[it] }) {
                if (posterImages.isEmpty()) {
                    ErrorScreen(message = "No Poster Found", modifier = Modifier.fillMaxWidth())
                } else {
                    CustomAsyncImage(
                        model = posterImages[it],
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(128.dp)
                            .height(180.dp)
                            .padding(8.dp)
                            .shadow(8.dp, MaterialTheme.shapes.small)
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.background)
                    )
                }
            }
        }
        TitleRow("Backdrops", {})
        LazyRow(
            Modifier
                .padding(8.dp)
                .height(160.dp)
        ) {
            items(backDropImages.size, key = { backDropImages[it] }) {
                if (backDropImages.isEmpty()) {
                    ErrorScreen(message = "No Poster Found", modifier = Modifier.fillMaxWidth())
                } else {
                    CustomAsyncImage(
                        model = backDropImages[it],
                        contentDescription = "Backdrop Image",
                        modifier = Modifier
                            .width(300.dp)
                            .height(160.dp)
                            .padding(8.dp)
                            .shadow(8.dp, MaterialTheme.shapes.small)
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.background)
                    )
                }
            }
        }
        TitleRow("Videos", {})
        LazyRow(
            Modifier
                .padding(end = 8.dp, start = 8.dp, top = 8.dp, bottom = 16.dp)
                .height(160.dp)
        ) {
            items(videos.size, key = { videos[it].id }) {
                if (videos.isEmpty()) {
                    ErrorScreen(message = "No Video Found", modifier = Modifier.fillMaxWidth())
                } else {
                    Column {
                        Box(
                            Modifier
                                .width(300.dp)
                                .height(160.dp)
                        ) {
                            CustomVideoAsyncImage(
                                videoModel = videos[it].key,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                                    .shadow(8.dp, MaterialTheme.shapes.small)
                                    .clip(MaterialTheme.shapes.small)
                                    .background(MaterialTheme.colorScheme.background)
                            )
                            Card(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 8.dp)
                                    .shadow(8.dp),
                            ) {
                                CustomText(
                                    text = videos[it].type,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(
                                        horizontal = 8.dp,
                                        vertical = 2.dp
                                    )
                                )
                            }
                            Card(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .shadow(8.dp, CircleShape),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.background
                                ),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = "",
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                        CustomText(
                            text = videos[it].name,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun SeriesDetailReviewsTab(reviews: List<Review>) {
    LazyColumn(
        Modifier
            .height((LocalConfiguration.current.screenHeightDp * 0.9).dp)
            .padding(8.dp)
    ) {
        items(reviews.size, key = { reviews[it].id }) {
            var lineCount by remember {
                mutableIntStateOf(8)
            }
            var overflow by remember {
                mutableStateOf(false)
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .shadow(8.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    CustomAsyncImage(
                        model = reviews[it].author.avatar,
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(top = 8.dp, end = 8.dp)
                            .size(36.dp)
                            .shadow(8.dp, CircleShape)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.background)
                    )
                    CustomText(
                        text = reviews[it].author.username,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamilyID = R.font.poppins_medium,
                        modifier = Modifier
                            .padding(top = 8.dp, end = 8.dp)
                            .weight(0.7f)
                    )
                    CustomText(
                        text = reviews[it].createdAt.dateTimeToFormattedDate(),
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamilyID = R.font.poppins_regular,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(top = 8.dp, end = 8.dp)
                            .weight(0.3f)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.dp, end = 8.dp, top = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_star),
                        contentDescription = "Rating"
                    )
                    CustomText(
                        text = "${reviews[it].author.rating}.0",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamilyID = R.font.poppins_medium,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                    )
                }
                CustomText(
                    text = reviews[it].content,
                    line = lineCount,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .animateContentSize { initialValue, targetValue ->
                            if (initialValue.height < targetValue.height) {
                                expandVertically()
                            } else {
                                shrinkVertically()
                            }
                        },
                    textLayoutResult = {
                        overflow = it.hasVisualOverflow
                    }
                )
                CustomText(
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamilyID = R.font.poppins_medium,
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                        .clickableWithoutIndicator {
                            lineCount = if (overflow) Int.MAX_VALUE else 8
                        },
                    text = if (overflow) "Read More" else "Read Less",
                )

            }
        }
    }
}
