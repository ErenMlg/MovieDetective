package com.softcross.moviedetective.presentation.detail

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.domain.model.MovieDetail
import com.softcross.moviedetective.domain.model.Review
import com.softcross.moviedetective.domain.model.Video
import com.softcross.moviedetective.presentation.components.CustomAsyncImage
import com.softcross.moviedetective.presentation.components.CustomDetailIcons
import com.softcross.moviedetective.presentation.components.CustomSnackbar
import com.softcross.moviedetective.presentation.components.CustomText
import com.softcross.moviedetective.presentation.components.CustomVideoAsyncImage
import com.softcross.moviedetective.presentation.components.ErrorScreen
import com.softcross.moviedetective.presentation.components.LoadingContentItems
import com.softcross.moviedetective.presentation.components.MovieDetailCastItem
import com.softcross.moviedetective.presentation.components.TrendContentItem
import kotlinx.coroutines.delay

sealed class TabRowItems<out T : Any> {
    data class Cast(val actor: List<Actor>) : TabRowItems<List<Actor>>()
    data class Similar(val movie: List<Movie>) : TabRowItems<List<Movie>>()
}

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val detailState = viewModel.detailState.value
    val actorMovieState = viewModel.actorMovieState
    when (detailState) {
        is ScreenState.Error -> println("Error ${detailState.message}")
        ScreenState.Loading -> println("Loading detail State")
        is ScreenState.Success -> {
            SuccessContent(
                movieDetail = detailState.uiData,
                actorMovieState = actorMovieState,
                onClick = remember {
                    {
                        viewModel.onMovieClicked(it)
                    }
                })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuccessContent(
    movieDetail: MovieDetail,
    actorMovieState: State<ScreenState<List<Movie>>>,
    onClick: (Int) -> Unit
) {
    val genres = movieDetail.movie.genres.map {
        GenreList.findMovieGenreWithID(it)
    }.joinToString {
        it.genreName
    }
    val scrollState = rememberScrollState()
    val tabItems = listOf("Overview", "Photos & Videos", "Reviews")
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }
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
        BannerContent(movieDetail)
        CustomText(
            text = movieDetail.movie.movieName,
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
        SubDetailRow(movieDetail)
        TabRow(
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
                0 -> OverViewTab(movieDetail, actorMovieState, onClick)
                1 -> PhotosVideosTab(
                    videos = movieDetail.videos,
                    backDropImages = movieDetail.backDropImages,
                    posterImages = movieDetail.posterImages
                )

                2 -> ReviewsTab(movieDetail.reviews)
            }
        }
    }
}

@Composable
fun SubDetailRow(movieDetail: MovieDetail) {
    val min = movieDetail.runtime % 60
    val hour = movieDetail.runtime / 60
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
                text = "%.2f".format(movieDetail.movie.imdb),
                fontSize = 14.sp,
                color = Color.White,
                fontFamilyID = R.font.poppins_semi_bold,
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp, end = 16.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(end = 8.dp)
                .background(MaterialTheme.colorScheme.secondary, CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_filter),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(end = 8.dp, start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
            CustomText(
                text = "$hour hr $min min",
                fontSize = 14.sp,
                color = Color.White,
                fontFamilyID = R.font.poppins_semi_bold,
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp, end = 16.dp)
            )
        }
        CustomText(
            text = movieDetail.movie.releaseDate.convertToFormattedDate(),
            modifier = Modifier.padding(end = 16.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            fontFamilyID = R.font.poppins_semi_bold,
            textAlign = TextAlign.End
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerContent(movieDetail: MovieDetail) {
    val configuration = LocalConfiguration.current
    val pagerState = rememberPagerState { movieDetail.backDropImages.size }
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
                model = movieDetail.backDropImages[it],
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
                painter = painterResource(id = R.drawable.icon_exit),
                tint = Color.White,
                contentDescription = "",
                modifier = Modifier
                    .padding(24.dp)
                    .size(32.dp)
            )
        }
        CustomAsyncImage(
            model = movieDetail.movie.movieImage,
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
            CustomDetailIcons(
                drawableID = R.drawable.icon_right_arrow, onClick = {},
                color = Color.White,
                iconModifier = Modifier
                    .padding(12.dp)
                    .size(16.dp),
                cardModifier = Modifier.shadow(8.dp, CircleShape)
            )
            CustomDetailIcons(
                drawableID = R.drawable.icon_reminder, onClick = {},
                color = Color.White,
                iconModifier = Modifier
                    .padding(12.dp)
                    .size(16.dp),
                cardModifier = Modifier.shadow(8.dp, CircleShape)
            )
            CustomDetailIcons(
                drawableID = R.drawable.icon_watchlist, onClick = {},
                color = Color.White,
                iconModifier = Modifier
                    .padding(12.dp)
                    .size(16.dp),
                cardModifier = Modifier.shadow(8.dp, CircleShape)
            )
            CustomDetailIcons(
                drawableID = R.drawable.icon_add_to_list, onClick = {},
                color = Color.White,
                iconModifier = Modifier
                    .padding(12.dp)
                    .size(16.dp),
                cardModifier = Modifier.shadow(8.dp, CircleShape)
            )
            CustomDetailIcons(
                drawableID = R.drawable.icon_done, onClick = {},
                color = Color.White,
                iconModifier = Modifier
                    .padding(12.dp)
                    .size(16.dp),
                cardModifier = Modifier.shadow(8.dp, CircleShape)
            )
        }
    }
}

@Composable
fun OverViewTab(
    movieDetail: MovieDetail,
    actorMovieState: State<ScreenState<List<Movie>>>,
    onClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .height((LocalConfiguration.current.screenHeightDp * 0.9).dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        item {
            CustomText(
                text = "Storyline",
                fontSize = 24.sp,
                fontFamilyID = R.font.poppins_semi_bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
        }
        item {
            CustomText(
                line = Int.MAX_VALUE,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                text = movieDetail.movie.description,
            )
        }
        item {
            TabsTitleRow("Cast", {})
        }
        item {
            OverViewTabRows(TabRowItems.Cast(movieDetail.cast))
        }
        item {
            TabsTitleRow("More Like This", {})
        }
        item {
            OverViewTabRows(TabRowItems.Similar(movieDetail.similarMovies))
        }
        item {
            TabsTitleRow("Casts More Movies", {})
        }
        item {
            OverViewCastSelectionRow(movieDetail.cast, onClick)
        }
        item {
            when (val response = actorMovieState.value) {
                is ScreenState.Error -> CustomSnackbar(
                    errorMessage = response.message,
                    modifier = Modifier.padding(16.dp)
                )

                is ScreenState.Loading ->
                    LazyRow(Modifier.padding(8.dp)) {
                        items(3) {
                            LoadingContentItems()
                        }
                    }

                is ScreenState.Success -> {
                    if (response.uiData.isNotEmpty()) {
                        OverViewTabRows(TabRowItems.Similar(response.uiData))
                    } else {
                        ErrorScreen(
                            message = "No Movie Found",
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OverViewCastSelectionRow(actors: List<Actor>, onClick: (Int) -> Unit) {
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
fun OverViewTabRows(type: TabRowItems<Any>) {
    LazyRow(
        Modifier.padding(8.dp)
    ) {
        when (type) {
            is TabRowItems.Cast -> items(type.actor.size, key = {
                type.actor[it].id
            }) {
                MovieDetailCastItem(
                    people = type.actor[it],
                )
            }

            is TabRowItems.Similar -> items(type.movie.size, key = {
                type.movie[it].movieID
            }) {
                TrendContentItem(
                    movie = type.movie[it],
                    onClick = {}
                )
            }
        }

    }
}

@Composable
fun PhotosVideosTab(
    videos: List<Video>,
    backDropImages: List<String>,
    posterImages: List<String>,
) {
    LazyColumn(
        Modifier
            .height((LocalConfiguration.current.screenHeightDp * 0.9).dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            TabsTitleRow("Posters", {})
        }
        item {
            LazyRow(
                Modifier
                    .padding(8.dp)
                    .height(180.dp)
            ) {
                items(posterImages.size, key = { posterImages[it] }) {
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
        item {
            TabsTitleRow("Backdrops", {})
        }
        item {
            LazyRow(
                Modifier
                    .padding(8.dp)
                    .height(160.dp)
            ) {
                items(backDropImages.size, key = { backDropImages[it] }) {
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
        item {
            TabsTitleRow("Videos", {})
        }
        item {
            LazyRow(
                Modifier
                    .padding(8.dp)
                    .height(160.dp)
            ) {
                items(videos.size, key = { videos[it].id }) {
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
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
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
fun TabsTitleRow(title: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomText(
            text = title,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            fontFamilyID = R.font.poppins_semi_bold,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .weight(0.8f)
        )
        CustomText(
            text = "View more",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 8.dp)
                .weight(0.3f)
        )
        Image(
            painter = painterResource(id = R.drawable.icon_left_arrow),
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .padding(top = 16.dp, end = 16.dp)
                .size(12.dp)
                .weight(0.1f)
        )
    }
}

@Composable
fun ReviewsTab(reviews: List<Review>) {
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
                        color = MaterialTheme.colorScheme.primary,
                        fontFamilyID = R.font.poppins_medium,
                        modifier = Modifier
                            .padding(top = 8.dp, end = 8.dp)
                            .weight(0.7f)
                    )
                    CustomText(
                        text = reviews[it].createdAt.dateTimeToFormattedDate(),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
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
                        text = "${reviews[it].author.rating.toString()}.0",
                        fontSize = 12.sp,
                        fontFamilyID = R.font.poppins_medium,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                    )
                }
                CustomText(
                    text = reviews[it].content,
                    line = lineCount,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).animateContentSize { initialValue, targetValue ->
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
                if (overflow) {
                    CustomText(
                        text = "Read More",
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamilyID = R.font.poppins_medium,
                        modifier = Modifier
                            .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                            .clickableWithoutIndicator { lineCount = Int.MAX_VALUE }
                    )
                }
                if (lineCount == Int.MAX_VALUE){
                    CustomText(
                        text = "Read Less",
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamilyID = R.font.poppins_medium,
                        modifier = Modifier
                            .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
                            .clickableWithoutIndicator { lineCount = 8 }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieDetailScreenPreview() {
}