package com.softcross.moviedetective.presentation.detail

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.softcross.moviedetective.common.extensions.swap
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.domain.model.MovieDetail
import com.softcross.moviedetective.presentation.components.CustomAsyncImage
import com.softcross.moviedetective.presentation.components.CustomDetailIcons
import com.softcross.moviedetective.presentation.components.CustomText
import com.softcross.moviedetective.presentation.components.MovieDetailCastItem
import com.softcross.moviedetective.presentation.components.TrendContentItem
import java.util.Collections

sealed class TabRowItems<out T : Any> {
    data class Cast(val actor: List<Actor>) : TabRowItems<List<Actor>>()
    data class Similar(val movie: List<Movie>) : TabRowItems<List<Movie>>()
}

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    when (state) {
        is ScreenState.Error -> println("Error ${state.message}")
        ScreenState.Loading -> println("Loading")
        is ScreenState.Success -> {
            SuccessContent(movieDetail = state.uiData)
        }
    }
}

@Composable
fun SuccessContent(movieDetail: MovieDetail) {
    val genres = movieDetail.movie.genres.map {
        GenreList.findMovieGenreWithID(it)
    }.joinToString {
        it.genreName
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
    ) {
        BannerContent()
        CustomText(
            text = movieDetail.movie.movieName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            fontSize = 24.sp,
            color = Color.DarkGray,
            fontFamilyID = R.font.poppins_semi_bold,
            line = 2
        )
        CustomText(
            text = genres,
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 14.sp,
            color = Color.Gray,
            line = 3
        )
        SubDetailRow(movieDetail)
        TabDetails(movieDetail)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabDetails(movieDetail: MovieDetail) {
    val tabItems = listOf("Overview", "Photos & Videos", "Reviews")
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    var pagerState = rememberPagerState {
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
    TabRow(selectedTabIndex = selectedTabIndex) {
        tabItems.forEachIndexed { index, tabItem ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { selectedTabIndex = index },
                text = {
                    CustomText(
                        text = tabItem,
                        color = Color.DarkGray,
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
            0 -> OverViewTab(movieDetail)
            1 -> Text(text = "2")
            2 -> Text(text = "3")
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
                .background(Color.Gray, CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_star),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White),
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
            Modifier
                .padding(end = 8.dp)
                .background(Color.Gray, CircleShape)
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
            fontSize = 14.sp,
            fontFamilyID = R.font.poppins_semi_bold,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun BannerContent() {
    val configuration = LocalConfiguration.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height((configuration.screenHeightDp * 0.7).dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.movieimage),
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
        )
        Card(
            shape = CircleShape,
            modifier = Modifier
                .align(BiasAlignment(1f, 0.75f))
                .padding(end = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Icon(
                painterResource(id = R.drawable.icon_exit),
                contentDescription = "",
                Modifier
                    .padding(24.dp)
                    .size(32.dp)
            )
        }
        Card(
            modifier = Modifier
                .height(180.dp)
                .width(145.dp)
                .align(Alignment.BottomStart)
                .padding(start = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Blue
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.movieimage),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            CustomDetailIcons(
                drawableID = R.drawable.icon_right_arrow, onClick = {},
                iconModifier = Modifier
                    .padding(12.dp)
                    .size(16.dp)
            )
            CustomDetailIcons(
                drawableID = R.drawable.icon_reminder, onClick = {},
                iconModifier = Modifier
                    .padding(12.dp)
                    .size(16.dp)
            )
            CustomDetailIcons(
                drawableID = R.drawable.icon_watchlist, onClick = {},
                iconModifier = Modifier
                    .padding(12.dp)
                    .size(16.dp)
            )
            CustomDetailIcons(
                drawableID = R.drawable.icon_add_to_list, onClick = {},
                iconModifier = Modifier
                    .padding(12.dp)
                    .size(16.dp)
            )
            CustomDetailIcons(
                drawableID = R.drawable.icon_done, onClick = {},
                iconModifier = Modifier
                    .padding(12.dp)
                    .size(16.dp)
            )
        }
    }
}

@Composable
fun OverViewTab(movieDetail: MovieDetail) {
    Column {
        CustomText(
            text = "Storyline",
            fontSize = 24.sp,
            fontFamilyID = R.font.poppins_semi_bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        CustomText(
            line = Int.MAX_VALUE,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
            text = movieDetail.movie.description,
        )
        CustomText(
            text = "Cast",
            fontSize = 24.sp,
            fontFamilyID = R.font.poppins_semi_bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        OverViewTabRows(TabRowItems.Cast(movieDetail.cast))
        CustomText(
            text = "More Like This",
            fontSize = 24.sp,
            fontFamilyID = R.font.poppins_semi_bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        OverViewTabRows(TabRowItems.Similar(movieDetail.similarMovies))
        CustomText(
            text = "Casts More Movies",
            fontSize = 24.sp,
            fontFamilyID = R.font.poppins_semi_bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
        )
        OverViewCastSelectionRow(movieDetail.cast.toMutableList())
        OverViewTabRows(TabRowItems.Similar(movieDetail.similarMovies))
    }
}

@Composable
fun OverViewCastSelectionRow(actors: MutableList<Actor>) {
    var selectedItemID by remember {
        mutableIntStateOf(actors[0].id)
    }
    val lazyRowState = rememberLazyListState()
    LaunchedEffect(key1 = selectedItemID) {
        lazyRowState.animateScrollToItem(0)
    }
    LazyRow(
        state = lazyRowState,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(actors.size, key = { actors[it].id }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clickableWithoutIndicator {
                        selectedItemID = actors[it].id
                        actors.swap(0, it)
                    }
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                    .shadow(8.dp, CircleShape)
                    .clip(CircleShape)
                    .background(if (selectedItemID == actors[it].id) MaterialTheme.colorScheme.secondary else Color.White)
            ) {
                CustomAsyncImage(
                    model = actors[it].image,
                    contentDescription = actors[it].name,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(if (selectedItemID == actors[it].id) 4.dp else 0.dp)
                        .size(48.dp)
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

@Preview
@Composable
fun MovieDetailScreenPreview() {
    MovieDetailScreen()
}