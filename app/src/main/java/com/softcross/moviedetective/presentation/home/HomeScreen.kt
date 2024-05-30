package com.softcross.moviedetective.presentation.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.CalculateRemainingDays
import com.softcross.moviedetective.core.common.bouncingClickable
import com.softcross.moviedetective.core.common.extensions.startOffsetForPage
import com.softcross.moviedetective.core.domain.model.Genre
import com.softcross.moviedetective.core.domain.model.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val genres = listOf(
        Genre("1", "Action"),
        Genre("3", "Adventure"),
        Genre("2", "Horror"),
        Genre("25", "Western"),
        Genre("26", "SciFin"),
        Genre("23", "Action"),
        Genre("212", "Action"),
        Genre("278", "Action"),
        Genre("2890", "Action"),
        Genre("96", "Action"),
        Genre("20", "Action"),
        Genre("223", "Action"),
        Genre("245", "Action"),
    )
    val scrollState = rememberScrollState()
    val fakeMovies = listOf(
        Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("23", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2024-05-31",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("13", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-06-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("112", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2024-07-27",
            movieImage = "gravida"
        ),
        Movie(
            movieID = "ponderum",
            movieName = "Wilma Arnold",
            description = "feugait",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("156", "Science Fiction")
            ),
            imdb = 14.15f,
            releaseDate = "2024-09-27",
            movieImage = "tempus"
        ),
        Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("123", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2024-05-31",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("167", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-06-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("178", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2024-07-27",
            movieImage = "gravida"
        ),
        Movie(
            movieID = "ponderum",
            movieName = "Wilma Arnold",
            description = "feugait",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("112", "Science Fiction")
            ),
            imdb = 14.15f,
            releaseDate = "2024-09-27",
            movieImage = "tempus"
        ), Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("1567", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2024-05-31",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("1", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-06-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("167", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2024-07-27",
            movieImage = "gravida"
        ),        Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("23", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2024-05-31",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("13", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-06-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("112", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2024-07-27",
            movieImage = "gravida"
        ),
        Movie(
            movieID = "ponderum",
            movieName = "Wilma Arnold",
            description = "feugait",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("156", "Science Fiction")
            ),
            imdb = 14.15f,
            releaseDate = "2024-09-27",
            movieImage = "tempus"
        ),
        Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("123", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2024-05-31",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("167", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-06-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("178", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2024-07-27",
            movieImage = "gravida"
        ),
        Movie(
            movieID = "ponderum",
            movieName = "Wilma Arnold",
            description = "feugait",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("112", "Science Fiction")
            ),
            imdb = 14.15f,
            releaseDate = "2024-09-27",
            movieImage = "tempus"
        ), Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("1567", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2024-05-31",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("1", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-06-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("167", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2024-07-27",
            movieImage = "gravida"
        ),        Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("23", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2024-05-31",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("13", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-06-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("112", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2024-07-27",
            movieImage = "gravida"
        ),
        Movie(
            movieID = "ponderum",
            movieName = "Wilma Arnold",
            description = "feugait",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("156", "Science Fiction")
            ),
            imdb = 14.15f,
            releaseDate = "2024-09-27",
            movieImage = "tempus"
        ),
        Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("123", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2024-05-31",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("167", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-06-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("178", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2024-07-27",
            movieImage = "gravida"
        ),
        Movie(
            movieID = "ponderum",
            movieName = "Wilma Arnold",
            description = "feugait",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("112", "Science Fiction")
            ),
            imdb = 14.15f,
            releaseDate = "2024-09-27",
            movieImage = "tempus"
        ), Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("1567", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2024-05-31",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("1", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-06-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("167", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2024-07-27",
            movieImage = "gravida"
        ),        Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("23", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2024-05-31",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("13", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-06-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("112", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2024-07-27",
            movieImage = "gravida"
        ),
        Movie(
            movieID = "ponderum",
            movieName = "Wilma Arnold",
            description = "feugait",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("156", "Science Fiction")
            ),
            imdb = 14.15f,
            releaseDate = "2024-09-27",
            movieImage = "tempus"
        ),
        Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("123", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2024-05-31",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("167", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-06-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("178", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2024-07-27",
            movieImage = "gravida"
        ),
        Movie(
            movieID = "ponderum",
            movieName = "Wilma Arnold",
            description = "feugait",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("112", "Science Fiction")
            ),
            imdb = 14.15f,
            releaseDate = "2024-09-27",
            movieImage = "tempus"
        ), Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("1567", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2024-05-31",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("1", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-06-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("167", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2024-07-27",
            movieImage = "gravida"
        )
    )
    val pagerState = rememberPagerState { fakeMovies.size }
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
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(state = pagerState) { page ->
            HeaderContentItem(
                movie = fakeMovies[page],
                Modifier
                    .graphicsLayer {
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
        Text(
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
        )
    }
}

@Composable
fun HeaderContentItem(movie: Movie, modifier: Modifier) {
    Box(
        modifier = modifier
            .padding(bottom = 32.dp)
            .width(400.dp)
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .height(125.dp)
                .width(300.dp)
                .padding(end = 16.dp)
                .shadow(8.dp, MaterialTheme.shapes.small)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(start = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = movie.movieName,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp, start = 16.dp, top = 8.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_star),
                    contentDescription = "",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(24.dp)
                        .align(alignment = Alignment.CenterVertically)
                        .padding(end = 8.dp, top = 8.dp)
                )
                Text(
                    text = movie.imdb.toString(),
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(0.15f)
                        .padding(end = 8.dp, top = 8.dp)
                )
            }
            LazyRow(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                items(movie.genres) {
                    GenreItem(genre = it)
                }
            }
            Text(
                text = movie.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 10.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                modifier = Modifier.padding(end = 8.dp, start = 16.dp)
            )
            Text(
                text = movie.releaseDate,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.movieimage),
            contentDescription = "",
            alignment = Alignment.CenterStart,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .padding(16.dp)
                .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                .clip(MaterialTheme.shapes.small)
        )
    }
}

@Composable
fun GenreItem(genre: Genre) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Box(
            Modifier.background(
                Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.secondary,
                        Color.White
                    )
                )
            )
        ) {
            Text(
                text = genre.genreName,
                fontSize = 8.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComingSoon(movieList: List<Movie>) {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        items(movieList) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(8.dp, MaterialTheme.shapes.small)
                    .clip(MaterialTheme.shapes.small)
                    .width(160.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .bouncingClickable(onClick = {})
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.movieimage),
                        contentDescription = "",
                        alignment = Alignment.CenterStart,
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .padding(8.dp)
                            .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                            .clip(MaterialTheme.shapes.small)
                    )
                    Card(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "${CalculateRemainingDays(it.releaseDate)} days",
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )
                    }
                }
                Text(
                    text = it.movieName,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = it.releaseDate,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                )
            }
        }
    }
}

@Composable
fun TrendMovies(movieList: List<Movie>) {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        items(movieList) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(8.dp, MaterialTheme.shapes.small)
                    .clip(MaterialTheme.shapes.small)
                    .width(160.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .bouncingClickable(onClick = {})
            ) {
                Image(
                    painter = painterResource(id = R.drawable.movieimage),
                    contentDescription = "",
                    alignment = Alignment.CenterStart,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .padding(8.dp)
                        .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                        .clip(MaterialTheme.shapes.small)
                )
                Text(
                    text = it.movieName,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_star),
                        contentDescription = "",
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .size(24.dp)
                            .weight(0.2f)
                            .padding(start = 16.dp)
                    )
                    Text(
                        text = it.imdb.toString(),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(0.3f)
                            .padding(start = 4.dp)
                    )
                    Text(
                        text = "2024",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        color = Color.Gray,
                        modifier = Modifier
                            .weight(0.6f)
                            .padding(end = 16.dp)
                    )
                }

            }
        }

    }
}

@Composable
fun DiscoverMovie(movieList: List<Movie>, genreList: List<Genre>) {
    Column {
        Row(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            LazyRow(
                modifier = Modifier.weight(0.8f)
            ) {
                items(genreList) {
                    Card(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Text(
                            text = it.genreName,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
            Image(
                Icons.Filled.Settings,
                contentDescription = "",
                modifier = Modifier.weight(0.1f)
            )
        }
        LazyRow(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            items(
                items = movieList
            ) {movie ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .shadow(8.dp, MaterialTheme.shapes.small)
                        .clip(MaterialTheme.shapes.small)
                        .width(160.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .bouncingClickable(onClick = {})
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.movieimage),
                        contentDescription = "",
                        alignment = Alignment.CenterStart,
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .padding(8.dp)
                            .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                            .clip(MaterialTheme.shapes.small)
                    )
                    Text(
                        text = movie.movieName,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp)
                    )
                    LazyRow(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        items(
                            movie.genres,
                            key = {
                                it.genreID
                            }
                        ) {
                            GenreItem(genre = it)
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_star),
                            contentDescription = "",
                            contentScale = ContentScale.Inside,
                            modifier = Modifier
                                .size(24.dp)
                                .weight(0.2f)
                                .padding(start = 16.dp)
                        )
                        Text(
                            text = movie.imdb.toString(),
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            color = Color.Black,
                            modifier = Modifier
                                .weight(0.3f)
                                .padding(start = 4.dp)
                        )
                        Text(
                            text = "2024",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            color = Color.Gray,
                            modifier = Modifier
                                .weight(0.6f)
                                .padding(end = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PopularPeoples(peopleList: List<String>) {
    LazyRow(
        modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
    ) {
        items(peopleList) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(8.dp, MaterialTheme.shapes.small)
                    .clip(MaterialTheme.shapes.small)
                    .width(160.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .bouncingClickable(onClick = {})
            ) {
                Image(
                    painter = painterResource(id = R.drawable.movieimage),
                    contentDescription = "",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(MaterialTheme.shapes.small)
                )
                Text(
                    text = it,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, bottom = 8.dp)
                )
            }
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